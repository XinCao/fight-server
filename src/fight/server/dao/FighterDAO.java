package fight.server.dao;

import fight.server.model.Fighter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;

/**
 *
 * @author caoxin
 */
public class FighterDAO extends NamedParameterJdbcDaoSupport {

    private static final String insertSql = "INSERT INTO fighter (name, life, phy_atk, mag_atk, phy_def, mag_def, crit, hit, dodge) VALUES (:name, :life, :phy_atk, :mag_atk, :phy_def, :mag_def, :crit, :hit, :dodge)";
    private static final String deleteSql = "DELETE FROM fighter WHERE name=:name";
    private static final String updateSql = "UPDATE fighter SET life=:life, phy_atk=:phy_atk, mag_atk=:mag_atk, phy_def=:phy_def, mag_def=:mag_def, crit=:crit, hit=:hit, dodge=:dodge where name=:name";
    private static final String selectSql = "SELECT name, life, phy_atk, mag_atk, phy_def, mag_def, crit, hit, dodge FROM fighter WHERE name=:name";
    private static final String selectSqlList = "SELECT name, life, phy_atk, mag_atk, phy_def, mag_def, crit, hit, dodge FROM fighter";

    public void insertFighter(Fighter fighter) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("name", fighter.getName());
        params.put("life", fighter.getLife());
        params.put("phy_atk", fighter.getPhyAtk());
        params.put("mag_atk", fighter.getMagAtk());
        params.put("phy_def", fighter.getPhyDef());
        params.put("mag_def", fighter.getMagDef());
        params.put("crit", fighter.getCrit());
        params.put("hit", fighter.getHit());
        params.put("dodge", fighter.getDodge());
        this.getNamedParameterJdbcTemplate().update(insertSql, params);
    }

    public void deleteFighter(String name) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("name", name);
        this.getNamedParameterJdbcTemplate().update(deleteSql, params);
    }

    public void updateFigther(Fighter fighter) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("name", fighter.getName());
        params.put("life", fighter.getLife());
        params.put("phy_atk", fighter.getPhyAtk());
        params.put("mag_atk", fighter.getMagAtk());
        params.put("phy_def", fighter.getPhyDef());
        params.put("mag_def", fighter.getMagDef());
        params.put("crit", fighter.getCrit());
        params.put("hit", fighter.getHit());
        params.put("dodge", fighter.getDodge());
        this.getNamedParameterJdbcTemplate().update(updateSql, params);
    }

    public Fighter selectFigther(String name) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("name", name);
        return this.getNamedParameterJdbcTemplate().queryForObject(selectSql, params, new FighterMapper());
    }

    private class FighterMapper implements RowMapper<Fighter> {

        @Override
        public Fighter mapRow(ResultSet rs, int i) throws SQLException {
            Fighter fighter = new Fighter(rs.getString("name"));
            fighter.setLife(rs.getInt("life"));
            fighter.setPhyAtk(rs.getInt("phy_atk"));
            fighter.setMagAtk(rs.getInt("mag_atk"));
            fighter.setPhyDef(rs.getInt("phy_def"));
            fighter.setMagDef(rs.getInt("mag_def"));
            fighter.setCrit(rs.getInt("crit"));
            fighter.setHit(rs.getInt("hit"));
            fighter.setDodge(rs.getInt("dodge"));
            return fighter;
        }
    }

    public List<Fighter> selectFighterList() {
        Map<String, Object> params = new HashMap<String, Object>();
        return this.getNamedParameterJdbcTemplate().query(selectSqlList, params, new FighterMapper());
    }
}