package fight.server.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author caoxin
 */
public enum CooldownId {

    PHY_ATK(0, false, false, 5),;
    public static final List<CooldownId> coolList = new ArrayList<CooldownId>();

    static {
        coolList.addAll(Arrays.asList(values()));
    }
    private int count; // 冷却编号
    private boolean saveToDB; // 是否需要存盘
    private boolean syncToClient; // 是否需要同步给客户端
    private int interval; // 默认冷却间隔时间，单位（秒）

    private CooldownId(int count, boolean saveToDB, boolean syncToClient, int interval) {
        this.count = count;
        this.saveToDB = saveToDB;
        this.syncToClient = syncToClient;
        this.interval = interval;
    }

    public int count() {
        return this.count;
    }

    public boolean isSave() {
        return this.saveToDB;
    }

    public boolean isSync() {
        return this.syncToClient;
    }

    public int interval() {
        return this.interval;
    }

    public static CooldownId fromInt(int count) {
        for (CooldownId CDId : values()) {
            if (CDId.count() == count) {
                return CDId;
            }
        }
        return PHY_ATK;
    }
}