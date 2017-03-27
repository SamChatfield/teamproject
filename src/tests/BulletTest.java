package tests;

import game.Bullet;
import game.Entity;
import game.Zombie;
import game.client.Player;
import game.map.MapData;
import game.util.DataPacket;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class BulletTest {

    protected transient MapData mapData;
    Player player = new Player(1, 1, null, "ryan");
    Bullet bullet = new Bullet(player, 1, 1, 1, 1, null);
    Bullet bullet1 = new Bullet(player, 1, 1, 1, 1, null);
    Bullet bullet2 = new Bullet(player, 1, 1, 1, 1, null);
    Bullet bullet3 = new Bullet(player, 1, 1, 1, 1, null);
    Entity entity = new Entity(1, 1, 0.3f, 50, null, null);
    Zombie zombie = new Zombie(1, 1, null, 0);
    Zombie zombie1 = new Zombie(1, 1, null, 0);
    DataPacket data = new DataPacket(1, 1, 0.3f, 50, 10l, null);
    private ArrayList<Bullet> bullets;

    @Test
    public void testMove() {
        bullet.move(1);
        assertEquals(2.2, bullet.getX(), 0.1);
        bullet.move(0.5);
        assertEquals(2.8, bullet.getY(), 0.1);

    }

    @Test
    public void testDamage() {
        bullet.damage(zombie, 10, false);

        assertEquals(15, zombie.getHealth(), 1);

        bullet1.damage(zombie, 50, false);


        bullet2.damage(zombie1, 0, true);

        assertEquals(25, zombie1.getHealth(), 1);


        bullet.active = false;
        bullet.move(1);
        assertEquals(2.2, bullet.getX(), 0.1);
        bullet.move(0.5);
        assertEquals(2.8, bullet.getY(), 0.1);

    }

    @Test
    public void testDamagePlayer() {
        bullet.damage(zombie, 10, false);

        assertEquals(15, zombie.getHealth(), 1);

        bullet1.damage(zombie, 50, false);


        bullet2.damage(zombie1, 0, true);

        assertEquals(25, zombie1.getHealth(), 1);


        bullet.active = false;
        bullet.move(1);
        assertEquals(2.2, bullet.getX(), 0.1);
        bullet.move(0.5);
        assertEquals(2.8, bullet.getY(), 0.1);

    }

//
}
