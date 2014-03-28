package fight.server.net.imp.packet.server;

import fight.server.model.Fighter;
import fight.server.net.imp.core.AionConnection;
import java.nio.ByteBuffer;

/**
 * PVP战斗信息同步（未完成）
 * @author caoxin
 */
public class SM_FIGHT_INFO extends AionServerPacket {
    
    private Fighter fighter;
    private Fighter targetFighter;

    public SM_FIGHT_INFO(int opcode, Fighter figher, Fighter targetFighter) {
        super(opcode);
        this.fighter = figher;
        this.targetFighter = targetFighter;
    }

    @Override
    protected void writeImpl(AionConnection con, ByteBuffer buf) {
        this.writeS(buf, fighter.getName());
        this.writeD(buf, fighter.getLife());
        this.writeS(buf, targetFighter.getName());
        this.writeD(buf, targetFighter.getLife());
    }
    
}
