package game.server;


import game.util.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Main server class
 * Created by Daniel on 31/01/2017.
 * Modified by George on 04/02/2017
 */
public class Server {

	public static void main(String[] args){

		int port = 4444;

		// Keep track of connected users
		// TODO: Expand clientTable
		ClientTable clientTable = new ClientTable();

		// Create ServerSocket
		ServerSocket outSocket = null;

		Matchmaker matchmaker = new Matchmaker(clientTable);
		matchmaker.start();

		// this state should be shared between two connected clients. One made for each game.
		//ServerGameState state = new ServerGameState("a","b");

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

				// Get name from client -- sort out duplicates later
				User clientObject = (User)objIn.readObject();
				System.out.println("DEBUG: Read client name");
				System.out.println("New user connected: " + clientObject.getUsername());
				clientTable.addToTable(clientObject);

				// Start threads
				ServerSender server_sender = new ServerSender(objOut);
				server_sender.start();
				ServerReceiver server_receiver = new ServerReceiver(objIn, clientObject, clientTable);
				server_receiver.start();

				clientObject.setServerReceiver(server_receiver);
				clientObject.setServerSender(server_sender);

				// REST OF SERVER CODE SHOULD BE IN SENDER/RECEIVER
			}

		} catch(Exception e) {
			//e.printStackTrace();
			System.exit(1);
		}
	}


}
