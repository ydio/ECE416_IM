package eceapp.ChitChat;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by lydiasainsbury on 6/04/17.
 */

public class Packet {

    private String loginName;
    private String type;
    private String isMe;
    private String time;
    private String message;

    public Packet(String loginName, String type, String isMe, String time, String message) {
        this.type = type;
        this.loginName = loginName;
        this.isMe = isMe;
        this.time = time;
        this.message = message;
    }

    public Packet(String packet) {
        System.out.println("this is what Packet recives: " + packet);
        String[] info = packet.split("\\|");
        loginName = info[0];
        type = info[1];
        isMe = info[2];
        time = info[3];
        message = info[4];
        System.out.println("this is what Packet recives: " + message);
    }

    public String ServerMessages(String newtype, String clientName) {
        String myMessage = "";
        switch (newtype) {
            case "LOGIN":
                myMessage = "Enter your name.";
                break;
            case "WELCOME":
                myMessage = clientName + " has joined the chat";
                break;
            case "LEAVING":
                myMessage = clientName + " has left the chat";
                break;
            default:
                myMessage = "";
                break;
        }
        Packet packet = new Packet(clientName, newtype, "no", DateFormat.getDateTimeInstance().format(new Date()), myMessage);
        return packet.tostring(packet);
    }

    public String tostring(Packet packet) {
        String sentPacket = "";
        sentPacket = sentPacket.concat(packet.getName());
        sentPacket = sentPacket.concat("|");
        sentPacket = sentPacket.concat(packet.getType());
        sentPacket = sentPacket.concat("|");
        sentPacket = sentPacket.concat(packet.getIsme());
        sentPacket = sentPacket.concat("|");
        sentPacket = sentPacket.concat(packet.getTime());
        sentPacket = sentPacket.concat("|");
        sentPacket = sentPacket.concat(packet.getMessage());
        return sentPacket;
    }

    public String addInfo (String... info) {
        String packet = "";
        for(String value : info){
            packet.concat(value);
            packet.concat("|");
        }
        return packet;
    }

    public String[] getInfo (String packet) {
        String[] info = packet.split("|", 5);
        return info;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return loginName;
    }

    public void setName(String loginName) {
        this.loginName = loginName;
    }

    public String getIsme() {
        return isMe;
    }

    public void setMe(String isMe) {
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

