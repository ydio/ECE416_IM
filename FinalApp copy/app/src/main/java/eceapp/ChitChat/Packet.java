package eceapp.ChitChat;

/**
 * Created by lydiasainsbury on 6/04/17.
 */

import java.io.Serializable;

public class Packet implements Serializable {
    private static final long serialVersionUID = -816057798889963281L;

    private PacketTypes type;
    private int seqno;
    private String loginName;
    private boolean isMe;
    private String time;
    private String message;

    public Packet(PacketTypes type, int seqno, String loginName, boolean isMe, String time, String message) {
        this.type = type;
        this.seqno = seqno;
        this.loginName = loginName;
        this.isMe = isMe;
        this.time = time;
        this.message = message;
    }

    public Packet makeAck(Packet packet) {
        packet.setType(PacketTypes.ACK);
        packet.setMessage(null);
        return packet;
    }

    public String convertToBytes() {

        return null;
    }

    public PacketTypes getType() {
        return type;
    }

    public void setType(PacketTypes type) {
        this.type = type;
    }

    public int getSecqNo() {
        return seqno;
    }

    public void setSecqNo(int seqno) {
        this.seqno = seqno;
    }

    public String getName() {
        return loginName;
    }

    public void setName(String loginName) {
        this.loginName = loginName;
    }

    public boolean getIsme() {
        return isMe;
    }

    public void setMe(boolean isMe) {
        this.isMe = isMe;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

