package fight.server.service;

import fight.server.dao.FighterDAO;
import fight.server.model.Fighter;
import java.util.List;

/**
 *
 * @author caoxin
 */
public class FighterService {

    private FighterDAO fighterDAO;

    public void insertFighter(Fighter fighter) {
        this.fighterDAO.insertFighter(fighter);
    }

    public void deleteFighter(String name) {
        this.fighterDAO.deleteFighter(name);
    }

    public void updateFighter(Fighter fighter) {
        this.fighterDAO.updateFigther(fighter);
    }

    public Fighter selectFighter(String name) {
        return this.fighterDAO.selectFigther(name);
    }

    public List<Fighter> loadAllFighter() {
        return this.fighterDAO.selectFighterList();
    }

    public void setFighterDAO(FighterDAO fighterDAO) {
        this.fighterDAO = fighterDAO;
    }
}