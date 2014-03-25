package fight.server.model;

import fight.server.model.constant.Status;
import fight.server.model.constant.FightProperty;
import fight.server.util.CooldownCollection;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author caoxin
 */
public class Fighter implements Comparable<Fighter>, Cloneable {

    private int id;
    private CooldownCollection cooldownCollection;
    private String name;
    private int life;
    private int phyAtk;
    private int magAtk;
    private int phyDef;
    private int magDef;
    private int crit;
    private int combatPower;
    private int hit;
    private int dodge;
    public Status status;
    public boolean isFighting = false;
    private Map<Integer, Integer> m = new HashMap<Integer, Integer>();
    private Fighter targetFigher;
    private boolean isAutoFight = false;
    private boolean isOneAction = false;

    public Fighter(String name) {
        this.name = name;
        this.cooldownCollection = new CooldownCollection(this);
        this.status = Status.DEFAULT;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public int getPhyAtk() {
        return phyAtk;
    }

    public void setPhyAtk(int phyAtk) {
        this.phyAtk = phyAtk;
    }

    public int getMagAtk() {
        return magAtk;
    }

    public void setMagAtk(int magAtk) {
        this.magAtk = magAtk;
    }

    public int getPhyDef() {
        return phyDef;
    }

    public void setPhyDef(int phyDef) {
        this.phyDef = phyDef;
    }

    public int getMagDef() {
        return magDef;
    }

    public void setMagDef(int magDef) {
        this.magDef = magDef;
    }

    public int getCrit() {
        return crit;
    }

    public void setCrit(int crit) {
        this.crit = crit;
    }

    public int getCombatPower() {
        this.setCombatPower();
        return combatPower;
    }

    private void setCombatPower() {
        this.combatPower = this.life + this.phyAtk + this.phyDef + this.magAtk * 2 + this.magDef * 2;
    }

    public int getHit() {
        return hit;
    }

    public void setHit(int hit) {
        this.hit = hit;
    }

    public int getDodge() {
        return dodge;
    }

    public void setDodge(int dodge) {
        this.dodge = dodge;
    }

    public String getName() {
        return name;
    }

    public CooldownCollection getCooldownCollection() {
        return cooldownCollection;
    }

    public Map<Integer, Integer> getFightPropertyCountAndValueMap() {
        m.put(FightProperty.LIFE.count, getLife());
        m.put(FightProperty.PHY_ATK.count, getPhyAtk());
        m.put(FightProperty.MAG_ATK.count, getMagAtk());
        m.put(FightProperty.PHY_DEF.count, getPhyDef());
        m.put(FightProperty.MAG_DEF.count, getMagDef());
        m.put(FightProperty.CRIT.count, getCrit());
        m.put(FightProperty.HIT.count, getHit());
        m.put(FightProperty.DODGE.count, getDodge());
        return m;
    }

    /**
     * 暂时只会改变，血
     */
    public void setFightPropertyByMap() {
        this.life = m.get(FightProperty.LIFE.count);
    }

    public Fighter getTargetFigher() {
        return this.targetFigher;
    }

    public void setTargetFigher(Fighter targetFigher) {
        this.targetFigher = targetFigher;
    }

    public boolean isAutoFight() {
        return isAutoFight;
    }

    public void autoFight(boolean isAutoFight) {
        this.isAutoFight = isAutoFight;
    }

    public boolean isOneAction() {
        return isOneAction;
    }

    public void oneAction(boolean isOneAction) {
        this.isOneAction = isOneAction;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + (this.name != null ? this.name.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Fighter other = (Fighter) obj;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(Fighter o) {
        return this.getCombatPower() - o.getCombatPower();
    }

    @Override
    public String toString() {
        return "Fighter{" + "name=" + name + ", life=" + life + ", phyAtk=" + phyAtk + ", magAtk=" + magAtk + ", phyDef=" + phyDef + ", magDef=" + magDef + ", crit=" + crit + ", hit=" + hit + ", dodge=" + dodge + '}';
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}