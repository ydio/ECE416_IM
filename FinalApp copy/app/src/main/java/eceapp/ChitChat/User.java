package eceapp.ChitChat;

/**
 * Created by lydiasainsbury.
 */
import android.os.AsyncTask;
import android.util.Log;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

public class User {

    String name;
    private String serverMessage;
    public static  String SERVERIP ; // your computer IP
    // address
    public static final int SERVERPORT = 2222;
    private OnMessageReceived mMessageListener = null;
    private boolean mRun = false;

    static ArrayList<Group> groupNames = new ArrayList<Group>(); // list of the user's groups
    int currGroup = -1;

    PrintStream output;
    BufferedReader in;
    String myMessage;

    /**
     * Constructor of the class. OnMessagedReceived listens for the messages
     * received from server
     */
    public User(String clientName) {
        name = clientName;
    }

    void addMessageListener(OnMessageReceived listener) {
        //where does this happen?
        mMessageListener = listener;
    }

    public String addInfo (String message) {

        return message;
    }

    /**
     * Sends the message entered by client to the server
     *
     * @param message
     *            text entered by client
     */
    public void sendMessage(String message) {
        myMessage = message;
        new AsyncTask<String,String,Void>() {
            protected Void doInBackground(String... message) {
                if (output != null && !output.checkError()) {
                    Packet sentPacket = new Packet("", "MESSAGE", "yes", DateFormat.getDateTimeInstance().format(new Date()), myMessage);
                    output.println(sentPacket.tostring(sentPacket));
                    output.flush();
                }
                return null;
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, message);
    }

    public void stopClient() {
        mRun = false;
    }

    public void run() {

        mRun = true;

        try {

            // here you must put your computer's IP address.
            InetAddress serverAddr = InetAddress.getByName(SERVERIP);
            Log.e("serverAddr", serverAddr.toString());
            Log.e("TCP User", "C: Connecting...");

            // create a socket to make the connection with the server
            Socket socket = new Socket(serverAddr, SERVERPORT);
            Log.e("TCP Server IP", SERVERIP);
            try {

                // send the message to the server
                output = new PrintStream(socket.getOutputStream());

                Log.e("TCP User", "C: Sent.");

                Log.e("TCP User", "C: Done.");

                // receive the message which the server sends back
                in = new BufferedReader(new InputStreamReader(
                        socket.getInputStream()));


                // in this while the client listens for the messages sent by the
                // server
                while (mRun) {

                    String recivedPacket = in.readLine();
                    Packet newPacket = new Packet(recivedPacket);

                    serverMessage = newPacket.getMessage();

                    if (serverMessage != null && mMessageListener != null) {
                        // call the method messageReceived from MyActivity class
                        mMessageListener.messageReceived(serverMessage);
                    }
                    serverMessage = null;

                }

                Log.e("RESPONSE FROM SERVER", "S: Received Message: '"
                        + serverMessage + "'");

            } catch (Exception e) {

                Log.e("TCP", "S: Error", e);

            } finally {
                // the socket must be closed. It is not possible to reconnect to
                // this socket
                // after it is closed, which means a new socket instance has to
                // be created.
                socket.close();
            }

        } catch (Exception e) {

            Log.e("TCP", "C: Error", e);

        }

    }

    // Declare the interface. The method messageReceived(String message) will
    // must be implemented in the MyActivity
    // class at on asynckTask doInBackground
    public interface OnMessageReceived {
        public void messageReceived(String message);
    }
}