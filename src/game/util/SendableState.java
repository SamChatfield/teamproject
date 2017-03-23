package game.util;


import game.server.ServerGameState;

import java.io.Serializable;
import java.util.ArrayList;
import game.PowerUp;
import game.Weapon;

/**
 * Sendable state that is sent to the server to handle status of the game
 */
public class SendableState implements Serializable {

	private int timeRemaining;
	private String mapImage;
	private ArrayList<DataPacket> zombies;
	private ArrayList<DataPacket> deadZombies;

	private ArrayList<DataPacket> bullets;
	
	private ArrayList<PowerUp> powerups;
	private ArrayList<Weapon> weapons;

	private boolean hasFinished;
	

	private DataPacket player1;
	private DataPacket player2;

	/**
	 * Get the ArrayList of bullets
	 * @return ArrayList of bullets in the game
	 */
	public ArrayList<DataPacket> getBullets() {
		return bullets;
	}

	/**
	 * Get current time remaining in the game
	 * @return Time remaining in game
	 */
	public int getTimeRemaining() {
		return timeRemaining;
	}

	/**
	 * Get the current map image
	 * @return Map image
	 */
	public String getMapImage() {
		return mapImage;
	}

	/**
	 * Get the ArrayList of dead zombies, in the form of DataPackets
	 * @return ArrayList of zombies in the game (as DataPackets)
	 */
	public ArrayList<DataPacket> getDeadZombies() {
		return deadZombies;
	}

	/**
	 * Get the ArrayList of zombies, in the form of DataPackets
	 * @return ArrayList of zombies in the game (as DataPackets)
	 */
	public ArrayList<DataPacket> getZombies() {
		return zombies;
	}
	
	
	public ArrayList<PowerUp> getPowerups(){
		return powerups;
	}
	
	public ArrayList<Weapon> getWeapons(){
		return weapons;
	}

	/**
	 * Get the DataPacket of a specific player
	 * @param username Username of player to get
	 * @return DataPacket for specific player
	 */
	public DataPacket getPlayer(String username){
		if(username.equals(player1.getUsername())){
			return player1;
		} else if (username.equals(player2.getUsername())){
			return player2;
		} else{
			return null;
		}
	}

	/**
	 * Get the DataPacket object for the 1st player
	 * @return DataPacket of Player 1
	 */
	public DataPacket getPlayer1(){
		return player1;
	}

	/**
	 * Get the DataPacket object for the 2nd player
	 * @return DataPacket of Player 2
	 */
	public DataPacket getPlayer2(){
		return player2;
	}

	/**
	 * Get whether the game has finished
	 * @return Boolean of whether game finished
	 */
	public boolean HasFinished() {
		return hasFinished;
	}

	/**
	 * Constructor to create new sendable state
	 * @param state Current state of Server
	 */
	public SendableState(ServerGameState state){
		this.player1 = state.getPlayer1().getData();
		this.player2 = state.getPlayer2().getData();

		this.zombies = state.getSendableZombies();
		this.timeRemaining = state.getTimeRemaining();
		this.mapImage = state.getMapImage();
		this.bullets = state.getSendableBullets();
		this.powerups = state.getPowerups();
		this.weapons = state.getWeapons();
		this.deadZombies = state.getDeadZombies();
		this.hasFinished = state.HasFinished();
	}
}