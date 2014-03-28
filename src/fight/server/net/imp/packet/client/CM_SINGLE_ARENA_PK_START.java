package fight.server.net.imp.packet.client;

import fight.server.net.imp.core.AionConnection;
import static fight.server.net.imp.packet.client.AionClientPacket.ac;
import fight.server.thread.SingleArenaManager;
import java.nio.ByteBuffer;

/**
 *
 * @author caoxin
 */
public class CM_SINGLE_ARENA_PK_START extends AionClientPacket {

    private int status; // 1.start, 2.cancel
    private String nameMe;
    private String nameHe;
    private SingleArenaManager singleArenaManager = ac.getBean(SingleArenaManager.class);

    public CM_SINGLE_ARENA_PK_START(ByteBuffer buf, AionConnection client, int opcode) {
        super(buf, client, opcode);
    }

    @Override
    protected void readImpl() {
        this.status = this.readC();
        this.nameMe = this.readS();
        this.nameHe = this.readS();
    }

    @Override
    protected void runImpl() {
        if (this.status == 1) {
            singleArenaManager.startFighting(nameMe, nameHe);
        } else if (this.status == 2) {
            singleArenaManager.stopFighting(nameMe);
        }
    }
}