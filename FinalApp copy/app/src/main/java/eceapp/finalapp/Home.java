package eceapp.finalapp;

/**
 * Created by lydiasainsbury.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
                Client.SERVERIP = ip;
                Intent intent = new Intent(getBaseContext(), SplashScreen.class);
                //Log.e("ServerIP", Client.SERVERIP);
                startActivity(intent);
            }
        });
    }


}
