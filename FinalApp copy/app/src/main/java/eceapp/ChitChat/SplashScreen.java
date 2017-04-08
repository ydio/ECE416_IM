package eceapp.ChitChat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
                Intent intent = new Intent(getBaseContext(), CreateGroup.class);
                startActivity(intent);
            }
        });

        Button joinGroupButton = (Button) findViewById(R.id.join);
        joinGroupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), JoinGroup.class);
                startActivity(intent);
            }
        });
    }
}