package game;

/**
 * Created by Sam on 31/01/2017.
 */
public class Collision {

    public static void checkCollision(Zombie zombie, Entity player) {
        if (zombie.getCollisionBox().intersects(player.getCollisionBox())) {
            zombie.attack(player, 1);
        }
    }

}
