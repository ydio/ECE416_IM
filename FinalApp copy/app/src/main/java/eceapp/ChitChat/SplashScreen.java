package eceapp.ChitChat;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;

/**
 * Created by Abby.
 */

// Welcome screen (after IP has been entered). Here you can choose to create or join a group.
public class SplashScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        Button createGroupButton = (Button) findViewById(R.id.Create);
        createGroupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Group group = new Group
                // create group for IMs.

            }
        });

        Button joinGroupButton = (Button) findViewById(R.id.join);
        joinGroupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // join pre-existing group.
            }
        });
    }
}

// Creates a new group, so that messages can be sent.
class CreateGroup extends Activity {

    static Group group;
    private Button create;
    private EditText gName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_group);

        gName = (EditText) findViewById(R.id.groupName);
        create = (Button) findViewById(R.id.createButton);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String groupName = gName.getText().toString();
                group = new Group(groupName);
                JoinGroup.groupNames.add(group);
//                Intent intent = new Intent(getBaseContext(), newGroup());
//                startActivity(intent);
            }
        });
    }
}

class JoinGroup extends ListActivity {

    static ArrayList<Group> groupNames = new ArrayList<Group>(); // list of group names
    private ArrayAdapter<Group> adapter;
    private Button join;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.join_group);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, groupNames);
        setListAdapter(adapter);
    }

    public void addClientToGroup(View v) {
        Client clientName = clientThread.client;
        CreateGroup.group.addMember(clientName.name);
//        groupNames.add(new Client(name)); // add client name
        adapter.notifyDataSetChanged();
    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.join_group);
////        gName = (EditText) findViewById(R.id.groupName);
//
//        for (int i = 0; i < groupNames.size(); i++) {
//            // Display the groups as buttons so they can be [clicked and] joined.
//            Button group = new Button(this);
//            group.setText(groupNames.get(i).groupName);
//            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linLayout);
//            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
//                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//            linearLayout.addView(group, layoutParams);
//
//            // Add listener so that when the user selects a group, they can join
//            group.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
////                String groupName = gName.getText().toString();
////                Group group = new Group(groupName);
////                    Intent intent = new Intent(getBaseContext(), newGroup());
////                    startActivity(intent);
//                }
//            });
//        }
//    }

}