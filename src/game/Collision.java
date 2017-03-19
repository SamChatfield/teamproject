package game;

import java.util.ArrayList;

import game.client.Player;

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
	 * @param bullets ArrayList of bullets
	 * @param player1 The player
	 * @param toDamage Amount of damage the bullet will apply
	 */
	public static void checkPlayerCollision(Bullet b, ArrayList<Bullet> bullets, Player player1, Player toDamage) {

		if (b.getCollisionBox().intersects(toDamage.getCollisionBox())) {
			b.damagePlayer(toDamage, 25);
			bullets.remove(b);
		}
	}

	/**
	 * Check if a bullet has hit a zombie, and apply corresponding damage
	 * @param b Bullet object
	 * @param bullets ArrayList of bullets
	 * @param zombies ArrayList of zombies
	 * @param player Player objects
	 */ 
	public static void checkBulletCollision(Bullet b, ArrayList<Bullet> bullets, ArrayList<Zombie> zombies,	Player player) {
		for (int i = 0; i < zombies.size(); i++) {
			if (b.getCollisionBox().intersects(zombies.get(i).getCollisionBox())) {
				b.damage(zombies.get(i), 25, player.conversionMode);
				bullets.remove(b);
				break;
			}
		}
	}
}