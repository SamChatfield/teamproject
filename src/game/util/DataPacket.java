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

	public int getNumConvertedZombies() {
		return numConvertedZombies;
	}

	public void setNumConvertedZombies(int numConvertedZombies) {
		this.numConvertedZombies = numConvertedZombies;
	}

	/**
	 * Object that the data packet refers to
	 */
	public enum Type{
		ZOMBIE,PLAYER,BULLET
	}

	// These are used for Zombies
	private State state;
	public enum State {
		WILD, PLAYER
	}

	/**
	 * Get the current state of the DataPacket
	 * @return (State) State of DataPacket
	 */
	public State getState(){
		return state;
	}

	/**
	 * Set the state of the DataPacket
	 * @param (State) state - New state of the DataPacket
	 */
	public void setState(State state){
		this.state = state;
	}

	/**
	 * Get the type of the DataPacket
	 * @return (Type) Type of data packet
	 */
	public Type getType(){
		return type;
	}

	/**
	 * Get the username - mainy used to refer to Player
	 * @return (String) Username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Set the username
	 * @param (String) username - Username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Get the facing angle of the object
	 * @return (double) Facing angle
	 */
	public double getFacingAngle() {
		return facingAngle;
	}

	/**
	 * Set facing angle of object
	 * @param (double) facingAngle - New facing angle to set
	 */
	public void setFacingAngle(double facingAngle) {
		this.facingAngle = facingAngle;
	}

	/**
	 * Get current move speed of the object
	 * @return (float) Current move speed
	 */
	public float getMoveSpeed() {
		return moveSpeed;
	}

	/**
	 * Set move speed
	 * @param (float) moveSpeed - New movespeed to set
	 */
	public void setMoveSpeed(float moveSpeed) {
		this.moveSpeed = moveSpeed;
	}

	/**
	 * Get the last attack time
	 * @return (long) - Time of the last attack of the object
	 */
	public long getLastAttackTime() {
		return lastAttackTime;
	}

	/**
	 * Set the last attack time
	 * @param (long) lastAttackTime - Time to set
	 */
	public void setLastAttackTime(long lastAttackTime) {
		this.lastAttackTime = lastAttackTime;
	}

	/**
	 * Get X coordinate of object
	 * @return (int) X coordinate of object
	 */
	public float getX() {
		return x;
	}

	/**
	 * Set X coordinate of object
	 * @param (float) x - X coordinate to set 
	 */
	public void setX(float x) {
		this.x = x;
	}

	/**
	 * Get Y coordinate of object
	 * @return (int) Y coordinate of object
	 */
	public float getY() {
		return y;
	}

	/**
	 * Set Y coordinate of object
	 * @param (float) y - Y coordinate to set 
	 */
	public void setY(float y) {
		this.y = y;
	}

	/**
	 * Get the health of the object
	 * @return (int) health of object
	 */
	public int getHealth() {
		return health;
	}

	/**
	 * Set the health of the object
	 * @param (int) health - New health of object to set
	 */
	public void setHealth(int health) {
		this.health = health;
	}

	/**
	 * Constructor to create a new DataPacket
	 * @param (float) x - X coordinate
	 * @param (float) y - Y coordinate
	 * @param (float)  moveSpeed - Set move speed
	 * @param (int) health - Current health
	 * @param (long) lastAttackTime - Last attack time
	 * @param (Type) t - Type of object that the DataPacket is based on
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