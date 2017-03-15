package game.server;

import java.io.IOException;
import java.io.ObjectOutputStream;

import game.util.SendableState;

/**
 * Class for managing sending from the server to the specified client
 */
public class ServerSender extends Thread {

	private ServerGameState state;
	private ObjectOutputStream objOut;
	private boolean initial;

	/**
	 * Constructor method
	 * @param (ObjectOutputStream) objOut - The ObjectOutputStream
	 */
	public ServerSender(ObjectOutputStream objOut, ServerGameState state) {
		this.objOut = objOut; this.state = state; this.initial = true;
	}

	/**
	 * Send an object down the ObjectOutputStream to the client
	 */
	public void sendGameState() {
		SendableState send = state.getPackagedState();

		try {
			objOut.writeObject(send);
			objOut.flush();
			objOut.reset();
		} catch (IOException e) {
			System.err.println("Communication Error! " + e.getMessage());
			System.exit(1);
		}
	}

	/**
	 * Send an object up the ObjectOutputStream to the Client
	 * @param (Object) obj - Object to send
	 */
	public void sendObject(Object obj) {
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
		while(true) {
			try {
				Thread.sleep(1000/60);
				if (state.inProgress()) { // If there is a game in progress
					if(initial){
						sendObject("StartingGame");
						initial = false;
					}
					sendGameState(); // Send the game state
				} else{
					System.out.println("Game not ready yet");
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			} 
		}
	}
}