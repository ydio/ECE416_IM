package eceapp.finalapp;

import java.util.ArrayList;

/**
 * Created by Abby.
 */

public class Group {
    String groupName;
    ArrayList<String> rcvdMessages;

    Group(String name) {
        this.groupName = name;
    }

    void addMessage(String message) {
        rcvdMessages.add(message);
    }
}
