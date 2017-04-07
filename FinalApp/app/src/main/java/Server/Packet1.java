package Server;

import java.io.Serializable;
import Server.PacketTypes1;

public class Packet1 implements Serializable {
	private static final long serialVersionUID = -816057798889963281L;
	private PacketTypes1 type;
	private int seqno;
    private String loginName;
    private boolean isMe;
    private String time;
    private String message;
    
    public Packet1(PacketTypes1 type, int seqno, String loginName, boolean isMe, String time, String message) {
    	this.type = type;
    	this.seqno = seqno;
    	this.loginName = loginName;
    	this.isMe = isMe;
    	this.time = time;
    	this.message = message;
    }
    
    public Packet1 makeAck(Packet1 packet) {
    	packet.setType(PacketTypes1.ACK);
    	packet.setMessage(null);
    	return packet;
    }

    public PacketTypes1 getType() {
        return type;
    }

    public void setType(PacketTypes1 type) {
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
