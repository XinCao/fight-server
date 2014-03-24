package fight.server.model.constant;

public enum FightProperty {

    LIFE(1),
    PHY_ATK(2),
    MAG_ATK(3),
    PHY_DEF(4),
    MAG_DEF(5),
    CRIT(6),
    HIT(7),
    DODGE(8);
    public int count;

    private FightProperty(int count) {
        this.count = count;
    }
}