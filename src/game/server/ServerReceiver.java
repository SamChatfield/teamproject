package game.server;

import game.util.PlayerUpdatePacket;
import game.util.User;

import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * @author georgesabourin
 * Class for managing receiving from the client and handling this data
 */
public class ServerReceiver extends Thread {

	private ServerGameState state;
	private ObjectInputStream objIn;
	private User username;
	private ClientTable table;
	private boolean running;
	
	/**
	 * Constructor method
	 * @param objIn The ObjectInputStream
	 */
	public ServerReceiver(ObjectInputStream objIn, User username, ClientTable table) {
		this.objIn = objIn;
		this.username = username;
		this.table = table;
		this.running = true;
	}
	
	// Main method to run when thread starts
	public void run() {
		while(running) {
			try {
				Object obj = objIn.readObject();
				if(obj.getClass() == String.class){
					if(obj.toString().equals("Waiting")) { 	//the player is waiting to be paired with another player
						table.changePlayerStatus(username, ClientTable.playerStatus.WAITING);
					} else if(obj.toString().equals("Bye")) {
						System.out.println("The client said bye :'(");
						running = false;
					}
				}else if(obj.getClass() == PlayerUpdatePacket.class){
					PlayerUpdatePacket plr = (PlayerUpdatePacket) obj;
					state.updatePlayer(plr.getData().getUsername(),plr);
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				System.out.println("User disconnected -- closing the socket");
				running = false;
			}
		}

		try {
			table.removeFromTable(username);
			System.out.println("Closing socket");
			objIn.close();

		} catch(IOException e) {
			System.out.println("Closing socket didn't work, dying");
			System.exit(1);
		}

	}

	public void updateState(ServerGameState state) {
		this.state = state;
	}
}
