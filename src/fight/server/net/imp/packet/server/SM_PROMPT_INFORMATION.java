package fight.server.net.imp.packet.server;

import fight.server.net.imp.core.AionConnection;
import fight.server.service.MessageInfo;
import java.nio.ByteBuffer;

/**
 * 提示消息（未添加i18n）
 * 
 * @author caoxin
 */
public class SM_PROMPT_INFORMATION extends AionServerPacket {
    
    private MessageInfo messageInfo;

    public SM_PROMPT_INFORMATION(int opcode, MessageInfo messageInfo) {
        super(opcode);
        this.messageInfo = messageInfo;
    }

    @Override
    protected void writeImpl(AionConnection con, ByteBuffer buf) {
        this.writeS(buf, messageInfo.getContent());
    }
}