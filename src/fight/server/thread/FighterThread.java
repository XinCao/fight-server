package fight.server.thread;

import fight.server.model.Fighter;
import fight.server.model.constant.Status;
import fight.server.script.FightingScript;
import fight.server.util.CooldownId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

/**
 *
 * @author caoxin
 */
public class FighterThread extends ObjectLock implements Runnable {

    private Logger logger = LoggerFactory.getLogger(FighterThread.class);
    private Fighter fighterA;
    private Fighter fighterB;
    private FightingScript fightingScript;
    private SingleArenaManager singleArenaManager;
    private CooldownId cooldownId;

    public FighterThread(Fighter fighterA, Fighter fighterB, ApplicationContext ac) {
        this.fighterA = fighterA;
        this.fighterB = fighterB;
        this.fightingScript = (FightingScript) ac.getBean("fightingScript");
        this.singleArenaManager = ac.getBean(SingleArenaManager.class);
    }

    @Override
    public void run() {
        fighterA.isFighting = true;
        onceAction();
        logger.info(fighterA.toString());
        fighterA.getCooldownCollection().setCoolDown(cooldownId);
        if (fighterA.getLife() <= 0 || fighterB.getLife() <= 0) {
            singleArenaManager.removeFighter(fighterA.getName());
            if (fighterA.getLife() > 0) {
                fighterA.status = Status.SUCCESS;
            } else {
                fighterA.status = Status.FAILURE;
            }
        }
        fighterA.isFighting = false;
    }

    private void onceAction() {
        if (cooldownId.equals(CooldownId.PHY_ATK)) {
            fightingScript.oncePhyAtk(fighterA.getFightPropertyCountAndValueMap(), fighterB.getFightPropertyCountAndValueMap());
        }
        fighterA.setFightPropertyByMap();
    }

    public CooldownId getCooldownId() {
        return cooldownId;
    }

    public void setCooldownId(CooldownId cooldownId) {
        this.cooldownId = cooldownId;
    }
}