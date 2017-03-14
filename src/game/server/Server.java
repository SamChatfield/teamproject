package game.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Main server class, handles connection to the clients
 */
public class Server {

	/**
	 * Main method to start the server
	 * @param (String[]) args - Arguments to start server (host and port)
	 */
	public static void main(String[] args){
		if(args.length != 2){
			System.out.println("Usage: java Server <host> <port>");
			System.exit(0);
		}

		String host = args[0];
		int port = Integer.parseInt(args[1]);

		// Create ServerSocket
		ServerSocket outSocket = null;

		// This state should be shared between two connected clients. One made for each game.
		ServerGameState state = new ServerGameState("a","b");

		try {
			outSocket = new ServerSocket(port);
		} catch (IOException e) {
			System.err.println("Error! Unable to listen on port " + port );
			System.exit(1);
		}

		System.out.println("Success! Server successfully started");

		try {
			while(true) {
				Socket clientSocket = outSocket.accept();

				// Someone connected, for debug we'll now generate the game state
				System.out.println("DEBUG: Accepting socket connection");


				ObjectOutputStream objOut = new ObjectOutputStream(clientSocket.getOutputStream());
				ObjectInputStream objIn = new ObjectInputStream(clientSocket.getInputStream()); // this seems to break
				System.out.println("DEBUG: I/O streams created");

				// Get name from client
				String clientName = (String)objIn.readObject();
				System.out.println("DEBUG: Read client name");
				System.out.println("New user connected: " + clientName);

				// Start threads
				ServerSender server_sender = new ServerSender(objOut,state);
				server_sender.start();
				ServerReceiver server_receiver = new ServerReceiver(objIn,state);
				server_receiver.start();
			}

		} catch(Exception e) {
			e.printStackTrace();
			System.exit(1); 
		}
	}
}
