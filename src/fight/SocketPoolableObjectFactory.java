package fight;

import java.net.SocketAddress;
import java.nio.channels.SocketChannel;
import org.apache.commons.pool.KeyedPoolableObjectFactory;

public class SocketPoolableObjectFactory implements KeyedPoolableObjectFactory {

    /**
     * 创建对象
     *
     * @param key Object 创建对象需要用到的参数
     * @return Object SocketChannel实例
     * @throws Exception
     */
    @Override
    public Object makeObject(Object key) throws Exception {
        SocketAddress address = (SocketAddress) key;
        SocketChannel channel = SocketChannel.open(address);
        return channel;
    }

    /**
     * 销毁对象
     *
     * @param key Object 创建对象时的参数
     * @param obj Object 需要销毁的对象
     * @throws Exception
     */
    @Override
    public void destroyObject(Object key, Object obj) throws Exception {
        SocketChannel channel = (SocketChannel) obj;
        if (channel != null) {
            channel.close();
        }
    }

    /**
     * 检验对象是否有效
     *
     * @param key Object 创建对象时的参数
     * @param obj Object 需要进行检验的对象
     * @return boolean 有效返回true，无效返回false
     */
    @Override
    public boolean validateObject(Object key, Object obj) {
        SocketChannel channel = (SocketChannel) obj;
        if (channel != null && channel.isOpen() && channel.isConnected()) {
            return true;
        }
        return false;
    }

    /**
     * 将对象激活，这里不需要做任何工作
     *
     * @param key Object
     * @param obj Object
     * @throws Exception
     */
    @Override
    public void activateObject(Object key, Object obj) throws Exception {
    }

    /**
     * 将对象挂起，这里不需要做任何工作
     *
     * @param key Object
     * @param obj Object
     * @throws Exception
     */
    @Override
    public void passivateObject(Object key, Object obj) throws Exception {
    }
}