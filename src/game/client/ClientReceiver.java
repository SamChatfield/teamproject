package game.client;

import game.server.ServerGameState;
import game.util.DataPacket;
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
				Thread.sleep(1000/30);
				if(!inProgress){
					String s = (String)objIn.readObject();
					if(s.equals("StartingGame")){
						System.out.println("starting");
						inProgress = true;
					}else if(s.equals("GameOver")){
						inProgress = false;
					}
				}else{
					System.out.println("Got state from server");
					SendableState updatedState = (SendableState) objIn.readObject();
					// This is printing out the health of the zombies already known to the client.
				//	ArrayList<DataPacket> zomb = updatedState.getZombies();
				//	for(DataPacket z:zomb){
				//		System.out.println("REC: "+z.getHealth());
				//	}

					state.updateClientState(updatedState); // update the clients view of the game state.
					System.out.println("Update complete");
				}

			}
		} catch(Exception e) {
			e.printStackTrace();
			System.err.println("Error! " + e.getMessage());
		}
	}
}
