package game.util;

import game.Bullet;
import game.Zombie;
import game.client.Player;
import game.map.MapData;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * A class that ServerGameState and ClientGameState inherit from. This contains many useful methods that can
 * be called on both game states.
 */
public class GameState implements Serializable {

	protected ArrayList<Zombie> zombies;
	protected ArrayList<Bullet> bullets;
	protected ArrayList<DataPacket> zombieDataPackets;
    protected ArrayList<DataPacket> bulletDataPackets;
	protected Player player1;
	protected Player player2;
	protected String mapImage; // Name of the file being used to create the image.
	protected int timeRemaining;
	protected MapData mapData;
	protected boolean inProgress; // Is the game in progress?
	protected boolean isConnected; // Is the game connected to the server? If this is false, the server hasn't sent a state yet
	protected EndState endState;
	protected boolean hasFinished;

	/**
	 * Get EndState of game
	 * @return The EndState of this game
	 */
	public EndState getEndState() {
		return endState;
	}

	/**
	 * Set the EndState of the current game
	 * @param endState EndState to set
	 */
	public void setEndState(EndState endState) {
		hasFinished = true;
		this.endState = endState;
	}

	/**
	 * Get whether the game has finished
	 * @return Whether game finished
	 */
	public boolean HasFinished() {
		return hasFinished;
	}

	/**
	 * Set whether game has finished
	 * @param hasFinished Boolean of whether game finished
	 */
	public void setHasFinished(boolean hasFinished) {
		this.hasFinished = hasFinished;
	}

	/**
	 * Get the list of bullets
	 * @return ArrayList of bullets
	 */
	public ArrayList<Bullet> getBullets() {
		return bullets;
	}

	/**
	 * Set the bullets
	 * @param bullets New ArrayList of bullets to set
	 */
	public void setBullets(ArrayList<Bullet> bullets) {
		this.bullets = bullets;
	}

	/**
	 * Get the current MapData
	 * @return MapData object
	 */
	public MapData getMapData() {
		return mapData;
	}

	/**
	 * Get the current image of the map
	 * @return Get the current MapImage as a String
	 */
	public String getMapImage() {
		return mapImage;
	}

	/**
	 * Get the time remaining of the current game
	 * @return Time remaining
	 */
	public int getTimeRemaining() {
		return timeRemaining;
	}

	/**
	 * Update time remaining in the game
	 * @param time New time to set
	 */
	public void updateTime(int time){
		timeRemaining = time;
	}

	/**
	 * Get an ArrayList of the zombies in the current game state
	 * @return ArrayList of zombies
	 */
	public ArrayList<Zombie> getZombies() {
		return zombies;
	}

	/**
	 * Get data packet of zombies
	 * @return ArrayList of data packets for zombies currently in the game
	 */
	public ArrayList<DataPacket> getZombieDataPackets() {
		return zombieDataPackets;
	}

    /**
     * Get data packet of bullets
     * @return ArrayList of data packets for bullets currently in the game
     */
    public ArrayList<DataPacket> getBulletDataPackets() {
        return bulletDataPackets;
    }

	/**
	 * Get the player object for player 1 
	 * @return Player 1 object
	 */
	public Player getPlayer1() {
		return player1;
	}

	/**
	 * Get the player object for player 2 
	 * @return Player 2 object
	 */
	public Player getPlayer2() {
		return player2;
	}

	/**
	 * Get whether a game is currently in progress
	 * @return Boolean of whether game in progress
	 */
	public boolean inProgress(){
		return inProgress;
	}

	/**
	 * Get whether a game state is currently connected between two players
	 * @return Boolean of whether two players connected
	 */
	public boolean isConnected(){
		return isConnected;
	}

	/**
	 * Set whether a new is in progress
	 * @param bool Whether game in progress
	 */
	public void setInProgress(boolean bool){
		inProgress = bool;
	}
}