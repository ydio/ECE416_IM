package eceapp.finalapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

    private Button create;
    private EditText gName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        gName = (EditText) findViewById(R.id.groupName);
        create = (Button) findViewById(R.id.createButton);
        create.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String groupName = gName.getText().toString();
                Group group = new Group(groupName);
//                Intent intent = new Intent(getBaseContext(), newGroup());
//                startActivity(intent);
            }
        });
    }

    private void newGroup() {

    }

    /**
     // TODO Auto-generated method stub
     String ip = ipAddress.getText().toString();
     Client.SERVERIP = ip;
     Intent intent = new Intent(getBaseContext(), SplashScreen.class);
     //Log.e("ServerIP", Client.SERVERIP);
     startActivity(intent);
     */

}

class JoinGroup extends Activity {

}