package game.client;

import game.util.EndState;
import game.util.SendableState;
import game.util.User;

import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * Class for receiving objects from the server and then determining what to do with them
 */
public class ClientReceiver extends Thread {

	private User user;
	private ObjectInputStream objIn;
	private ClientGameState state;
	private boolean inProgress, initialState, running;

	/**
	 * Constructor
	 * @param user Name of user
	 * @param objIn ObjectInputStream
	 */
	ClientReceiver(User user, ObjectInputStream objIn) {
		this.user = user;
		this.objIn = objIn;
		this.initialState = true;
		this.running = true;
	}

	/**
	 * Add a new game state
	 * @param state ClientGameState to end to this sender
	 */
	public void addState(ClientGameState state){
		this.state = state;
	}

	/**
	 * Close the stream to close the socket
	 */

	public void closeStream() { running = false; }

	// Main method to run when thread starts
	public void run() {
		System.out.println("Client: ClientReceiver started");
		try {
			while(running) {
				Thread.sleep(1000/120);
				Object obj = objIn.readObject();

				if(obj.getClass() == String.class){
					String s = (String) obj;
					if(s.substring(0, 11).equals("newUsername")) {
						String strings[] = s.split(":");
						String newUsername = strings[1];
						user.setUsername(newUsername);
						System.out.println(user.getUsername());
					}else if(!inProgress){
						if(s.equals("StartingGame")){
							System.out.println("starting");
							inProgress = true;
							initialState = true;
						}else if(s.equals("GameOver")){
							inProgress = false;
						}
					}
				}else if(obj.getClass() == SendableState.class){
					SendableState updatedState = (SendableState) obj;
					state.updateClientState(updatedState); // update the clients view of the game state.

					if(initialState){
						state.setConnected(true);
						initialState = false;
					}
				}else if(obj.getClass() == EndState.class) {
					System.out.println("End state");
					EndState end = (EndState) obj;
					state.setEndState(end);
					//Set initialState back to true, to reinitialise the state
					initialState = true;
				}
			}

			try {
				objIn.close();
			} catch (IOException e) {
				System.out.println("Couldn't close client input stream");
			}
		} catch(Exception e) {
			//System.err.println("Exception in ClientReceiver: " + e.getMessage());
			//System.exit(1);

		}
	}
}