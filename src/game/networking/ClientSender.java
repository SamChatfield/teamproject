package game.networking;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import game.PlayerData;

/**
 * @author georgesabourin
 * Class for sending objects from the client to the server
 */
public class ClientSender extends Thread {

	private String username;
	private ObjectOutputStream objOut;
	private ArrayList<Object> toSend = new ArrayList();

	/**
	 * Constructor
	 * @param username Name of user
	 * @param objOut ObjectOutputStream
	 */
	public ClientSender(String username, ObjectOutputStream objOut) {
		this.username = username;
		this.objOut = objOut;
	}

	/**
	 * Send an object up the ObjectOutputStream to the Server
	 * @param obj Object to send
	 */
	public void sendObject(Object obj) {
		try {
			//objOut.reset();
			System.out.println("Writing object...");
			objOut.writeObject(obj);
			objOut.flush();
		} catch (IOException e) {
			System.err.println("Communication Error! " + e.getMessage());
			System.exit(1);
		}
		//System.out.println("DEBUG: Object successfully sent");
	}
	
	public void addSend(Object obj) {
		toSend.clear();
		toSend.add(obj);
	}

	// Main method to run when thread starts
	public void run() {
		System.out.println("DEBUG: ClientSender running");
		try {
			objOut.reset();
			objOut.writeObject(username);
			objOut.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//String dummyMessage = "Hello from " + username;
		//Object dummyMessage = new SampleObject("Hello World",1);

		while(true) {
			
			/*
			for(Object obj : toSend) {
				try {
					objOut.reset();
					objOut.writeUnshared(obj);
					objOut.flush();
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					//
				}
			}
			*/
			
			/*
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			*/
			//sendObject(dummyMessage);
		}
	}
}
