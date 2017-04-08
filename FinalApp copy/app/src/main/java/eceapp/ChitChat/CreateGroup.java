package eceapp.ChitChat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Abby.
 */

// Creates a new group, so that messages can be sent.
public class CreateGroup extends Activity {

    static Group group;
    private EditText groupName;
    private Button create;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_group);

        groupName = (EditText) findViewById(R.id.groupName);
        create = (Button) findViewById(R.id.createButton);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = groupName.getText().toString();
                group = new Group(name);
                group.addMember(AddUser.user);
                JoinGroup.groupNames.add(group);

                Intent intent = new Intent(getBaseContext(), SendMessages.class);
                startActivity(intent);
            }
        });
    }
}

