package fight.server.thread;

import fight.server.model.Fighter;
import fight.server.model.constant.Status;
import fight.server.net.imp.core.AionConnection;
import fight.server.net.imp.core.AionPacketHandler.AionServerKind;
import fight.server.net.imp.packet.server.SM_FIGHT_INFO;
import fight.server.script.FightingScript;
import fight.server.util.CooldownId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

/**
 *
 * @author caoxin
 */
public class SingleArenaThread extends ObjectLock implements Runnable {

    private Logger logger = LoggerFactory.getLogger(SingleArenaThread.class);
    private Fighter fighter;
    private FightingScript fightingScript;
    private SingleArenaManager singleArenaManager;
    private CooldownId cooldownId;
    private AionConnection client;

    public SingleArenaThread(Fighter fighter, ApplicationContext ac, AionConnection client) {
        this.fighter = fighter;
        this.fightingScript = (FightingScript) ac.getBean("fightingScript");
        this.singleArenaManager = ac.getBean(SingleArenaManager.class);
        this.client = client;
    }

    @Override
    public void run() {
        fighter.isFightingAndK = true;
        onceAction();
        logger.info(fighter.toString());
        client.sendPacket(new SM_FIGHT_INFO(AionServerKind.SM_FIGHT_INFO.getOpcode(), fighter, fighter.getTargetFigher()));
        if (fighter.getLife() <= 0 || fighter.getTargetFigher().getLife() <= 0) { // 战斗结束时
            singleArenaManager.removeFighter(fighter.getName());
            if (fighter.getLife() > 0) {
                fighter.status = Status.SUCCESS;
            } else {
                fighter.status = Status.FAILURE;
            }
        }
        fighter.getCooldownCollection().setCoolDown(cooldownId);
        fighter.isFightingAndK = false;
    }

    private void onceAction() {
        if (cooldownId.equals(CooldownId.PHY_ATK)) {
            fightingScript.oncePhyAtk(fighter.getFightPropertyCountAndValueMap(), fighter.getTargetFigher().getFightPropertyCountAndValueMap());
        }
        fighter.getTargetFigher().setFightPropertyByMap();
    }

    public CooldownId getCooldownId() {
        return cooldownId;
    }

    public void setCooldownId(CooldownId cooldownId) {
        this.cooldownId = cooldownId;
    }
}