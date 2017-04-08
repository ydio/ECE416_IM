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
    static String name = null;
    private EditText groupName = null;
    private Button create = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_group);

//        groupName = (EditText) findViewById(R.id.groupName);
//        group = new Group(groupName.getText().toString());
        create = (Button) findViewById(R.id.createButton);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                groupName = (EditText) findViewById(R.id.groupName);
                group = new Group(groupName.getText().toString());
                group.addMember(AddUser.user);
                AddUser.user.groupNames.add(group);
                AddUser.user.currGroup++;

                Intent intent = new Intent(getBaseContext(), SendMessages.class);
                startActivity(intent);
            }
        });

        Button back = (Button) findViewById(R.id.home);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), SplashScreen.class);
                startActivity(intent);
            }
        });
    }
}

