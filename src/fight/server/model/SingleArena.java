package fight.server.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author caoxin
 */
public class SingleArena {
    
    private Fighter[] fighters = new Fighter[2];
    private List<Fighter> fighter = new ArrayList<Fighter>();

    public void joinFighters(Fighter... fighter) {
        this.fighters[0] = fighter[0];
        this.fighters[1] = fighter[1];
    }
}
