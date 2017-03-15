package game.server;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Main server class that spins up the ServerReceiver and ServerSender threads for each connected client
 */
public class Server {

	public static void main(String[] args){
		/*
    	if(args.length != 1){
            System.out.println("Usage: java Server <port>");
            System.exit(0);
        }
        int port = Integer.parseInt(args[0]);
		 */

		// Port currently hardcoded as 4444 to match the client
		int port = 4444;

		// Keep track of connected users
		ClientTable clientTable = new ClientTable();

		// Create ServerSocket
		ServerSocket outSocket = null;

		// this state should be shared between two connected clients. One made for each game.
		ServerGameState state = new ServerGameState("a","b");

		try {
			outSocket = new ServerSocket(port);
		} catch (IOException e) {
			System.err.println("Server: Error! Unable to listen on port " + port );
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
                 String clientName = (String)objIn.readObject();
                 System.out.println("DEBUG: Read client name");
                 System.out.println("New user connected: " + clientName);
                 clientTable.addToTable(clientName);

                 ArrayList<String> players = clientTable.checkAvailable();
				 System.out.println(players.size());
                 if((players.size() % 2 == 0) && !(players.size() == 0)) {
                 	System.out.println("We have enough players now");
                 	state.setReady(true);
                 	clientTable.changePlayerStatus(clientName, ClientTable.playerStatus.IN_GAME);
				 }


                 // Start threads
                 ServerSender server_sender = new ServerSender(objOut,state);
                 server_sender.start();
                 ServerReceiver server_receiver = new ServerReceiver(objIn,state, clientName, clientTable);
                 server_receiver.start();

				// REST OF SERVER CODE SHOULD BE IN SENDER/RECEIVER
			}

		} catch(Exception e) {
			System.err.println("Exception in Server: " + e.getMessage());
			e.printStackTrace();
        }
	}
}