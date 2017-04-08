package eceapp.ChitChat;

import java.util.ArrayList;

/**
 * Created by Abby.
 */

public class Group {
    String groupName;
    ArrayList<User> members; // names of the people in the group
    ArrayList<String> rcvdMessages;

    Group(String name) {
        this.groupName = name;
    }

    void addMember(User user) {
        members.add(user);
    }

    void addMessage(String message) {
        rcvdMessages.add(message);
    }
}
