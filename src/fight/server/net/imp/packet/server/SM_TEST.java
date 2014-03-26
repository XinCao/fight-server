package fight.server.net.imp.packet.server;

import fight.server.net.imp.core.AionConnection;
import java.nio.ByteBuffer;

/**
 *
 * @author caoxin
 */
public class SM_TEST extends AionServerPacket {

    public SM_TEST(Integer opcode) {
        super(opcode);
    }

    @Override
    protected void writeImpl(AionConnection con, ByteBuffer buf) {
        this.writeS(buf, "hello world!");
    }
}