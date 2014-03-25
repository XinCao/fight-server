package fight.server.thread;

import fight.server.model.Fighter;
import fight.server.service.AC;
import fight.server.service.FighterService;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javolution.util.FastMap;
import org.springframework.context.ApplicationContext;

/**
 *
 * @author caoxin
 */
public class SingleArenaManager implements Runnable {

    private final Lock lock = new ReentrantLock();
    private final Condition notEmpty = lock.newCondition();
    private final Map<String, FighterThread> fighterThreadSet = new FastMap<String, FighterThread>().shared();
    private ApplicationContext ac = AC.getAC();
    private ThreadPool<FighterThread> fightingPool = (ThreadPool<FighterThread>) ac.getBean("fightingPool");
    private FighterService fighterService = ac.getBean(FighterService.class);

    public void start() {
        Thread singleArenaManager = new Thread(this, "single arena manager thread");
        singleArenaManager.start();
    }

    @Override
    public void run() {
        while (true) {
            lock.lock();
            try {
                while (fighterThreadSet.isEmpty()) {
                    notEmpty.awaitUninterruptibly();
                }
                for (Entry<String, FighterThread> e : fighterThreadSet.entrySet()) {
                    fightingPool.executeTask(e.getValue());
                    fighterThreadSet.remove(e.getKey());
                }
            } finally {
                lock.unlock();
            }
        }
    }

    public JoinResult joinSingleArena(String nameMe, String nameHe) {
        if (!canJoin(nameMe)) {
            return JoinResult.I_HAVE_JOINED;
        }
        if (!canJoin(nameHe)) {
            return JoinResult.HE_HAVE_JOINED;
        }
        lock.lock();
        try {
            if (!joinFighterThreadSet(nameMe, nameHe)) {
                return JoinResult.ERROR;
            }
        } finally {
            lock.unlock();
        }
        return JoinResult.OK;
    }

    private boolean joinFighterThreadSet(String nameMe, String nameHe) {
        Fighter fighterMe = fighterService.selectFighter(nameMe);
        Fighter fighterHe = fighterService.selectFighter(nameHe);
        if (fighterMe == null || fighterHe == null) {
            return false;
        }
        FighterThread fighterThreadMe = new FighterThread(fighterMe, fighterHe, ac);
        FighterThread fighterThreadHe = new FighterThread(fighterHe, fighterMe, ac);
        fighterThreadSet.put(nameMe, fighterThreadMe);
        fighterThreadSet.put(nameHe, fighterThreadHe);
        fighterMe.isFighting = true;
        fighterHe.isFighting = true;
        notEmpty.signal();
        return true;
    }

    private boolean canJoin(String name) {
        if (fighterThreadSet.containsKey(name)) {
            return false;
        }
        return true;
    }

    public static enum JoinResult {

        OK(1, "ok"),
        I_HAVE_JOINED(2, "I have join"),
        HE_HAVE_JOINED(3, "He have join"),
        ERROR(4, "error fighter")
        ;
        private int num;
        private String describe;

        private JoinResult(int num, String describe) {
            this.num = num;
            this.describe = describe;
        }

        public int getNum() {
            return this.num;
        }

        public String getDescribe() {
            return this.describe;
        }
    }
}