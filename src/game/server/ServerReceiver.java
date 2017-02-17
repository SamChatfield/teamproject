package game.server;

import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * @author georgesabourin
 * Class for managing receiving from the client and handling this data
 */
public class ServerReceiver extends Thread {

	private GameStateInterface state;
	private ObjectInputStream objIn;
	
	/**
	 * Constructor method
	 * @param objIn The ObjectInputStream
	 */
	public ServerReceiver(ObjectInputStream objIn, GameStateInterface inter) {
		this.objIn = objIn;
		this.state = inter;
	}
	
	// Main method to run when thread starts
	public void run() {

		while(true) {
			try {
				ServerGameState obj =  (ServerGameState) objIn.readObject();
				System.out.println(obj.getPlayers().size());
				//state.updateGameState(obj);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

	}
}
