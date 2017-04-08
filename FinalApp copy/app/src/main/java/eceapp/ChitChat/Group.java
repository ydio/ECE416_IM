package eceapp.ChitChat;

import java.util.ArrayList;

/**
 * Created by Abby.
 */

public class Group {
    String groupName;
    ArrayList<String> members; // names of the people in the group
    ArrayList<String> rcvdMessages;

    Group(String name) {
        this.groupName = name;
    }

    void addMember(String member) {
        members.add(member);
    }

    void addMessage(String message) {
        rcvdMessages.add(message);
    }
}
