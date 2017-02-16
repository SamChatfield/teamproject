package game.client;

import game.server.ServerGameState;

import java.io.ObjectInputStream;

/**
 * @author georgesabourin, Daniel Tonks
 * Class for receiving objects from the server and then determining what to do with them
 */
public class ClientReceiver extends Thread {

	private String username;
	private ObjectInputStream objIn;
	private ClientGameStateInterface inter;

	/**
	 * Constructor
	 * @param username Name of user
	 * @param objIn ObjectInputStream
	 */
	ClientReceiver(String username, ObjectInputStream objIn) {
		this.username = username;
		this.objIn = objIn;
	}


	public void addInterface(ClientGameStateInterface inter){
		this.inter = inter;
	}


	// Main method to run when thread starts
	public void run() {
		System.out.println("DEBUG: ClientReceiver started");
		try {
			while(true) {
				ServerGameState state = (ServerGameState)objIn.readObject();
				inter.update(state); // update the clients view of the game state.
			}
		} catch(Exception e) {
			System.err.println("Error! " + e.getMessage());
		}
	}
}
