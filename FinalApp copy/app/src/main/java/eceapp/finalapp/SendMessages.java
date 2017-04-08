package eceapp.finalapp;

/**
 * Created by lydiasainsbury on 6/04/17.
 * This is where you send messages.
 */

import java.util.ArrayList;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

public class SendMessages extends Activity {

    private ListView mList;
    private MyCustomAdapter mAdapter;
    private Client mClient;
    private Group group = new Group(); // later change this to be multiple

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner dropdown = (Spinner)findViewById(R.id.theSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, group.groupNames);
        dropdown.setAdapter(adapter);

        group.rcvdMessages = new ArrayList<String>();

        final EditText editText = (EditText) findViewById(R.id.editText);
        Button send = (Button)findViewById(R.id.send_button);

        //relate the listView from java to the one created in xml
        mList = (ListView)findViewById(R.id.list);
        mAdapter = new MyCustomAdapter(this, group.rcvdMessages);
        mList.setAdapter(mAdapter);

        // connect to the server
        new connectTask().execute("");

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String message = editText.getText().toString();
                
                //add the text in the arrayList
                //arrayList.add("c: " + message);

                //sends the message to the server
                if (mClient != null) {
                    mClient.sendMessage(message);
                }

                //refresh the list
                mAdapter.notifyDataSetChanged();
                editText.setText("");
            }
        });

    }

    public class connectTask extends AsyncTask<String,String,Client> {

        @Override
        protected Client doInBackground(String... message) {
            //we create a Client object and
            mClient = new Client(new Client.OnMessageReceived() {
                @Override
                //here the messageReceived method is implemented
                public void messageReceived(String message) {
                    //this method calls the onProgressUpdate
                    publishProgress(message);
                }
            });
            mClient.run();

            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);

            //in the arrayList we add the messaged received from server
            group.rcvdMessages.add(values[0]);
            // notify the adapter that the data set has changed. This means that new message received
            // from server was added to the list
            mAdapter.notifyDataSetChanged();
        }
    }

}

