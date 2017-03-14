package game.util;

import java.io.Serializable;
import java.util.ArrayList;

import game.Bullet;
import game.server.ServerGameState;

/**
 * Sendable state that is sent to the server to handle status of the game
 */
public class SendableState implements Serializable {

	private int timeRemaining;
	private String mapImage;
	private ArrayList<DataPacket> zombies;
	private ArrayList<Bullet> bullets;

	private DataPacket player1;
	private DataPacket player2;

	public ArrayList<Bullet> getBullets() {
		return bullets;
	}

	/**
	 * Get current time remaining in the game
	 * @return (int) Time remaining in game
	 */
	public int getTimeRemaining() {
		return timeRemaining;
	}

	/**
	 * Get the current map image
	 * @return (String) Map image
	 */
	public String getMapImage() {
		return mapImage;
	}

	/**
	 * Get the ArrayList of zombies, in the form of DataPackets
	 * @return ArrayList<DataPacket> Zombies in the game
	 */
	public ArrayList<DataPacket> getZombies() {
		return zombies;
	}

	/**
	 * Get the DataPacket of a specific player
	 * @param (String) username - Username of player to get
	 * @return (DataPacket) DataPacket for specific player
	 */
	public DataPacket getPlayer(String username){
		if(username.equals(player1.getUsername())){
			return player1;
		}else if (username.equals(player2.getUsername())){
			return player2;
		}else{
			return null;
		}
	}

	/**
	 * Get the DataPacket object for the 1st player
	 * @return (DataPacket) DataPacket of Player 1
	 */
	public DataPacket getPlayer1(){
		return player1;
	}

	/**
	 * Get the DataPacket object for the 2nd player
	 * @return (DataPacket) DataPacket of Player 2
	 */
	public DataPacket getPlayer2(){
		return player2;
	}

	/**
	 * Constructor to create new sendable state
	 * @param (ServerGameState) state - Current state of Server
	 */
	public SendableState(ServerGameState state){
		this.player1 = state.getPlayer1().getData();
		this.player2 = state.getPlayer2().getData();

		this.zombies = state.getSendableZombies();
		this.timeRemaining = state.getTimeRemaining();
		this.mapImage = state.getMapImage();
		this.bullets = state.getBullets();
	}
}
