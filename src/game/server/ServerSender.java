package game.server;

import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * @author georgesabourin, Daniel Tonks
 * Class for managing sending from the server to the specified client
 */
public class ServerSender extends Thread {

	private GameStateInterface state;
	private ObjectOutputStream objOut;
	
	/**
	 * Constructor method
	 * @param objOut The ObjectOutputStream
	 */
	public ServerSender(ObjectOutputStream objOut, GameStateInterface inter) {
		this.objOut = objOut; this.state = inter;
	}
	
	/**
	 * Send an object down the ObjectOutputStream to the client
	 */
	private void sendGameState() {
		try {
			objOut.writeObject(state.getPackagedState());
			objOut.flush();
		} catch (IOException e) {
			System.err.println("Communication Error! " + e.getMessage());
			System.exit(1);
		}
	}
	
	// Main method to run when thread starts
	public void run() {
		while(true) {
            try {
				Thread.sleep(1000);
				sendGameState(); // send the game state
			} catch (InterruptedException e) {
				e.printStackTrace();
			} 
		}
	}
}
