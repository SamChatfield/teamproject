package game;

import game.client.Player;

import java.util.ArrayList;

/**
 * Class  to detect collisions for bullets and zombies, bullets and players, and players and zombies
 */
public class Collision {

	/**
	 * Check if a zombie has hit a player, and apply corresponding damange
	 * @param zombie Zombie object
	 * @param player Player object
	 */
	public static void checkCollision(Zombie zombie, Player player) {
		if (zombie.getCollisionBox().intersects(player.getCollisionBox())) {
			zombie.attack(player, 1);
		}
	}

	/**
	 * Check if a bullet has hit a player, and apply corresponding damage
	 * @param b Bullet object
	 * @param toDamage Amount of damage the bullet will apply
	 */
	public static void checkPlayerCollision(Bullet b, Player toDamage) {

		if (b.getCollisionBox().intersects(toDamage.getCollisionBox())) {
			b.damagePlayer(toDamage, 25);
			b.active = false;
		}
	}

	/**
	 * Check if a bullet has hit a zombie, and apply corresponding damage
	 * @param b Bullet object
	 * @param zombies ArrayList of zombies
	 * @param player Player objects
	 */ 
	public static void checkBulletCollision(Bullet b, ArrayList<Zombie> zombies,
			Player player) {
		for (int i = 0; i < zombies.size(); i++) {
			if (b.getCollisionBox().intersects(zombies.get(i).getCollisionBox())) {
				b.damage(zombies.get(i), 25, player.conversionMode);
				b.active = false;
				break;
			}
		}
	}
}