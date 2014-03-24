package fight.server.script;

import fight.server.model.constant.FightEffect;
import java.util.List;
import java.util.Map;

/**
 *
 * @author caoxin
 */
public interface FightingScript {

    public List<FightEffect> oncePhyAtk(Map<Integer, Integer> fighterA, Map<Integer, Integer> fighterB);
}