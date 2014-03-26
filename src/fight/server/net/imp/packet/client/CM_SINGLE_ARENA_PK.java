package fight.server.net.imp.packet.client;

import fight.server.net.imp.core.AionConnection;
import fight.server.net.imp.core.AionPacketHandler.AionServerKind;
import fight.server.net.imp.packet.server.SM_PROMPT_INFORMATION;
import fight.server.service.MessageInfo;
import fight.server.thread.SingleArenaManager;
import fight.server.thread.SingleArenaManager.JoinResult;
import static fight.server.thread.SingleArenaManager.JoinResult.I_HAVE_JOINED;
import java.nio.ByteBuffer;

/**
 *
 * @author caoxin
 */
public class CM_SINGLE_ARENA_PK extends AionClientPacket {

    private String nameMe;
    private String nameHe;
    private SingleArenaManager singleArenaManager = ac.getBean(SingleArenaManager.class);

    public CM_SINGLE_ARENA_PK(ByteBuffer buf, AionConnection client, Integer opcode) {
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
        MessageInfo messageInfo = MessageInfo.OK;
        switch (joinResult) {
            case I_HAVE_JOINED: {
                messageInfo = MessageInfo.I_HAVE_JOINED;
                break;
            }
            case HE_HAVE_JOINED: {
                messageInfo = MessageInfo.HE_HAVE_JOINED;
                break;
            }
            case OK: {
                messageInfo = MessageInfo.OK;
                break;
            }
            case ERROR: {
                messageInfo = MessageInfo.ERROR;
                break;
            }
        }
        this.getConnection().sendPacket(new SM_PROMPT_INFORMATION(AionServerKind.SM_PROMPT_INFORMATION.getOpcode(), messageInfo));
    }
}