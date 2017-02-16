package game.networking;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Main server class
 * Created by Daniel on 31/01/2017.
 * Modified by George on 04/02/2017
 */
public class Server implements Serializable {

    public static void main(String[] args){
        if(args.length != 2){
            System.out.println("Usage: java Server <host> <port>");
            System.exit(0);
        }
        
        String host = args[0];
        int port = Integer.parseInt(args[1]);
        
        // Keep track of connected users
        // TODO: Expand clientTable
        ClientTable clientTable = new ClientTable();
      
        // Create ServerSocket
        ServerSocket outSocket = null;

        GameStateInterface inter = new GameStateInterface();
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
        		 System.out.println("DEBUG: Accepting socket connection");
        		 
        		 ObjectOutputStream objOut = new ObjectOutputStream(clientSocket.getOutputStream());
        		 ObjectInputStream objIn = new ObjectInputStream(clientSocket.getInputStream());
                 System.out.println("DEBUG: I/O streams created");
                 
                 // Get name from client -- sort out duplicates later
                 String clientName = (String)objIn.readObject();
                 System.out.println("DEBUG: Read client name");
                 System.out.println("New user connected: " + clientName);
                 clientTable.addToTable(clientName);
                 
                 // Start threads
                 ServerSender server_sender = new ServerSender(objOut,inter);
                 server_sender.start();
                 ServerReceiver server_receiver = new ServerReceiver(objIn,inter,clientName);
                 server_receiver.start();
        		
                 // REST OF SERVER CODE SHOULD BE IN SENDER/RECEIVER
        	}

        } catch(Exception e) {
			System.err.println("Error! -- " + e.getMessage());
			System.exit(1); 
        }
    }
}
