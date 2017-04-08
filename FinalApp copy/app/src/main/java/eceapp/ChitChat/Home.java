package eceapp.ChitChat;

/**
 * Created by lydiasainsbury.
 * This is where the IP address is entered, and once this is entered it takes you to add user
 * so that you can enter your name.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Home extends Activity {
    private Button connect;
    private EditText ipAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        ipAddress = (EditText) findViewById(R.id.editText1);
        connect = (Button) findViewById(R.id.button1);
        connect.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                String ip = ipAddress.getText().toString();
                User.SERVERIP = ip;
                System.out.println("1");
                Intent intent = new Intent(getBaseContext(), AddUser.class);
                System.out.println("2");
                //Log.e("ServerIP", User.SERVERIP);
                startActivity(intent);
            }
        });
    }


}
