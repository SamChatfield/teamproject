package game.server;

import game.util.PlayerUpdatePacket;
import game.util.User;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

/**
 * @author georgesabourin
 * Class for managing receiving from the client and handling this data
 */
public class ServerReceiver extends Thread {

	private ServerGameState state;
	private ObjectInputStream objIn;
	private User username;
	private ClientTable table;
	
	/**
	 * Constructor method
	 * @param objIn The ObjectInputStream
	 */
	public ServerReceiver(ObjectInputStream objIn, User username, ClientTable table) {
		this.objIn = objIn;
		this.username = username;
		this.table = table;
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

					} else if(obj.toString().equals("Waiting")) {
						//the player is waiting to be paired with another player
						table.changePlayerStatus(username, ClientTable.playerStatus.WAITING);
					}
				}else if(obj.getClass() == PlayerUpdatePacket.class){
				//	System.out.println("Received a player object");
					PlayerUpdatePacket plr = (PlayerUpdatePacket) obj;
					state.updatePlayer(plr.getData().getUsername(),plr);
				}
				//ServerGameState obj =  (ServerGameState) objIn.readObject();
				//System.out.println(obj.getPlayers().size());
				//state.updateGameState(obj);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("User disconnected -- closing the server");
				System.exit(1);
				//e.printStackTrace();
			}
			
		}

	}

	public void updateState(ServerGameState state) {
		this.state = state;
	}
}
