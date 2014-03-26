package fight.server.net.imp.core;

import fight.server.net.imp.core.AionConnection.State;
import fight.server.net.imp.packet.client.AionClientPacket;
import fight.server.net.imp.packet.client.CM_TEST;
import fight.server.net.imp.packet.client.CM_SINGLE_ARENA_PK;
import fight.server.net.imp.packet.server.AionServerPacket;
import fight.server.net.imp.packet.server.SM_PROMPT_INFORMATION;
import fight.server.net.imp.packet.server.SM_TEST;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import org.apache.log4j.Logger;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

public class AionPacketHandler {

    private static final Logger log = Logger.getLogger(AionPacketHandler.class);

    public enum AionClientKind {

        CLIENT_TEST(0x0001, CM_TEST.class, State.CONNECTED),
        SINGLE_ARENA_PK_REQUEST(0x0002, CM_SINGLE_ARENA_PK.class, State.CONNECTED)
        ;
        public static final Map<Integer, AionClientKind> authedLoginAionClientKindMap = new HashMap<Integer, AionClientKind>();
        public static final Map<Integer, AionClientKind> connectedAionClientKindMap = new HashMap<Integer, AionClientKind>();
        public static final Map<Integer, AionClientKind> authedGGAionClientKindMap = new HashMap<Integer, AionClientKind>();

        static {
            for (AionClientKind ack : values()) {
                switch (ack.getState()) {
                    case AUTHED_LOGIN: {
                        authedLoginAionClientKindMap.put(ack.getOpcode(), ack);
                        break;
                    }
                    case CONNECTED: {
                        connectedAionClientKindMap.put(ack.getOpcode(), ack);
                        break;
                    }
                    case AUTHED_GG: {
                        authedGGAionClientKindMap.put(ack.getOpcode(), ack);
                        break;
                    }
                }
            }
        }
        private int opcode;
        private Class<AionClientPacket> clazz;
        private State state;

        private AionClientKind(int opcode, Class clazz, State state) {
            this.opcode = opcode;
            this.clazz = clazz;
            this.state = state;
        }

        public int getOpcode() {
            return this.opcode;
        }

        public Class<AionClientPacket> getClazz() {
            return this.clazz;
        }

        public State getState() {
            return this.state;
        }
    }

    /**
     * 获得客户端包
     * 
     * @param data
     * @param client
     * @return 
     */
    public static AionClientPacket handle(ByteBuffer data, AionConnection client) {
        AionClientPacket msg = null;
        State state = client.getState();
        int opcode = data.get() & 0xff;
        AionClientKind ack = null;
        switch (state) {
            case CONNECTED: {
                ack = AionClientKind.connectedAionClientKindMap.get(opcode);
                break;
            }
            case AUTHED_GG: {
                ack = AionClientKind.authedGGAionClientKindMap.get(opcode);
                break;
            }
            case AUTHED_LOGIN: {
                ack = AionClientKind.authedLoginAionClientKindMap.get(opcode);
                break;
            }
            default: {
                unknownPacket(state, opcode);
            }
        }
        if (ack != null) {
            try {
                Class<AionClientPacket> clazz = ack.getClazz();
                Constructor<AionClientPacket> constructor = clazz.getConstructor(ByteBuffer.class, AionConnection.class, Integer.class);
                msg = constructor.newInstance(data, client, ack.getOpcode());
            } catch (NoSuchMethodException ex) {
                log.warn(ex);
            } catch (SecurityException ex) {
                log.warn(ex);
            } catch (InstantiationException ex) {
                log.warn(ex);
            } catch (IllegalAccessException ex) {
                log.warn(ex);
            } catch (IllegalArgumentException ex) {
                log.warn(ex);
            } catch (InvocationTargetException ex) {
                log.warn(ex);
            }
        }
        return msg;
    }

    private static void unknownPacket(State state, int id) {
        log.warn(String.format("Unknown packet recived from Aion client: 0x%02X state=%s", id, state.toString()));
    }

    public static enum AionServerKind {

        SERVER_TEST(0x0002, SM_TEST.class, State.CONNECTED),
        SM_PROMPT_INFORMATION(0x0003, SM_PROMPT_INFORMATION.class, State.CONNECTED)
        ;
        private int opcode;
        private Class<AionServerPacket> clazz;
        private State state;

        private AionServerKind(int opcode, Class clazz, State state) {
            this.opcode = opcode;
            this.clazz = clazz;
            this.state = state;
        }

        public int getOpcode() {
            return this.opcode;
        }

        public Class<AionServerPacket> getClazz() {
            return this.clazz;
        }

        public State getState() {
            return this.state;
        }
    }
}