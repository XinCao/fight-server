package fight.server.net.imp.packet.client;

import fight.server.model.Fighter;
import fight.server.net.imp.core.AionConnection;
import fight.server.service.FighterService;
import java.nio.ByteBuffer;

/**
 *
 * @author caoxin
 */
public class SINGLE_ARENA_PK_REQUEST extends AionClientPacket {

    private String nameMe;
    private String nameHe;
    private FighterService fighterService = ac.getBean(FighterService.class);

    public SINGLE_ARENA_PK_REQUEST(ByteBuffer buf, AionConnection client, Integer opcode) {
        super(buf, client, opcode);
    }

    @Override
    protected void readImpl() {
        this.nameMe = this.readS();
        this.nameHe = this.readS();
    }

    @Override
    protected void runImpl() {
        Fighter fighterMe = fighterService.selectFighter(nameMe);
        Fighter fighterHe = fighterService.selectFighter(nameHe);
        if (fighterHe == null) {
            // 用户不存在
        }
    }
}
