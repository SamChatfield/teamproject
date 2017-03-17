package game.client;

import game.server.ServerGameState;
import game.util.DataPacket;
import game.util.EndState;
import game.util.SendableState;

import java.io.ObjectInputStream;
import java.util.ArrayList;

/**
 * @author georgesabourin, Daniel Tonks
 * Class for receiving objects from the server and then determining what to do with them
 */
public class ClientReceiver extends Thread {

	private String username;
	private ObjectInputStream objIn;
	private ClientGameState state;
	private boolean inProgress;

	/**
	 * Constructor
	 * @param username Name of user
	 * @param objIn ObjectInputStream
	 */
	ClientReceiver(String username, ObjectInputStream objIn) {
		this.username = username;
		this.objIn = objIn;
	}


	public void addState(ClientGameState state){
		this.state = state;
	}


	// Main method to run when thread starts
	public void run() {
		System.out.println("DEBUG: ClientReceiver started");
		try {
			while(true) {
				Thread.sleep(1000/60);
				if(!inProgress){
					String s = (String)objIn.readObject();
					if(s.equals("StartingGame")){
						System.out.println("starting");
						inProgress = true;
					}else if(s.equals("GameOver")){
						inProgress = false;
					}
				}else{
					Object obj = objIn.readObject();
					if(obj.getClass() == SendableState.class){
						SendableState updatedState = (SendableState) objIn.readObject();
						state.updateClientState(updatedState); // update the clients view of the game state.
					}else if(obj.getClass() == EndState.class){
						EndState end = (EndState) objIn.readObject();
						System.out.println("Winner "+end.getWinnerName());
					}
				}

			}
		} catch(Exception e) {
			e.printStackTrace();
			System.err.println("Error! " + e.getMessage());
		}
	}
}
