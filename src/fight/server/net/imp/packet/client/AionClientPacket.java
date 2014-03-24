package fight.server.net.imp.packet.client;

import com.aionemu.commons.network.packet.BaseClientPacket;
import fight.server.net.imp.core.AionConnection;
import fight.server.net.imp.packet.server.AionServerPacket;
import fight.server.service.AC;

import java.nio.ByteBuffer;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

public abstract class AionClientPacket extends BaseClientPacket<AionConnection> {

    private static final Logger log = Logger.getLogger(AionClientPacket.class);
    protected static final ApplicationContext ac = AC.getAC();

    protected AionClientPacket(ByteBuffer buf, AionConnection client, int opcode) {
        super(buf, opcode);
        this.setConnection(client);
    }

    @Override
    public final void run() {
        try {
            this.runImpl();
        } catch (Throwable e) {
            log.error("error handling client opcode = {} message" + getConnection().getIP());
        }
    }

    protected void sendPacket(AionServerPacket msg) {
        this.getConnection().sendPacket(msg);
    }
}