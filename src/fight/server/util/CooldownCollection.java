package fight.server.util;

import fight.server.model.Fighter;
import java.util.EnumMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author caoxin
 */
public class CooldownCollection {

    private static Logger logger = LoggerFactory.getLogger(CooldownCollection.class);
    private Map<CooldownId, CoolDownInfo> coolmap = new EnumMap<CooldownId, CoolDownInfo>(CooldownId.class);
    private Fighter fight;

    public static class CoolDownInfo {

        public int cur;
        public int interval;

        public CoolDownInfo(int cur, int interval) {
            this.cur = cur;
            this.interval = interval;
        }
    }

    public CooldownCollection(Fighter fight) {
        this.fight = fight;
    }

    public Map<CooldownId, CoolDownInfo> getCoolMap() {
        return coolmap;
    }

    /**
     * 设置冷却
     *
     * @param coolid
     * @return
     */
    public void setCoolDown(CooldownId coolid) {
        int curTimeSecond = GameTime.getInstance().currentTimeSecond();
        CoolDownInfo cooldownInfo;
        if (!coolmap.containsKey(coolid)) {
            cooldownInfo = new CoolDownInfo(coolid.interval() + curTimeSecond, coolid.interval());
            coolmap.put(coolid, cooldownInfo);
        } else {
            cooldownInfo = coolmap.get(coolid);
            cooldownInfo.cur = curTimeSecond + cooldownInfo.interval;
        }
    }

    /**
     * 设置冷却（仅在初始化冷却和对冷却进行重置时调用）
     *
     * @param coolid
     */
    public void resetCoolDown(CooldownId coolid) {
        coolmap.put(coolid, new CoolDownInfo(0, coolid.interval()));
    }

    public void initCooldownCoolection() {
        for (CooldownId c : CooldownId.coolList) {
            this.setCoolDown(c);
        }
    }

    /**
     * 购买冷却时，调用
     *
     * @param coolid
     * @param decS 减少的秒数
     */
    public void resetCoolDown(CooldownId coolid, int decS) {
        coolmap.put(coolid, new CoolDownInfo(0, coolid.interval() > decS ? coolid.interval() - decS : 0));
    }

    /**
     * 获得冷却信息
     *
     * @param coolid
     * @return
     */
    public CoolDownInfo getCoolDown(CooldownId coolid) {
        if (!coolmap.containsKey(coolid)) {
            this.setCoolDown(coolid);
        }
        return coolmap.get(coolid);
    }

    /**
     * 测试是否在冷却中
     *
     * @param coolid
     * @return
     */
    public boolean inCoolDown(CooldownId coolid) {
        if (coolmap.containsKey(coolid)) {
//            logger.info(coolmap.get(coolid).cur + "\t" + GameTime.getInstance().currentTimeSecond);
            if (coolmap.get(coolid).cur < GameTime.getInstance().currentTimeSecond()) {
                return false;
            }
            return true;
        } else {
            resetCoolDown(coolid);
            logger.debug("企图测试一种不存在的CoolDown: name={}, id={}", coolid, coolid.count());
            return false;
        }
    }

    /**
     * 清除一个冷却
     *
     * @param player
     * @param coolid
     */
    public void clearCoolDown(CooldownId coolid) {
        this.resetCoolDown(coolid);
    }
}