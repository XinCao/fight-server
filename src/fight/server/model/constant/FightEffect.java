package fight.server.model.constant;

/**
 *
 * @author caoxin
 */
public enum FightEffect {

    NOT_EFFECT(-1),
    DOUBLE_HURT(1),
    DODGE(2),
    HIT(3),;
    private int value;

    private FightEffect(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}