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
	private ClientGameStateInterface inter;


	/**
	 * Constructor
	 * @param username Name of user
	 * @param objOut ObjectOutputStream
	 */
	ClientSender(String username, ObjectOutputStream objOut, ClientGameStateInterface inter) {
		this.username = username;
		this.objOut = objOut;
		this.inter = inter;
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

		// Keep running, sending the player object from the client game state every so often.
		while(true) {
			try {
				objOut.writeObject(inter.getPlayer(username));
				objOut.flush();
				Thread.sleep(3000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void addInterface(ClientGameStateInterface inter){
	    this.inter = inter;
    }
}
