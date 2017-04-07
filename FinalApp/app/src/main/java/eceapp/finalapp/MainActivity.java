package eceapp.finalapp;

/**
 * Created by lydiasainsbury on 6/04/17.
 */

import java.util.ArrayList;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import java.text.DateFormat;
import java.util.Date;

public class MainActivity extends Activity {

    private ListView mList;
    private ArrayList<String> arrayList;
    private MyCustomAdaptor mAdapter;
    private Client mClient;
    private String loginName;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        arrayList = new ArrayList<String>();

        final EditText editText = (EditText) findViewById(R.id.editText);
        Button send = (Button)findViewById(R.id.send_button);

        //relate the listView from java to the one created in xml
        mList = (ListView)findViewById(R.id.list);
        mAdapter = new MyCustomAdaptor(this, arrayList);
        mList.setAdapter(mAdapter);

        // connect to the server
        new connectTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, null);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("here", "cool");

                String message = editText.getText().toString();
                Packet packet = new Packet(PacketTypes.MESSAGE, 0, loginName, true, DateFormat.getDateTimeInstance().format(new Date()), message);

                //add the text in the arrayList
                //arrayList.add("c: " + message);

                //sends the message to the server
                if (mClient != null) {
                    Log.e(message, "cool");
                    mClient.sendMessage(packet);
                }

                //refresh the list
                mAdapter.notifyDataSetChanged();
                editText.setText("");
            }
        });

    }

    public class connectTask extends AsyncTask<Packet,Packet,Client> {

        @Override
        protected Client doInBackground(Packet... packet) {

            //we create a Client object and
            mClient = new Client(new Client.OnMessageReceived() {
                @Override
                //here the messageReceived method is implemented
                public void messageReceived(Packet packet) {
                    //this method calls the onProgressUpdate
                    publishProgress(packet);

                }
            });
            mClient.run();

            return null;
        }

        @Override
        protected void onProgressUpdate(Packet... values) {
            super.onProgressUpdate(values);

            //in the arrayList we add the messaged received from server
            //arrayList.add(values[0]);
            // notify the adapter that the data set has changed. This means that new message received
            // from server was added to the list
            mAdapter.notifyDataSetChanged();
        }
    }

}

