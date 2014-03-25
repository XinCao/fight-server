package fight.server.script;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import fight.server.model.constant.FightEffect;
import fight.server.model.constant.FightProperty;

/**
 * fighterA 为估计者， fighterB为被攻击对象
 *
 * @author caoxin
 */
public class FightingScriptImp implements FightingScript {

    private static final int MAX_CRIT = 10000;
    private static int DOUBLE = 2;
    private static int LIFE_KEY = FightProperty.LIFE.count;
    private static int PHY_ATK_KEY = FightProperty.PHY_ATK.count;
    private static int MAG_ATK_KEY = FightProperty.MAG_ATK.count;
    private static int PHY_DEF_KEY = FightProperty.PHY_DEF.count;
    private static int MAG_DEF_KEY = FightProperty.MAG_DEF.count;
    private static int CRIT_KEY = FightProperty.CRIT.count;
    private static int HIT_KEY = FightProperty.HIT.count;
    private static int DODGE_KEY = FightProperty.DODGE.count;

    /**
     * 普通攻击
     *
     * @param fighterA
     * @param fighterB
     * @return 返回战斗效果信息
     */
    public List<FightEffect> oncePhyAtk(Map<Integer, Integer> fighterA, Map<Integer, Integer> fighterB) {
        List<FightEffect> fightEffects = new ArrayList<FightEffect>();
        if (canHit(fighterA.get(HIT_KEY), fighterB.get(DODGE_KEY))) {
            fightEffects.add(FightEffect.HIT);
        } else {
            fightEffects.add(FightEffect.DODGE);
            return fightEffects;
        }
        int d = 1;
        if (canCrit(fighterA.get(CRIT_KEY))) {
            fightEffects.add(FightEffect.DOUBLE_HURT);
            d = DOUBLE;
        }
        onceAtk(fighterA, fighterB, d);
        return fightEffects;
    }

    private boolean canCrit(int n) {
        int r = (int) (Math.random() * MAX_CRIT) + 1;
        if (r <= n) {
            return true;
        }
        return false;
    }

    public boolean canHit(int hit, int dodge) {
        if (hit + dodge == 0) {
            return true;
        }
        if (hit == 0){
            return true;
        } else if (dodge == 0) {
            return false;
        }
        int r = (int) (Math.random() * (hit + dodge)) + 1;
        if (r <= hit) {
            return true;
        }
        return false;
    }

    public void onceAtk(Map<Integer, Integer> fighterA, Map<Integer, Integer> fighterB, int n) {
        int atkSubDef = fighterA.get(PHY_ATK_KEY) - fighterB.get(PHY_DEF_KEY);
        if (atkSubDef > 0) {
            fighterB.put(LIFE_KEY, fighterB.get(LIFE_KEY) - atkSubDef * n);
        }
    }
}