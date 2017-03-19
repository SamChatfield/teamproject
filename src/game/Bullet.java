package game;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.Serializable;

import game.client.Player;
import game.map.MapData;
import game.util.DataPacket;
import game.util.Vector;

/**
 * Class that represents bullets in the game that can harm zombies
 */
public class Bullet extends Entity implements Serializable {

	public static final float BULLET_SPEED = 0.15f; // Speed of bullets

	private float dx, dy; // Change in x and y of the bullet each update before delta

	public boolean active;
	private Player player;
	private double distance;
	private static final double fadeDistance = 5;

	// Load image for bullet
	private static final BufferedImage image = ResourceLoader.bulletImage();

	/**
	 * Create a new bullet
	 * @param player Player object
	 * @param aimX X coordinate aimed in
	 * @param aimY Y coordinate aimed in
	 * @param mapData Current mapdata used to know when bullet is over obstacles
	 */
	public Bullet(Player player, float aimX, float aimY, MapData mapData) {
		super(player.getX(), player.getY(), BULLET_SPEED, 0, mapData, DataPacket.Type.BULLET);

		setUsername(player.getUsername());

		Vector normalDir = new Vector(aimX, aimY).normalised();
		dx = normalDir.x();
		dy = normalDir.y();

		data.setFacingAngle(player.getFacingAngle()); // TODO check the
		// efficiency of this
		active = true;
		this.player = player;
	}

	/**
	 * Move zombie on the map
	 * @param delta Interpolation
	 */
	public void move(double delta) {
		float deltX = (float) (dx * BULLET_SPEED * delta);
		float deltY = (float) (dy * BULLET_SPEED * delta);
		float incX = (float) (dx * BULLET_SPEED * delta);
		float incY = (float) (dy * BULLET_SPEED * delta);

		data.setX(getX() + incX);
		data.setY(getY() + incY);

		distance = distance + Math.sqrt((deltX * deltX) + (deltY * deltY));

		if(distance > fadeDistance) {
			this.active = false;
		}
	}

	/**
	 * Draw bullet on the screen
	 * @param g2d Graphics2D object
	 */
	public void draw(Graphics2D g2d) {
		int w = image.getWidth();
		int h = image.getHeight();

		// Draw relative to the player
		Point drawPoint = player.relativeDrawPoint(getX(), getY(), w, h);
		int drawX = drawPoint.x;
		int drawY = drawPoint.y;

		// Rotate bullet so it is facing direction of travel
		AffineTransform at = g2d.getTransform();
		g2d.rotate(data.getFacingAngle(), drawX, drawY);
		g2d.drawImage(image, drawX, drawY, null);
		g2d.setTransform(at);
	}

	/**
	 * Apply damage to the zombie done by the player
	 * @param entity The entity that damaged the zombie
	 * @param damageDone The amount of damage done
	 * @param conversionMode Whether conversion mode is enabled
	 */
	public void damage(Entity entity, int damageDone, boolean conversionMode) {
		Zombie zom = (Zombie) entity;
		if (active) {
			if (conversionMode) {
				zom.convert(player.getUsername()); // pass the shooting player
				// to the zombie.
				System.out.println("Successfully converted zombie!");
			}
			else {
				// TODO: Add in so converted zombies won't damage player
				if(entity.getHealth() <= damageDone){
					System.out.println("Critically injured");
					entity.setHealth(0);
				}else{
					entity.setHealth(entity.getHealth() - damageDone);
				}
				active = false;
			}
		}
	}

	/**
	 * Apply damage to the player done by the bullet
	 * @param player Player object
	 * @param damageDone The amount of damage done
	 */
	public void damagePlayer(Player player, int damageDone) {
		if (active) {
			if (player.getHealth() <= damageDone) {
				System.out.println("Critically injured");
				player.setHealth(0);
			} else {
				player.setHealth(player.getHealth() - damageDone);
			}
			active = false;
		}
	}

	/**
	 * Get image for bullet
	 * @return BufferedImage of bullet
	 */
	public static BufferedImage getImage() {
		return image;
	}

	public CollisionBox getCollisionBox() {
		return collisionBox;
	}
}