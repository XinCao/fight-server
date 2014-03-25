package fight.server.thread;

import fight.server.model.Fighter;
import fight.server.model.constant.Status;
import fight.server.script.FightingScript;
import fight.server.util.CooldownCollection;
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
    private CooldownCollection cooldownCollection;
    private FightingScript fightingScript;

    public FighterThread(Fighter fighterA, Fighter fighterB, ApplicationContext ac) {
        this.fighterA = fighterA;
        this.fighterB = fighterB;
        this.cooldownCollection = fighterA.getCooldownCollection();
        this.fightingScript = (FightingScript) ac.getBean("fightingScript");
        cooldownCollection.initCooldownCoolection();
    }

    @Override
    public void run() {
        long startTime = System.currentTimeMillis() / 1000;
        logger.info("fight name = {} start fighting!", fighterA.getName());
        logger.info(fighterA.toString());
        while (fighterA.isFighting) {
            for (CooldownId cooldownId : CooldownId.coolList) {
                if (cooldownCollection.inCoolDown(cooldownId)) {
                    continue;
                }
                if (!fighterA.isAutoFight() && !fighterA.isOneAction()) {
                    fighterA.autoFight(true);
                }
                onceAction(cooldownId);
                logger.info(fighterA.toString());
                cooldownCollection.setCoolDown(cooldownId);
                if (fighterA.getLife() <= 0 || fighterB.getLife() <= 0) {
                    fighterA.isFighting = false;
                    if (fighterA.getLife() > 0) {
                        fighterA.status = Status.SUCCESS;
                    } else {
                        fighterA.status = Status.FAILURE;
                    }
                    logger.info("fight name = {} is {}, useing time is {}", fighterA.getName(), fighterA.status.count == Status.SUCCESS.count ? "success" : "failure", System.currentTimeMillis() / 1000 - startTime);
                    break;
                }
            }
        }
    }

    private void onceAction(CooldownId cooldownId) {
        if (cooldownId.equals(CooldownId.PHY_ATK)) {
            fightingScript.oncePhyAtk(fighterA.getFightPropertyCountAndValueMap(), fighterB.getFightPropertyCountAndValueMap());
        }
        fighterA.setFightPropertyByMap();
    }
}