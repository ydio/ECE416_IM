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


// Creates a new user, so that they can create/join a group and send messages.
public class AddUser extends Activity {

    static User user;
    private Button createUser;
    private EditText userName; // name of the client/user

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.add_client);

        userName = (EditText) findViewById(R.id.clientName);
        createUser = (Button) findViewById(R.id.createClient);
        createUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = new User(userName.getText().toString()); // initialise a new client
                Intent intent = new Intent(getBaseContext(), SplashScreen.class);
                startActivity(intent);
            }
        });
    }
}

