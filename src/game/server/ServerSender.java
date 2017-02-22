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
	private boolean initial;
	
	/**
	 * Constructor method
	 * @param objOut The ObjectOutputStream
	 */
	public ServerSender(ObjectOutputStream objOut, GameStateInterface inter) {
		this.objOut = objOut; this.state = inter; this.initial = true;
	}

	/**
	 * Send an object down the ObjectOutputStream to the client
	 */
	public void sendGameState() {
		try {
			objOut.writeObject(state.getPackagedState());
			objOut.flush();
		} catch (IOException e) {
			System.err.println("Communication Error! " + e.getMessage());
			System.exit(1);
		}
	}

	/**
	 * Send an object up the ObjectOutputStream to the Client
	 * @param obj Object to send
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
				Thread.sleep(500);
				if (state.inProgress()) { // if there is a game in progress
					if(initial){
						sendObject("StartingGame");
						initial = false;
					}
					System.out.println("Game started: Sending state");
					sendGameState(); // send the game state
				}else{
					System.out.println("Game not ready yet");
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			} 
		}
	}
}
