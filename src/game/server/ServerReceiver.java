package game.server;

import game.client.EntityData;

import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * @author georgesabourin
 * Class for managing receiving from the client and handling this data
 */
public class ServerReceiver extends Thread {

	private ServerGameState state;
	private ObjectInputStream objIn;
	
	/**
	 * Constructor method
	 * @param objIn The ObjectInputStream
	 */
	public ServerReceiver(ObjectInputStream objIn, ServerGameState state) {
		this.objIn = objIn;
		this.state = state;
	}
	
	// Main method to run when thread starts
	public void run() {

		while(true) {
			try {
				Object obj = objIn.readObject();
				if(obj.getClass() == String.class){
					// If the player has requested to start the game...
					if(obj.toString().equals("StartGame")){
						state.startNewGame();
					}
				}else if(obj.getClass() == EntityData.class){
					System.out.println("Received a player object");
					EntityData plr = (EntityData) obj;
					System.out.println("DEBUG, Player.x = "+plr.getX());
				}
				//ServerGameState obj =  (ServerGameState) objIn.readObject();
				//System.out.println(obj.getPlayers().size());
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
