package fight.server.thread;

import fight.server.model.Fighter;
import fight.server.net.imp.core.AionConnection;
import fight.server.service.AC;
import fight.server.service.FighterService;
import fight.server.util.CooldownId;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
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
    private final Map<String, SingleArenaThread> fighterThreadSet = new FastMap<String, SingleArenaThread>().shared();
    private final Map<String, Fighter> fighterSet = new FastMap<String, Fighter>().shared();
    private ApplicationContext ac = AC.getAC();
    private ThreadPool<SingleArenaThread> fightingPool = (ThreadPool<SingleArenaThread>) ac.getBean("fightingPool");
    private FighterService fighterService = ac.getBean(FighterService.class);

    public void start() {
        Thread singleArenaManager = new Thread(this, "single arena manager thread");
        singleArenaManager.start();
    }

    @Override
    public void run() {
        Set<Entry<String, Fighter>> s = fighterSet.entrySet();
        while (true) {
            lock.lock();
            try {
                while (fighterThreadSet.isEmpty()) {
                    notEmpty.awaitUninterruptibly();
                }
                for (Entry<String, Fighter> e : s) {
                    Fighter f = e.getValue();
                    if (f.isFighting && f.getTargetFigher().isFighting && !f.isFightingAndK) {
                        for (CooldownId cooldownId : CooldownId.coolList) {
                            if (f.getCooldownCollection().inCoolDown(cooldownId)) {
                                continue;
                            }
                            if (!f.isAutoFight() && !f.isOneAction()) {
                                f.autoFight(true);
                            }
                            SingleArenaThread fighterThread = fighterThreadSet.get(f.getName());
                            fighterThread.setCooldownId(cooldownId);
                            fightingPool.executeTask(fighterThread);
                        }
                    }
                }
            } finally {
                lock.unlock();
            }
        }
    }

    public boolean startFighting(String nameMe, String nameHe) {
        Fighter fighterMe;
        Fighter fighterHe;
        if (fighterSet.containsKey(nameMe) && fighterSet.containsKey(nameHe)) {
            fighterMe = fighterSet.get(nameMe);
            fighterHe = fighterSet.get(nameHe);
            fighterMe.setTargetFigher(fighterHe);
            fighterMe.isFighting = true;
        } else {
            return false;
        }
        return true;
    }

    public void stopFighting(String name) {
        if (fighterSet.containsKey(name)) {
            fighterSet.get(name).isFighting = false;
        }
    }

    public JoinResult joinSingleArena(String name, AionConnection client) {
        if (!canJoin(name)) {
            return JoinResult.I_HAVE_JOINED;
        }
        lock.lock();
        try {
            if (!joinFighterThreadSet(name, client)) {
                return JoinResult.ERROR;
            }
        } finally {
            lock.unlock();
        }
        return JoinResult.OK;
    }

    private boolean joinFighterThreadSet(String name, AionConnection client) {
        Fighter fighter = fighterService.selectFighter(name);
        if (fighter == null) {
            return false;
        }
        SingleArenaThread fighterThread = new SingleArenaThread(fighter, ac, client);
        fighterThreadSet.put(name, fighterThread);
        fighterSet.put(name, fighter);
        fighter.isFightingAndK = false;
        fighter.getCooldownCollection().initCooldownCoolection();
        notEmpty.signal();
        return true;
    }

    private boolean canJoin(String name) {
        if (fighterSet.containsKey(name)) {
            return false;
        }
        return true;
    }

    public void removeFighter(String name) {
        if (fighterSet.containsKey(name)) {
            fighterSet.remove(name);
        }
        if (fighterThreadSet.containsKey(name)) {
            fighterThreadSet.remove(name);
        }
    }

    public static enum JoinResult {

        OK(1, "ok"),
        I_HAVE_JOINED(2, "I have join"),
        HE_HAVE_JOINED(3, "He have join"),
        ERROR(4, "error fighter");
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

    public SingleArenaThread getSingleArenaThreadByName(String name) {
        if (fighterThreadSet.containsKey(name)) {
            return fighterThreadSet.get(name);
        }
        return null;
    }
}