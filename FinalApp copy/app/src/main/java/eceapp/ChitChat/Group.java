package eceapp.ChitChat;

import java.util.ArrayList;

/**
 * Created by Abby.
 */

public class Group {
    String groupName = null;
    ArrayList<User> members = new ArrayList<>(); // names of the people in the group
    ArrayList<String> rcvdMessages = new ArrayList<>();

    Group(String name) {
        this.groupName = name;
    }

    void addMember(User user) {
        members.add(user);
    }

    void removeMember(User user) {
        members.remove(user);
    }

    void addMessage(String message) {
        rcvdMessages.add(message);
    }
}
