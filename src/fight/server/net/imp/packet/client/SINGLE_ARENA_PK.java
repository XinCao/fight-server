package fight.server.net.imp.packet.client;

import fight.server.net.imp.core.AionConnection;
import fight.server.thread.SingleArenaManager;
import fight.server.thread.SingleArenaManager.JoinResult;
import static fight.server.thread.SingleArenaManager.JoinResult.I_HAVE_JOINED;
import java.nio.ByteBuffer;

/**
 *
 * @author caoxin
 */
public class SINGLE_ARENA_PK extends AionClientPacket {

    private String nameMe;
    private String nameHe;
    private SingleArenaManager singleArenaManager = ac.getBean(SingleArenaManager.class);

    public SINGLE_ARENA_PK(ByteBuffer buf, AionConnection client, Integer opcode) {
        super(buf, client, opcode);
    }

    @Override
    protected void readImpl() {
        this.nameMe = this.readS();
        this.nameHe = this.readS();
    }

    @Override
    protected void runImpl() {
        JoinResult joinResult = singleArenaManager.joinSingleArena(nameMe, nameHe);
        switch (joinResult) {
            case I_HAVE_JOINED: {
                break;
            }
            case HE_HAVE_JOINED: {
                break;
            }
            case OK: {
                break;
            }
            case ERROR: {
                break;
            }
        }
        log.info("single arena join result is " + joinResult);
    }
}
