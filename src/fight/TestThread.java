package fight;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import org.apache.commons.pool.KeyedObjectPool;
import org.apache.commons.pool.impl.StackKeyedObjectPoolFactory;

public class TestThread implements Runnable {

    private String name;
    private KeyedObjectPool pool;
    private InetSocketAddress address;

    private TestThread(String name, KeyedObjectPool pool, InetSocketAddress address) {
        this.name = name;
        this.pool = pool;
        this.address = address;
    }
    
    public static void start(String name, KeyedObjectPool pool, InetSocketAddress address) {
        Thread thread = new Thread(new TestThread(name, pool, address));
        thread.start();
    }

    @Override
    public void run() {
        System.out.println(name + ": Client Start");
        SocketChannel channel = null;
        try {
            channel = (SocketChannel) pool.borrowObject(address);
        } catch (Exception ex) {
        }
        if (channel != null) {
            try {
                channel.configureBlocking(false);
                Selector selector = Selector.open();
                SelectionKey key = channel.register(selector, SelectionKey.OP_READ);
                boolean stop = false;
                int n = 0;
                int read = 0;
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                while (!stop) {
                    n = selector.select();
                    // 当传回的值大于0事，读事件发生了
                    if (n > 0) {
                        Set<SelectionKey> set = selector.selectedKeys();
                        Iterator<SelectionKey> it = set.iterator();
                        while (it.hasNext()) {
                            key = it.next();
                            it.remove();
                            if (key.isReadable()) {
                                SocketChannel sc = (SocketChannel) key.channel();
                                while ((read = sc.read(buffer)) != -1) {
                                    if (read == 0) {
                                        break;
                                    }
                                    buffer.flip();
                                    byte[] array = new byte[read];
                                    buffer.get(array);
                                    String s = new String(array);
                                    System.out.print(s);
                                    buffer.clear();

                                    if (s.indexOf("new") != -1) {
                                        stop = true;
                                        System.out.println();
                                    }
                                }
                            }
                        }
                    }
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }

            try {
                pool.returnObject(address, channel);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        System.out.println(name + ": Client Stop");
    }

    /**
     * 测试方法
     *
     * @param args String[] 控制台参数
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        SocketPoolableObjectFactory factory = new SocketPoolableObjectFactory();
        StackKeyedObjectPoolFactory poolFactory = new StackKeyedObjectPoolFactory(factory);
        KeyedObjectPool pool = poolFactory.createPool();
        TestThread.start("清华", pool, new InetSocketAddress("bbs.tsinghua.edu.cn", 23));
    }
}
