package game.networking;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;


/**
 * Main client clas
 * Created by Daniel on 31/01/2017.
 * Modified by George on 04/02/2017.
 */
public class Client {

	public static void main(String[] args){
		if(args.length != 3){
			System.out.println("Usage: java Client <username> <host> <port>");
			System.exit(0);
		}

		String username = args[0];
		String host = args[1];
		int port = Integer.parseInt(args[2]);

		// Initialise
		ObjectOutputStream objOut = null;
		ObjectInputStream objIn = null;
		Socket outSocket = null;

		try {
			outSocket = new Socket(host,port);
			System.out.println("DEBUG: Socket created");
			objOut = new ObjectOutputStream(outSocket.getOutputStream());
			objIn = new ObjectInputStream(outSocket.getInputStream());
			System.out.println("DEBUG: I/O streams created");
		} catch(UnknownHostException e) {
			System.err.println("Error! Unknown host - " + host);
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Error! Perhaps the server hasn't been started? " + e.getMessage());
			System.exit(1);
		}

		ClientSender client_sender = new ClientSender(username, objOut);
		ClientReceiver client_receiver = new ClientReceiver(username, objIn);

		// Starting threads
		client_sender.start();
		client_receiver.start();
		
		// TODO: Closure method for threads
	}
}
