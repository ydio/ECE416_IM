package eceapp.ChitChat;

import java.io.PrintStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.ServerSocket;
import java.io.BufferedReader;

import java.text.DateFormat;
import java.util.Date;

/*
 * A chat server that delivers public and private messages.
 */
public class Server {

	// The server socket.
	private static ServerSocket serverSocket = null;
	// The client socket.
	private static Socket clientSocket = null;

	// This chat server can accept up to maxClientsCount clients' connections.
	private static final int maxClientsCount = 10;
	private static final clientThread[] threads = new clientThread[maxClientsCount];

	public static void main(String args[]) {

		// The default port number.
		int portNumber = 2222;
    /*
     * Open a server socket on the portNumber (default 2222). Note that we can
     * not choose a port less than 1023 if we are not privileged users (root).
     */
		try {
			serverSocket = new ServerSocket(portNumber);
		} catch (IOException e) {
			System.out.println(e);
		}

		System.out.println("Server Running on port number: " + portNumber);

    /*
     * Create a client socket for each connection and pass it to a new client
     * thread.
     */
		while (true) {
			try {
				System.out.println("ehhEEE");
				clientSocket = serverSocket.accept();
				System.out.println("eEEE");

				int i = 0;
				for (i = 0; i < maxClientsCount; i++) {
					if (threads[i] == null) {
						(threads[i] = new clientThread(clientSocket, threads)).start();
						break;
					}
				}
				if (i == maxClientsCount) {
					PrintStream os = new PrintStream(clientSocket.getOutputStream());
					//os.println("Server too busy. Try later.");
					os.close();
					clientSocket.close();
				}
			} catch (IOException e) {
				System.out.println(e);
			}
		}
	}
}

/*
 * The chat client thread. This client thread opens the input and the output
 * streams for a particular client, ask the client's name, informs all the
 * clients connected to the server about the fact that a new client has joined
 * the chat room, and as long as it receive data, echos that data back to all
 * other clients. The thread broadcast the incoming messages to all clients and
 * routes the private message to the particular client. When a client leaves the
 * chat room this thread informs also all the clients about that and terminates.
 */
class clientThread extends Thread {

	private String name = null;
	private BufferedReader is = null;
	private PrintStream os = null;
	private Socket clientSocket = null;
	private final clientThread[] threads;
	private int maxClientsCount;

	public clientThread(Socket clientSocket, clientThread[] threads) {
		this.clientSocket = clientSocket;
		this.threads = threads;
		maxClientsCount = threads.length;
	}

	//public String addInfo (String loginName, String type, String isMe, String time, String message) {
	public String addInfo (String... info) {
		String packet = "";
		for(String value : info){
			packet.concat(value);
			packet.concat("|");
		}
		return packet;
	}

	public String getInfo (String packet) {
		String[] info = packet.split("|", 5);
		return packet;
	}

	public void run() {
		int maxClientsCount = this.maxClientsCount;
		clientThread[] threads = this.threads;

		try {
      /*
       * Create input and output streams for this client.
       */
			is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			os = new PrintStream(clientSocket.getOutputStream());
			while (true) {
				Packet sentPacket = new Packet (" ", "LOGIN", "no", DateFormat.getDateTimeInstance().format(new Date()), "Enter your name.");
				os.println(sentPacket.tostring(sentPacket));

//				sentPacket.setName(AddUser.user.name);

				String recivedPacket = is.readLine().trim();
				//System.out.println("packet " + recivedPacket);
				Packet newPacket = new Packet(recivedPacket);
				name = newPacket.getMessage();
				User usr = new User(name);

				if (name.indexOf('|') == -1) {
					break;
				} else {
					sentPacket = new Packet (name, "MESSAGE", "no", DateFormat.getDateTimeInstance().format(new Date()), "name should not contain bad char");
					os.println(sentPacket.tostring(sentPacket));
				}
			}

      /* Welcome the new the client. */

			synchronized (this) {
				for (int i = 0; i < maxClientsCount; i++) {
					if (threads[i] != null) {
						Packet sentPacket = new Packet (name, "MESSAGE", "no", DateFormat.getDateTimeInstance().format(new Date()), "*** A new user " + name
								+ " entered the chat room !!! ***");

						threads[i].os.println(sentPacket.tostring(sentPacket));
						System.out.println(sentPacket.getMessage());
					} else if (threads[i] != null && threads[i] == this) {
						Packet sentPacket = new Packet (name, "MESSAGE", "no", DateFormat.getDateTimeInstance().format(new Date()), "*** Welcome " + name
								+ " to the chat room !!! ***");
						threads[i].os.println(sentPacket.tostring(sentPacket));
						System.out.println(sentPacket.getMessage());
					}
				}
			}
        	/* Start the conversation. */
			while (true) {
				String recivedPacket = is.readLine();
				if (recivedPacket != null) {
					Packet newPacket = new Packet(recivedPacket);
					String line = newPacket.getMessage();
					if (line.startsWith("/quit")) {
						break;
					}
        /* The message is public, broadcast it to all other clients. */
					synchronized (this) {
						for (int i = 0; i < maxClientsCount; i++) {
							if (threads[i] != null && threads[i].name != null) {
								Packet sentPacket = new Packet(name, "MESSAGE", "no", DateFormat.getDateTimeInstance().format(new Date()), "<" + name + "> " + line);
								threads[i].os.println(sentPacket.tostring(sentPacket));
							}
						}
					}
				}
			}
			synchronized (this) {
				for (int i = 0; i < maxClientsCount; i++) {
					if (threads[i] != null && threads[i] != this && threads[i].name != null) {
						Packet sentPacket = new Packet (name, "MESSAGE", "no", DateFormat.getDateTimeInstance().format(new Date()), "*** The user " + name + " is leaving the chat room !!! ***");
						threads[i].os.println(sentPacket.tostring(sentPacket));

					}
				}
			}
			Packet sentPacket = new Packet (name, "MESSAGE", "no", DateFormat.getDateTimeInstance().format(new Date()), "*** Bye " + name + " ***");
			os.println(sentPacket.tostring(sentPacket));

      /*
       * Clean up. Set the current thread variable to null so that a new client
       * could be accepted by the server.
       */
			synchronized (this) {
				for (int i = 0; i < maxClientsCount; i++) {
					if (threads[i] == this) {
						threads[i] = null;
					}
				}
			}
      /*
       * Close the output stream, close the input stream, close the socket.
       */
			is.close();
			os.close();
			clientSocket.close();
		} catch (IOException e) {
		}
	}
}