package game.util;

import game.Bullet;
import game.PowerUp;
import game.Weapon;
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

	//Used w/ ServerGameState
	protected ArrayList<Zombie> zombies;
	protected ArrayList<Bullet> bullets;

	//Used w/ ClientGameState
	protected ArrayList<DataPacket> zombieDataPackets;
    protected ArrayList<DataPacket> bulletDataPackets;
    protected ArrayList<PowerUp> powerups;
    protected ArrayList<Weapon>  weapons;
    
	protected Player player1;
	protected Player player2;
	protected String mapImage; // Name of the file being used to create the image.
	protected int timeRemaining;
	protected MapData mapData;
	protected boolean inProgress; // Is the game in progress?
	protected boolean isConnected;

	public boolean isConnected() {
		return isConnected;
	}

	public void setConnected(boolean connected) {
		isConnected = connected;
	}

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
		this.inProgress = false;
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

	
	public ArrayList<PowerUp> getPowerups(){
		return powerups;
	}
	
	public void setPowerUp(ArrayList<PowerUp> powerups){
		this.powerups = powerups;
	}
	
	
	public ArrayList<Weapon> getWeapons(){
		return weapons;
	}
	
	public void setWeapons(ArrayList<Weapon> weapons){
		this.weapons = weapons;
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
	 * Set the list of zombies to the argument
	 * @param zombies new list of zombies
	 */
	public void setZombies(ArrayList<Zombie> zombies) {
		this.zombies = zombies;
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

}