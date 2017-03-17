package game;

import game.client.Player;
import game.util.Vector;

/**
 * Initialise artwork in the game, pointing in the right direction
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
