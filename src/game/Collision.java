package game;

import game.client.Player;
import game.client.Sound;

import java.util.ArrayList;

/**
 * Created by Sam on 31/01/2017.
 */
public class Collision {

    public static void checkCollision(Zombie zombie, Player player) {
        if (zombie.getCollisionBox().intersects(player.getCollisionBox())) {
            zombie.attack(player, 1);
        }
    }
    public static void checkBulletCollision(Bullet b, ArrayList<Bullet> bullets, ArrayList<Zombie> zombies, Player player) {
        for (int i = 0; i < zombies.size(); i++) {
            if (b.getCollisionBox().intersects(zombies.get(i).getCollisionBox())) {
            	//sound.zombieDeath();
                b.damage(zombies.get(i), 25, player.conversionMode);
                bullets.remove(b);
                break;
            }
        }
    }
}
