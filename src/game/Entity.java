package game;

import java.io.Serializable;

import game.map.MapData;
import game.util.DataPacket;

/**
 * Representation of entities in the game (Zombies and Players)
 */
public class Entity implements Serializable{

	protected CollisionBox collisionBox;
	protected transient MapData mapData;
	protected int imageWidth, imageHeight;
	protected DataPacket data;

	/**
	 * Create a new entity
	 * @param x Initial X coordinate
	 * @param y Initial y coordinate 
	 * @param moveSpeed Movement speed
	 * @param health Initial health
	 * @param mapData MapData
	 * @param t Type of the DataPacket that this entity will use
	 */
	public Entity(float x, float y, float moveSpeed, int health, MapData mapData, DataPacket.Type t) {
		this.data = new DataPacket(x,y,moveSpeed,health, 0L,t);
		//        showCollBox = false;
		this.mapData = mapData;
		collisionBox = new CollisionBox(this);
	}

	/**
	 * Get the type of the DataPacket within this entity
	 * @return Type of data packet
	 */
	public DataPacket.Type getType(){
		return getType();
	}

	/**
	 * Get the time of the last attack made by this entity
	 * @return Time of last attack
	 */
	public long getLastAttackTime(){
		return data.getLastAttackTime();
	}

	/**
	 * Set the last time the entity attacked
	 * @param newTime New last time of attack
	 */
	public void setLastAttackTime(long newTime){
		data.setLastAttackTime(newTime);
	}

	/**
	 * Set the state of the entity
	 * @param state New state to set
	 */
	public void setState(DataPacket.State state){
		data.setState(state);
	}

	/**
	 * Get the current state of the entity
	 * @return Current state
	 */
	public DataPacket.State getState(){
		return data.getState();
	}

	/**
	 * Move entity on the map
	 * @param dx Movement X
	 * @param dy Movement Y
	 */
	public void move(float dx, float dy) {
		float nx = data.getX() + dx;
		float ny = data.getY() + dy;

		if (mapData.isEntityMoveValid(nx, ny, this)) {
			data.setX(nx);
			data.setY(ny);
		} else if (mapData.isEntityMoveValid(nx, data.getY(), this)) {
			data.setX(nx);
		} else if (mapData.isEntityMoveValid(data.getX(), ny, this)) {
			data.setY(ny);
		}
	}

	/**
	 * Set the facing angle of the entity
	 * @param fx Facing X value
	 * @param fy Facing Y value
	 */
	public void face(float fx, float fy) {
		data.setFacingAngle(Math.atan2(fx, fy) - Math.PI / 2);
	}

	/**
	 * Set the movement speed of the entity
	 * @param moveSpeed New movement speed to set
	 */
	public void setMoveSpeed(float moveSpeed) {
		data.setMoveSpeed(moveSpeed);
	}

	/**
	 * Get current X coordinate of entity
	 * @return X coordinate
	 */
	public float getX(){
		return data.getX();
	}

	/**
	 * Get current Y coordinate of entity
	 * @return Y coordinate
	 */
	public float getY(){
		return data.getY();
	}

	/**
	 * Update DataPacket for this entity
	 * @param data2 New DataPacket to set
	 */
	public void updateData(DataPacket data2) {
		this.data = data2;
	}

	/**
	 * Update local player information of this entity with new data from new DataPacket
	 * @param data2 DataPacket of new updated information to set
	 */
	public void updateLocalPlayerData(DataPacket data2) {
		setLastAttackTime(data2.getLastAttackTime());
		setHealth(data2.getHealth());
		setNumConvertedZombies(data2.getNumConvertedZombies());
	}

	/**
	 * Set number of zombies that entity has converted
	 * @param num New number of converted zombies
	 */
	public void setNumConvertedZombies(int num) {
		data.setNumConvertedZombies(num);
	}

	/**
	 * Get number of zombies entity has converted
	 * @return Number of zombies entity converted / on their team
	 */
	public int getNumConvertedZombies() {
		return data.getNumConvertedZombies();
	}

	/**
	 * Get collision box for entity
	 * @return CollisionBox of entity
	 */
	public CollisionBox getCollisionBox() {
		return collisionBox;
	}

	/**
	 * Get angle the entity is facing
	 * @return Facing angle
	 */
	public double getFacingAngle() {
		return data.getFacingAngle();
	}

	/**
	 * Get current health of entity
	 * @return Current health
	 */
	public int getHealth(){
		return data.getHealth();
	}

	/**
	 * Get DataPacket of entity
	 * @return DataPacket of this entity
	 */
	public DataPacket getData() {
		return data;
	}

	/**
	 * Set username of entity
	 * @param username New username to set
	 */
	public void setUsername(String username){
		data.setUsername(username);
	}

	/**
	 * Get username of entity
	 * @return Username
	 */
	public String getUsername(){
		return data.getUsername();
	}

	/**
	 * Get movement speed of entity
	 * @return Movement speed
	 */
	public float getMoveSpeed(){
		return data.getMoveSpeed();
	}

	/**
	 * Set health of entity
	 * @param newHealth New health to set
	 */
	public void setHealth(int newHealth){
		data.setHealth(newHealth);
	}
}