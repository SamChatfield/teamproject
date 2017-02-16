package game.client;

import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * @author georgesabourin
 * Class for sending objects from the client to the server
 */
public class ClientSender extends Thread {

	private String username;
	private ObjectOutputStream objOut;

	/**
	 * Constructor
	 * @param username Name of user
	 * @param objOut ObjectOutputStream
	 */
	ClientSender(String username, ObjectOutputStream objOut) {
		this.username = username;
		this.objOut = objOut;
	}

	/**
	 * Send an object up the ObjectOutputStream to the Server
	 * @param obj Object to send
	 */
	private void sendObject(Object obj) {
		try {
			objOut.writeObject(obj);
			objOut.flush();
		} catch (IOException e) {
			System.err.println("Communication Error! " + e.getMessage());
			System.exit(1);
		}
		System.out.println("DEBUG: Object successfully sent");
	}

	// Main method to run when thread starts
	public void run() {
		System.out.println("DEBUG: ClientSender running");
		sendObject(username);

		//String dummyMessage = "Hello from " + username;
		//Object dummyMessage = new SampleObject("Hello World",1);

		while(true) {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//sendObject(dummyMessage);
		}
	}
}
