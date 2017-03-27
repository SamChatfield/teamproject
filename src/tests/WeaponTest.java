package tests; /**
 *
 */

import game.Bullet;
import game.Entity;
import game.Weapon;
import game.Weapon.WeaponState;
import game.Zombie;
import game.client.Player;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author ryan-
 */
public class WeaponTest {
    //--
    Player player = new Player(1, 1, null, "ryan");
    Bullet bullet = new Bullet(player, 1, 1, 1, 1, null);
    Entity entity = new Entity(1, 1, 0.3f, 50, null, null);
    Zombie zombie = new Zombie(1, 1, null, 0);

    WeaponState w = WeaponState.PISTOL;
    WeaponState w1 = WeaponState.UZI;
    WeaponState w2 = WeaponState.MAC_GUN;
    WeaponState w3 = WeaponState.CONVERT;
    WeaponState w4 = WeaponState.SHOTGUN;
    WeaponState w5 = null;
    Weapon weapon = new Weapon(2, 2, null, w, 5);


    /**
     * Test method for {@link game.Weapon#setwState(game.Weapon.WeaponState)}.
     */
    @Test
    public final void testSetwState() {
        weapon.setwState(w);
        assertNotNull(weapon.getwState());
    }

    @Test
    public final void testGetShootDelay() {
        player.setShootDelay(500000000l);
        assertEquals(500000000l, player.getShootDelay());
    }

    /**
     * Test method for {@link game.Weapon#getWeaponStats(game.Weapon.WeaponState, game.client.Player)}.
     */
    @Test
    public final void testGetWeaponStats() {
        weapon.getWeaponStats(w, player);
        assertEquals(500000000L, player.getShootDelay(), 0.1);

        weapon.getWeaponStats(w1, player);
        assertEquals(10000000L, player.getShootDelay(), 0.1);

        weapon.getWeaponStats(w2, player);
        assertEquals(200000000L, player.getShootDelay(), 0.1);

        weapon.getWeaponStats(w3, player);
        assertEquals(500000000L, player.getShootDelay(), 0.1);

        weapon.getWeaponStats(w4, player);
        assertEquals(1000000000L, player.getShootDelay(), 0.1);
    }

    /**
     * Test method for {@link game.Weapon#randomW()}.
     */
    @Test
    public final void testRandomW() {
        assertNotNull(weapon.randomW());
        assertNotNull(weapon.randomW());
        assertNotNull(weapon.randomW());
        assertNotNull(weapon.randomW());
        assertNotNull(weapon.randomW());
        assertNotNull(weapon.randomW());
        assertNotNull(weapon.randomW());
        assertNotNull(weapon.randomW());
        assertNotNull(weapon.randomW());
        assertNotNull(weapon.randomW());
        assertNotNull(weapon.randomW());
        assertNotNull(weapon.randomW());
        assertNotNull(weapon.randomW());
        assertNotNull(weapon.randomW());
        assertNotNull(weapon.randomW());
    }

    /**
     * Test method for {@link game.Weapon#getIndex(game.Weapon.WeaponState)}.
     */
    @Test
    public final void testGetIndex() {
        assertNotNull(weapon.getIndex(w));
        assertNotNull(weapon.getIndex(w1));
        assertNotNull(weapon.getIndex(w2));
        assertNotNull(weapon.getIndex(w3));
        assertNotNull(weapon.getIndex(w4));
        //assertNull(weapon.getIndex(w5));
    }

    /**
     * Test method for {@link game.Weapon#addToInventory(game.Weapon.WeaponState, game.client.Player)}.
     */

    @Test
    public final void testAddToInventory() {

        assertNotNull(player.getInventory()[0]);
        //assertNull(player.getInventory()[0]);
        //assertNotNull(player.getInventory()[1]);

        weapon.addToInventory(w, player);
        assertNotNull(player.getInventory());

        weapon.addToInventory(w1, player);
        assertNotNull(player.getInventory());
    }

}
