package fight.server.service;

/**
 *
 * @author caoxin
 */
public enum MessageInfo {

    I_HAVE_JOINED(1, "Me已经加入战斗，或者角色进入离线战斗中！"),
    HE_HAVE_JOINED(2, "He已经加入战斗，或者角色进入离线战斗中！"),
    OK(3, "OK"),
    ERROR(4, "出现错误，请即使联系GM！");
    private int no;
    private String content;

    private MessageInfo(int no, String content) {
        this.no = no;
        this.content = content;
    }

    public String getContentByNo(int no) {
        for (MessageInfo m : values()) {
            if (m.getNo() == no) {
                return m.getContent();
            }
        }
        return "";
    }

    public int getNo() {
        return no;
    }

    public String getContent() {
        return content;
    }
}
