package Server;

//import java.io.PrintStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.util.Date;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Client implements Runnable {

	// The client socket
	private static Socket clientSoc = null;
	private static ObjectOutputStream clientOutput = null;
	private static ObjectInputStream clientInput = null;
	private static BufferedReader inputText = null;
	//private String[] groups = null;

	private static String loginName;

	public static void main(String[] args) {

		int portNumber = 8081;
		String hostName = "localhost";
		String message = null;
		Packet1 packet = null;

		//
		try {
			clientSoc = new Socket(hostName, portNumber);
			inputText = new BufferedReader(new InputStreamReader(System.in));
			clientOutput = new ObjectOutputStream(clientSoc.getOutputStream());
			clientInput = new ObjectInputStream(clientSoc.getInputStream());
		} catch (UnknownHostException error) {
			System.err.println("UnknownHostException " + error);
			System.exit(1);
		} catch (IOException error) {
			System.err.println("IOException:  " + error);
			System.exit(1);
		}
		
		try {
			/* Create a thread to read from the server. */
			new Thread(new Client()).start();
			System.out.println("Enter your name.");
			loginName = inputText.readLine().trim();
			System.out.println(loginName + " has entered chat");
			
			packet = new Packet1(PacketTypes1.LOGIN, 0, loginName, true, DateFormat.getDateTimeInstance().format(new Date()), null);
			clientOutput.writeObject(packet);
			
			boolean serverRunning = true;
			while (serverRunning) {
				//send client input to server 
				message = inputText.readLine().trim();
				System.out.println(loginName + ": " + message);
				packet = new Packet1(PacketTypes1.MESSAGE, 0, loginName, true, DateFormat.getDateTimeInstance().format(new Date()), message);
				clientOutput.writeObject(packet);
			}
			clientOutput.close();
			clientInput.close();
			clientSoc.close();
		} catch (IOException error) {
			System.err.println("IOException:  " + error);
			System.exit(1);
		}
	}

	public void run() {
		Packet1 receivedPacket;
		PacketTypes1 type;
		String name;
		String time;
		String recievedMessage;
		try {
			while ((receivedPacket = (Packet1) clientInput.readObject()) != null) {
				type = receivedPacket.getType();
				if (type == PacketTypes1.ACK) {
					System.out.println("recieved: " + receivedPacket.getTime());
				} else if (type == PacketTypes1.LOGIN) {
					System.out.println(receivedPacket.getName() + " has entered chat");
				} else {
					name = receivedPacket.getName();
					recievedMessage = receivedPacket.getMessage();
					time = receivedPacket.getTime();
					System.out.println(name + ": " + recievedMessage + " at " + time);
				}
			}
		} catch (IOException error) {
			System.err.println("IOException:  " + error);
			System.exit(1);
		} catch (ClassNotFoundException error) {
			System.err.println("ClassNotFoundException:  " + error);
			System.exit(1);
		}
	}
}

