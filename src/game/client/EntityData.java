package game.client;

import java.io.Serializable;

/**
 * Data for an individual entity
 */
public class EntityData implements Serializable {

	public enum Tag { PLAYER, ZOMBIE }
	public enum ZombieState { WILD, PLAYER1, PLAYER2 }

	private int health;
	private String username;
	private float x,y;
	private Tag tag;
	private ZombieState state;

	/**
	 * Get health of entity
	 * @return Entity health
	 */
	public int getHealth() {
		return health;
	}

	/**
	 * Set health of entity
	 * @param health New health to set
	 */
	public void setHealth(int health) {
		this.health = health;
	}

	/**
	 * Get username of entity
	 * @return Entity username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Set username of entity
	 * @param username New username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Get Y coordinate of entity
	 * @return Y coordinate
	 */
	public float getX() {
		return x;
	}

	/**
	 * Set X coordinate of entity
	 * @param x New X coordinate to set
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Get Y coordinate of entity
	 * @return Y coordinate
	 */
	public float getY() {
		return y;
	}

	/**
	 * Set Y coordinate of entity
	 * @param y New Y coordinate to set
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * Create a new entity data object
	 * @param health Current health of entity
	 * @param x Current X coordinate of entity
	 * @param y Current Y coordinate of entity
	 * @param tag Entity tag (PLAYER / ZOMBIE)
	 * @param state State if a zombie (WILD / PLAYER1 / PLAYER2)
	 */
	public EntityData(int health, float x, float y, Tag tag, ZombieState state ){
		this.health = health;
		this.x = x;
		this.y = y;
		this.tag = tag;
		this.state = state;
	}
}