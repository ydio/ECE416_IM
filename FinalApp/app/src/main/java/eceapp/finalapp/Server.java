package eceapp.finalapp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

//import Server.Packet;
//import Server.PacketTypes;


public class Server {

	private static Socket clientSoc = null;
	private static int numClients = 32;
	private static final clientThread[] clients = new clientThread[numClients];

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		int serverPortNumber = 8081;
		System.out.print("Waiting for Client\n");

		ServerSocket serverSoc = new ServerSocket(serverPortNumber);
		boolean serverRunning = true;
		while (serverRunning) {
			clientSoc = serverSoc.accept();
			System.out.print("Client Accepted\n");
			for(int index = 0; index < numClients; index++) {
				if (clients[index] == null) {
					clients[index] = new clientThread(clientSoc, clients);
					clients[index].start();
					break;
				}
			}
		}
		clientSoc.close();
		serverSoc.close();
	}

}

class clientThread extends Thread {

	private String loginName = null;
	private String group = null;
	private ObjectOutputStream serverOutput = null;
	private ObjectInputStream clientInput = null;
	private Socket clientSoc = null;
	private final clientThread[] clients;
	private int numClients;

	public clientThread(Socket clientSoc, clientThread[] clients) {
		this.clientSoc = clientSoc;
		this.clients = clients;
		numClients = clients.length;
	}

	public void run() {
		int numClients = this.numClients;
		clientThread[] clients = this.clients;
		
		Packet receivedPacket;
		//Packet ackPacket = new Packet(PacketTypes.ACK);
		String name;
		String time;
		String recievedMessage;

		try {
			serverOutput = new ObjectOutputStream(clientSoc.getOutputStream());
			clientInput = new ObjectInputStream(clientSoc.getInputStream());
			System.out.println("Waiting for client respose\n");

			//conversation
			boolean serverRunning = true;
	    	while (serverRunning) {
	    		try {
                    System.out.println("why not\n");
                    Object object = clientInput.readObject();
                    //clientInput.readClassDescriptor();
					receivedPacket = (Packet) object;
					//send to all client
					synchronized (this) {
						for (int index = 0; index < numClients; index++) {
							if (clients[index] != null && clients[index] != this) {
								receivedPacket.setMe(false);
								clients[index].serverOutput.writeObject(receivedPacket);
							} else if (clients[index] != null && clients[index] == this) {
								receivedPacket.setType(PacketTypes.ACK);
								receivedPacket.setMe(true);
								clients[index].serverOutput.writeObject(receivedPacket);
								receivedPacket.setType(PacketTypes.MESSAGE);
								receivedPacket.setMe(false);
							}
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
			clientInput.close();
			serverOutput.close();
			clientSoc.close();
		} catch (IOException error) {
			System.err.println("IOException:  " + error);
			System.exit(1);
		}
	}
}
