package game.server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ConcurrentModificationException;

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
	 * @param objOut The ObjectOutputStream
	 */
	public ServerSender(ObjectOutputStream objOut, ServerGameState state) {
		this.objOut = objOut; this.state = state; this.initial = true;
	}

	/**
	 * Send an object down the ObjectOutputStream to the client
	 */
	public void sendGameState() {

		try {
			SendableState newState = state.getPackagedState();
			objOut.writeObject(newState);
			objOut.flush();
			objOut.reset();
		} catch (IOException e) {
			System.err.println("Communication Error! " + e.getMessage());
			//System.exit(1);
		} catch(ConcurrentModificationException c){
			System.out.println("Concurrent mod exception in sendGameState()");
		}
	}


	/**
	 * Send the end state of the game
	 */
	public void sendEndState() {

		try {
			objOut.writeObject(state.getEndState());
			objOut.flush();
			objOut.reset();
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
		System.out.println("Server: Object successfully sent");
	}

	// Main method to run when thread starts
	public void run() {
		boolean finalStateSent = false;
		while(true) {
			try {
				Thread.sleep(1000/60);
				if (state.inProgress()) { // If there is a game in progress
					if(initial){
						sendObject("StartingGame");
						initial = false;
					}
					if(state.HasFinished() && !finalStateSent){
						finalStateSent = true;

						sendGameState(); // Send a final update (so players don't finish with 50% health because they didn't get the final update.
						sendEndState(); // Send end state of the game
						System.out.println("Final state sent, end state sent");
					}else{
						sendGameState(); // Send the game state
					}
				} else{
					// System.out.println("Game not ready yet");
				}
			} catch (InterruptedException e) {
				System.err.println("ServerSender Interupted Exception: " + e.getMessage());
			} 
		}
	}
}