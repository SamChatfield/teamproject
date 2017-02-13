package game;

import java.util.ArrayList;

/**
 * Created by Sam on 31/01/2017.
 */
public class Collision {

    public static void checkCollision(Zombie zombie, Player player, Sound sound) {
        if (zombie.getCollisionBox().intersects(player.getCollisionBox())) {
            zombie.attack(player, 1);
            //sound.playerHurt();
        }
    }

    public static void checkBulletCollision(int bulletIndex, ArrayList<Bullet> bullets, ArrayList<Zombie> zombies, Sound sound) {
        for (int i = 0; i < zombies.size(); i++) {
            if (bullets.get(bulletIndex).getCollisionBox().intersects(zombies.get(i).getCollisionBox())) {
            	sound.zombieDeath();
                bullets.get(bulletIndex).damage(zombies.get(i), 25);
                bullets.remove(bulletIndex);
                System.out.println("Hit");
                break;
            }
        }
    }
}
