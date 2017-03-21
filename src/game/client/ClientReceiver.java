package game.client;

import java.io.ObjectInputStream;

import game.util.EndState;
import game.util.SendableState;
import game.util.User;

/**
 * Class for receiving objects from the server and then determining what to do with them
 */
public class ClientReceiver extends Thread {

	private User user;
	private ObjectInputStream objIn;
	private ClientGameState state;
	private boolean inProgress;

	/**
	 * Constructor
	 * @param user Name of user
	 * @param objIn ObjectInputStream
	 */
	ClientReceiver(User user, ObjectInputStream objIn) {
		this.user = user;
		this.objIn = objIn;
	}

	/**
	 * Add a new game state
	 * @param state ClientGameState to end to this sender
	 */
	public void addState(ClientGameState state){
		this.state = state;
	}

	// Main method to run when thread starts
	public void run() {
		System.out.println("Client: ClientReceiver started");
		try {
			while(true) {
				Thread.sleep(1000/120);
				if(!state.playersReady()) {
					String s = (String)objIn.readObject();
					if(s.equals("PlayersReady")) {
						state.setReady(true);
						// System.out.println("state set to true");
						// System.out.println(state.playersReady());
					}
				}
				if(!inProgress){
					String s = (String)objIn.readObject();
					if(s.equals("StartingGame")){
						System.out.println("Starting the game");
						inProgress = true;
					}else if(s.equals("GameOver")){
						inProgress = false;
					}
				}else{
					Object obj = objIn.readObject();
					if(obj.getClass() == SendableState.class){
						SendableState updatedState = (SendableState) obj;
						state.updateClientState(updatedState); // update the clients view of the game state.
					}else if(obj.getClass() == EndState.class){
						EndState end = (EndState) obj;
						state.setEndState(end);
					}
				}

			}
		} catch(Exception e) {
			System.err.println("Exception in ClientReceiver: " + e.getMessage());
			// e.printStackTrace();;
			System.exit(1);

		}
	}
}