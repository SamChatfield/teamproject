package game;

import game.map.MapData;
import game.util.DataPacket;
import game.util.Vector;

/**
 * Class that represents a zombie in the game
 */
public class Zombie extends Entity {

	private float dx, dy; // Which direction is the zombie moving in every tick?

	public static final float DIRECTION_CHANGE_PROBABILITY = 0.01f;
	private static final float COLL_BOX_WIDTH = 25.0f;
	private static final float COLL_BOX_HEIGHT = 25.0f;
	private static final int HEALTH = 25;
	private static final float MOVE_SPEED = 0.05f;
	public static final float AGGRO_RANGE = 4.0f;

	/**
	 * Create a new zombie
	 * @param x Initial x coordinate
	 * @param y Initial y coordinate
	 * @param mapData MapData of the current map
	 */
	public Zombie(float x, float y, MapData mapData, int attackDamage) {
		super(x, y, MOVE_SPEED, HEALTH, mapData, DataPacket.Type.ZOMBIE);
		setState(DataPacket.State.WILD);
		setAttackDamage(attackDamage);
		setUsername("None");
	}

	/**
	 * Get x direction in which zombie is moving
	 * @return X direction of movement
	 */
	public float getDx() {
		return dx;
	}

	/**
	 * Get y direction in which zombie is moving
	 * @return Y direction of movement
	 */
	public float getDy() {
		return dy;
	}

	/**
	 * Convert a zombie to the player's team
	 * @param username Username of player who converted zombie
	 */
	public void convert(String username) {
		setUsername(username); // Change the owner of the zombie to the new player
		setState(DataPacket.State.PLAYER);
	}

	/**
	 * Apply zombie movement
	 * @param delta Interpolation
	 */
	public void move(double delta) {
		float moveX = dx * getMoveSpeed() * (float) delta;
		float moveY = dy * getMoveSpeed() * (float) delta;
		move(moveX,moveY);
	}

	/**
	 * Move zombie on the map
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
	 * Make zombie follow the player
	 * @param player Player object of player zombie sohuld follow
	 */
//	public void followDirection(Player player) {
//
//		// Zombie vector changed to follow player, if wild or if it is an opposing player
//		if (getState() == DataPacket.State.WILD || player.getUsername() != getUsername()) {
//			Vector zdv = ArtInt.followPlayer(this, players);
//			Vector znv = zdv.normalised();
//
//			dx = znv.x();
//			dy = znv.y();
//
//			face((int) zdv.x(), (int) zdv.y());
//		}
//	}

	/**
	 * Change movement direction of zombie to a new random one
	 */
	public void newMovingDir() {
		Vector zdv = Vector.randomVector();
		Vector znv = zdv.normalised();

		dx = znv.x();
		dy = znv.y();
		face((int) zdv.x(), (int) zdv.y()); // -x because of the original orientation of the zombie image
	}

	/**
	 * Attack the player
	 * @param entity Entity the zombie is attacking (likely Player)
	 * @param damageDone Amount of damange to be done
	 */
	public void attack(Entity entity, int damageDone) {
		long now = System.nanoTime();

		if(getState() == DataPacket.State.PLAYER && entity.getUsername().equals(getUsername())) {
			// Currently, do nothing
		}
		else {
			if (now - getLastAttackTime() > 1000000000L) {
				setLastAttackTime(now);
				entity.setHealth(entity.getHealth() - damageDone);
			}
		}
	}


	public void setDx(float dx) {
		this.dx = dx;
	}

	public void setDy(float dy) {
		this.dy = dy;
	}

}