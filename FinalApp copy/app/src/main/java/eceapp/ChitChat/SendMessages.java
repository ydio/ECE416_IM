package eceapp.ChitChat;

/**
 * Created by lydiasainsbury on 6/04/17.
 * This is where you send messages.
 */

import java.util.ArrayList;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class SendMessages extends Activity {

    private ListView mList;
    private MyCustomAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_messages);

        TextView groupName = (TextView) findViewById(R.id.groupID);
//        System.out.println(AddUser.user);
//        System.out.println(AddUser.user.currGroup);
        System.out.println("2: " + AddUser.user.groupNames.get(AddUser.user.currGroup).groupName);

//        groupName.setText(AddUser.user.groupNames.get(AddUser.user.currGroup).groupName);
//        groupName.setText("Group ID: " + CreateGroup.group.groupName.getBytes().toString());

        final EditText editText = (EditText) findViewById(R.id.editText);
        Button send = (Button) findViewById(R.id.send_button);

        //relate the listView from java to the one created in xml
        mList = (ListView) findViewById(R.id.list);
        mAdapter = new MyCustomAdapter(this, AddUser.user.groupNames.get(AddUser.user.currGroup).rcvdMessages);
        mList.setAdapter(mAdapter);

        // connect to the server
        new connectTask().execute("");

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String message = editText.getText().toString();

                //add the text in the arrayList
//                CreateGroup.group.addMessage(message);
                //arrayList.add("c: " + message);

                //sends the message to the server
                if (AddUser.user != null) {
                    AddUser.user.sendMessage(message);
                }

                //refresh the list
                mAdapter.notifyDataSetChanged();
                editText.setText("");
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

    public class connectTask extends AsyncTask<String,String,User> {

        @Override
        protected User doInBackground(String... message) {
            //we create a User object and
            AddUser.user.addMessageListener(new User.OnMessageReceived() {
                @Override
                //here the messageReceived method is implemented
                public void messageReceived(String message) {
                    //System.out.println("in message recieved? ");
                    //this method calls the onProgressUpdate
                    publishProgress(message);
                }
            });
            AddUser.user.run();

            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);

            //in the arrayList we add the messaged received from server
            AddUser.user.groupNames.get(AddUser.user.currGroup).addMessage(values[0]);

            // notify the adapter that the data set has changed. This means that new message received
            // from server was added to the list
            mAdapter.notifyDataSetChanged();
        }
    }

}

