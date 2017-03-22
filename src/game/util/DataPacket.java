package game.util;

import java.io.Serializable;

/**
 * Data packet that is sent from client -> server
 */
public class DataPacket implements Serializable {

	private float x,y;
	private int health;
	private double facingAngle;
	private float moveSpeed;
	protected long lastAttackTime;
	private String username;
	private Type type;
	private int numConvertedZombies;

	/**
	 * Object that the data packet refers to
	 */
	public enum Type {
		ZOMBIE,PLAYER,BULLET
	}

	// These are used for Zombies
	private State state;
	public enum State {
		WILD, PLAYER
	}

	/**
	 * Get the current state of the DataPacket
	 * @return State of DataPacket
	 */
	public State getState(){
		return state;
	}

	/**
	 * Set the state of the DataPacket
	 * @param state New state of the DataPacket
	 */
	public void setState(State state){
		this.state = state;
	}

	/**
	 * Get the type of the DataPacket
	 * @return Type of data packet
	 */
	public Type getType(){
		return type;
	}

	/**
	 * Get the username - mailny used to refer to Player
	 * @return Username of player
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Set the username
	 * @param Username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Get the facing angle of the object
	 * @return Facing angle
	 */
	public double getFacingAngle() {
		return facingAngle;
	}

	/**
	 * Set facing angle of object
	 * @param facingAngle New facing angle to set
	 */
	public void setFacingAngle(double facingAngle) {
		this.facingAngle = facingAngle;
	}

	/**
	 * Get current move speed of the object
	 * @return Current move speed
	 */
	public float getMoveSpeed() {
		return moveSpeed;
	}

	/**
	 * Set move speed
	 * @param moveSpeed New movespeed to set
	 */
	public void setMoveSpeed(float moveSpeed) {
		this.moveSpeed = moveSpeed;
	}

	/**
	 * Get the last attack time
	 * @return Time of the last attack of the object
	 */
	public long getLastAttackTime() {
		return lastAttackTime;
	}

	/**
	 * Set the last attack time
	 * @param lastAttackTime Time to set
	 */
	public void setLastAttackTime(long lastAttackTime) {
		this.lastAttackTime = lastAttackTime;
	}

	/**
	 * Get number of converted zombies
	 * @return Number of converted zombies
	 */
	public int getNumConvertedZombies() {
		return numConvertedZombies;
	}

	/**
	 * Set number of converted zombies
	 * @param numConvertedZombies New number of converted zombies
	 */
	public void setNumConvertedZombies(int numConvertedZombies) {
		this.numConvertedZombies = numConvertedZombies;
	}

	/**
	 * Get X coordinate of object
	 * @return X coordinate of object
	 */
	public float getX() {
		return x;
	}

	/**
	 * Set X coordinate of object
	 * @param x X coordinate to set
	 */
	public void setX(float x) {
		this.x = x;
	}

	/**
	 * Get Y coordinate of object
	 * @return Y coordinate of object
	 */
	public float getY() {
		return y;
	}

	/**
	 * Set Y coordinate of object
	 * @param y Y coordinate to set
	 */
	public void setY(float y) {
		this.y = y;
	}

	/**
	 * Get the health of the object
	 * @return Health of object
	 */
	public int getHealth() {
		return health;
	}

	/**
	 * Set the health of the object
	 * @param health New health of object to set
	 */
	public void setHealth(int health) {
		this.health = health;
	}

	/**
	 * Constructor to create a new DataPacket
	 * @param x X coordinate
	 * @param y Y coordinate
	 * @param moveSpeed The set move speed
	 * @param health Current health
	 * @param lastAttackTime Last attack time
	 * @param t Type of object that the DataPacket is based on (ZOMBIE, PLAYER or BULLET)
	 */
	public DataPacket(float x, float y, float moveSpeed, int health, long lastAttackTime, Type t) {
		this.lastAttackTime = lastAttackTime;

		this.x = x;
		this.y = y;
		this.moveSpeed = moveSpeed;
		this.health = health;
		this.type = t;
	}
}