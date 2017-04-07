package eceapp.finalapp;

/**
 * Created by lydiasainsbury on 6/04/17.
 */

import android.util.Log;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import android.os.AsyncTask;

public class Client {

    private Packet serverMessage;
    public static  String SERVERIP ; // your computer IP
    // address
    public static final int SERVERPORT = 8081;
    private OnMessageReceived mMessageListener = null;
    private boolean mRun = false;

    //PrintStream out;
    //BufferedReader in;

    private ObjectOutputStream clientOutput = null;
    private ObjectInputStream clientInput = null;

    Packet packet;

    /**
     * Constructor of the class. OnMessagedReceived listens for the messages
     * received from server
     */
    public Client(OnMessageReceived listener) {
        mMessageListener = listener;

    }

    /**
     * Sends the message entered by client to the server
     *
     * @param packet
     *            text entered by client
     */
    public void sendMessage(Packet packet) {
        Log.e("whhhy", "C: not yet.");
        new AsyncTask<Packet,String,Void> () {
            protected Void doInBackground(Packet... packet) {
                // something you know that will take a few seconds
                try {
                    if (clientOutput != null) {
                        clientOutput.writeObject(packet);
                        //clientOutput.flush();
                    }
                } catch (IOException error) {
                    System.err.println("IOException:  " + error);
                    System.exit(1);
                }
                return null;
            }

        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, packet);

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
            Log.e("TCP Client", "C: Connecting...");

            // create a socket to make the connection with the server
            Socket clientSoc = new Socket(serverAddr, SERVERPORT);
            Log.e("TCP Server IP", SERVERIP);
            try {

                // send the message to the server
                //out = new PrintStream(socket.getOutputStream());
                clientOutput = new ObjectOutputStream(clientSoc.getOutputStream());

                Log.e("TCP Client", "C: Sent.");

                Log.e("TCP Client", "C: Done.");

                // receive the message which the server sends back
                //in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                clientInput = new ObjectInputStream(clientSoc.getInputStream());

                Log.e("lol", "what");
                // in this while the client listens for the messages sent by the
                // server
                while (mRun) {
                    //Log.e("stuff", "what");
                    serverMessage = (Packet) clientInput.readObject();

                    if (serverMessage != null && mMessageListener != null) {
                        // call the method messageReceived from MyActivity class
                        mMessageListener.messageReceived(serverMessage);
                        Log.e("name", "entered");
                        //break;
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
                clientSoc.close();
            }

        } catch (Exception e) {

            Log.e("TCP", "C: Error", e);

        }

    }

    // Declare the interface. The method messageReceived(String message) will
    // must be implemented in the MainActivity
    // class at on asynckTask doInBackground
    public interface OnMessageReceived {
        public void messageReceived(Packet packet);
    }
}

