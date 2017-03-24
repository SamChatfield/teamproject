package game;
import game.map.MapData;
import game.util.DataPacket;
import java.io.Serializable;
/**
 * Representation of entities in the game (Zombies and Players)
 */
public class Entity implements Serializable {
	protected CollisionBox collisionBox;
	protected transient MapData mapData;
	protected int imageWidth, imageHeight;
	protected DataPacket data;
	float x;
	float y;
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
		this.x = x;
		this.y = y;
	}

	public Entity(float x, float y, float moveSpeed, int health, MapData mapData, DataPacket.Type t, boolean isActivePU, float appearTimePU, boolean isActivePD, float appearTimePD) {
		this.data = new DataPacket(x,y,moveSpeed,health, 0L,t, isActivePU, appearTimePU, isActivePD, appearTimePD);
		//        showCollBox = false;
		this.mapData = mapData;
		collisionBox = new CollisionBox(this);
		this.x = x;
		this.y = y;
	}


	public Entity(float x, float y, MapData mapData) {
		this.mapData = mapData;
		collisionBox = new CollisionBox(this);
	}
	/**
	 * Get the time of the last attack made by this entity
	 * @return Time of last attack
	 */
	public long getLastAttackTime(){
		return data.getLastAttackTime();
	}


	public float getx(){
		return x;
	}

	public float gety(){
		return y;
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

	public void setIsActive(boolean isActive){
		data.setIsActive(isActive);
	}

	public void setAppearTime(float appearTime){
		data.setAppearTime(appearTime);
	}

	public void setIsActivePD(boolean isActivePD){
		data.setIsActivePD(isActivePD);
	}

	public void setAppearTimePD(float appearTimePD){
		data.setAppearTimePD(appearTimePD);
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
		setMoveSpeed(data2.getMoveSpeed());
		setIsActive(data2.getIsActive());
		setAppearTime(data2.getAppearTime());
		setIsActivePD(data2.getIsActivePD());
		setAppearTimePD(data2.getAppearTimePD());
		setCurrentlyEquipped(data2.getCurrentlyEquipped());
		setInventory(data2.getInventory());
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
	public boolean getIsActive(){
		return data.getIsActive();
	}

	public float getAppearTime(){
		return data.getAppearTime();
	}

	public boolean getIsActivePD(){
		return data.getIsActivePD();
	}

	public float getAppearTimePD(){
		return data.getAppearTimePD();
	}

	/**
	 * Set health of entity
	 * @param newHealth New health to set
	 */
	public void setHealth(int newHealth){
		data.setHealth(newHealth);
	}
	/**
	 * Get attack damage of entity
	 * @return Attack damage
	 */
	public int getAttackDamage(){
		return data.getAttackDamage();
	}
	/**
	 * Set health of entity
	 * @param newDamage New health to set
	 */
	public void setAttackDamage(int newDamage){
		data.setAttackDamage(newDamage);
	}
	public boolean isAlive(){
		return data.isAlive();
	}
	public void setAlive(boolean b) {
		data.setAlive(b);
	}
	public Weapon.WeaponState[] getInventory(){
		return data.getInventory();
	}

	public void setInventory(Weapon.WeaponState[] newInventory){
		data.setInventory(newInventory);
	}
	public void setCurrentlyEquipped(Weapon.WeaponState newWeapon){
		data.setCurrentlyEquipped(newWeapon);
	}
	public Weapon.WeaponState getCurrentlyEquipped(){
		return data.getCurrentlyEquipped();
	}

	public long getShootDelay() {
		return data.getShootDelay();
	}

	public void setShootDelay(long shootDelay) {
		data.setShootDelay(shootDelay);
	}
}