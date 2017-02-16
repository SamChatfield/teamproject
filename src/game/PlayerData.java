package game;

import java.io.Serializable;

/**
 * @author georgesabourin
 * Class which will eventually be used for keeping data that is sent to and from the server
 */
public class PlayerData implements Serializable {
	
	public int health;
	public int numberConvertedZombies;
	
	public PlayerData() {
	}
	
	/**
	 * Get the number of converted zombies the player hass
	 * @return Number of zombies converted by the player
	 */
	public int getNumberConvertedZombies() {
		return numberConvertedZombies;
	
	}
	
	/**
	 * Set the number of converted zombies
	 * @param newNum New number of coonverted zombies
	 */
	public void setnumberConvertedZombies(int newNum) {
		numberConvertedZombies = newNum;
	}
	
	/**
	 * Get the health of the player
	 * @return Health of player
	 */
	public int getHealth() {
		return health;
	}
	
	/**
	 * Set the health of the player
	 * @param newHealth New health of player
	 */
	public void setHealth(int newHealth) {
		health = newHealth;
	}
 
}
