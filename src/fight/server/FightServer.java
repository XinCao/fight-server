package fight.server;

import fight.server.net.imp.core.IOServer;
import fight.server.net.imp.util.DeadLockDetector;
import fight.server.net.imp.util.ThreadPoolManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 *
 * @author caoxin
 */
public class FightServer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ApplicationContext ac = new FileSystemXmlApplicationContext("./config/app.xml");
//        FighterArena fighterArena = ac.getBean(FighterArena.class);
//        fighterArena.towFighterPK("caoxin", "lijing");
        new DeadLockDetector(60, DeadLockDetector.RESTART).start(); // 检查死锁
        ThreadPoolManager.getInstance();
        IOServer.getInstance().connect();
    }
}
