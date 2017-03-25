package game;

import game.client.Player;
import game.util.Vector;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

/**
 * Initialises zombie artwork, making sure they face in the direction of the player when they are near
 */
public class ArtInt {

	// Makes new zombie vector in direction of player.
	public static void followPlayer(Zombie zombie, ArrayList<Player> ps) {
		// Calculate new direction of zombie toward player
		float zx = zombie.getX();
		float zy = zombie.getY();
		ArrayList<Player> players = new ArrayList<>(ps);

		players.removeIf(p -> p.getUsername().equals(zombie.getUsername()) || Math.hypot(zombie.getX() - p.getX(), zombie.getY() - p.getY()) > zombie.AGGRO_RANGE);

		players.sort(Comparator.comparing(p -> Math.hypot(zombie.getX() - p.getX(), zombie.getY() - p.getY())));

		if (players.size() == 0) {
			Random rand = new Random();
			if (rand.nextFloat() < Zombie.DIRECTION_CHANGE_PROBABILITY) {
				zombie.newMovingDir();
			}
		} else {
			// The closest hostile player to this zombie
			Player closestPlayer = players.get(0);

			float x = closestPlayer.getX() - zx;
			float y = closestPlayer.getY() - zy;

			zombie.face(x, y);

			// If the zombie is already touching the player, don't move
			if (Math.hypot(x, y) < 0.3) {
				x = 0.0f;
				y = 0.0f;
			}

			Vector znv = new Vector(x, y).normalised();
			zombie.setDx(znv.x());
			zombie.setDy(znv.y());
		}
	}

}
