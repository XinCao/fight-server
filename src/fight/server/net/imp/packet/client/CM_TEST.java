package fight.server.net.imp.packet.client;

import fight.server.net.imp.core.AionConnection;
import java.nio.ByteBuffer;

/**
 *
 * @author caoxin
 */
public class CM_TEST extends AionClientPacket{
    
    private String name;

    public CM_TEST(ByteBuffer buf, AionConnection client, Integer opcode) {
        super(buf, client, opcode);
    }

    @Override
    protected void readImpl() {
        name = this.readS();
    }

    @Override
    protected void runImpl() {
        System.out.println("client connection name = {" + name + "} ok!");
    }
}
