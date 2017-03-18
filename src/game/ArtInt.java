package game;

import game.client.Player;
import game.util.Vector;

/**
 * Initialises zombie artwork, making sure they face in the direction of the player when they are near
 */
public class ArtInt {

	// Makes new zombie vector in direction of player.
	public static Vector followPlayer(float zx, float zy, Player player) {
		// Calculate new direction of zombie toward player
		float x = player.getX() - zx;
		float y = player.getY() - zy;

		return new Vector(x, y);
	}
}
