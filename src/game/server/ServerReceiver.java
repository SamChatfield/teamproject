package game.server;

import java.io.IOException;
import java.io.ObjectInputStream;

import game.util.PlayerUpdatePacket;

/**
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
				}else if(obj.getClass() == PlayerUpdatePacket.class){
					PlayerUpdatePacket plr = (PlayerUpdatePacket) obj;
					state.updatePlayer(plr.getData().getUsername(),plr);
				}
				//ServerGameState obj =  (ServerGameState) objIn.readObject();
				//System.out.println(obj.getPlayers().size());
				//state.updateGameState(obj);
			} catch (ClassNotFoundException e) {
				System.err.println("Class Exception in ServerReceiver: " + e.getMessage());
				System.exit(1);
			} catch (IOException e) {
				System.err.println("IO Exception in ServerReceiver: " + e.getMessage());
				System.exit(1);
			}

		}

	}
}
