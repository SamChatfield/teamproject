package game;

import game.util.Vector;

public class ArtInt {

	//Makes new zombie vector in direction of player.
	public static Vector followPlayer(float zx, float zy, Player player){
		
		// calculate new direction of zombie toward player

        float x = (float) (player.getX() - zx);
        float y = (float) (player.getY() - zy);

        return new Vector(x, y);		
		
	}

}
