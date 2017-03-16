package game;

import game.client.Player;
import game.map.MapData;
import game.util.DataPacket;
import game.util.Vector;

import java.io.Serializable;

/**
 * Created by Sam on 31/01/2017.
 */
public class Bullet extends Entity implements Serializable {

	public static final float BULLET_SPEED = 0.3f;

	private float dx, dy; // the change in x and y of the bullet each update before delta

	public boolean active;
	private Player player;
	private double distance;
	private static final double fadeDistance = 5;

	public Bullet(Player player, float aimX, float aimY, float pdx, float pdy, MapData mapData) {
		super(player.getX(), player.getY(), BULLET_SPEED, 0, mapData, DataPacket.Type.BULLET);

		setUsername(player.getUsername());

		Vector normalDir = new Vector(aimX, aimY).normalised();

		dx = normalDir.x() * BULLET_SPEED + pdx;
		dy = normalDir.y() * BULLET_SPEED + pdy;

		data.setFacingAngle(player.getFacingAngle()); // TODO check the efficiency of this

		active = true;
		this.player = player;
	}

	public void move(double delta) {
		float deltX = (float) (dx * delta);
		float deltY = (float) (dy * delta);

		data.setX(getX() + deltX);
		data.setY(getY() + deltY);

		distance = distance + Math.sqrt((deltX * deltX) + (deltY * deltY));

		if(distance > fadeDistance) {
			this.active = false;
		}
	}

	public void damage(Entity entity, int damageDone, boolean conversionMode) {
		Zombie zom = (Zombie) entity;
		if (active) {
			if(conversionMode) {
				zom.convert(player.getUsername()); // pass the shooting player to the zombie.
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

	public CollisionBox getCollisionBox() {
		return collisionBox;
	}

}
