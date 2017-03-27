/**
 *
 */

import game.Bullet;
import game.Entity;
import game.PowerUp;
import game.PowerUp.PuState;
import game.Zombie;
import game.client.Player;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author ryan-
 */
public class PowerUpTest {
//--

    PuState puHealth = PuState.HEALTH;
    PuState puSpeed = PuState.SPEED_UP;
    PuState puSlow = PuState.SLOW_DOWN;
    PuState puFreeze = PuState.FREEZE;
    PuState puInverse = PuState.INVERSE;

    PowerUp powerH = new PowerUp(2, 2, null, puHealth, 0);
    PowerUp powerSp = new PowerUp(2, 2, null, puSpeed, 0);
    PowerUp powerSl = new PowerUp(2, 2, null, puSlow, 0);
    PowerUp powerFr = new PowerUp(2, 2, null, puFreeze, 0);
    PowerUp powerIn = new PowerUp(2, 2, null, puInverse, 0);

    Player playerHigh = new Player(1, 1, null, "ryan");
    Player player = new Player(1, 1, null, "ryan");
    Player playerLow = new Player(1, 1, null, "ryan");
    Bullet bullet = new Bullet(playerHigh, 1, 1, 1, 1, null);
    Entity entity = new Entity(1, 1, 0.3f, 50, null, null);

    Zombie zombie = new Zombie(1, 1, null, 49);
    ArrayList<Zombie> zombies = new ArrayList<>();


    /**
     * Test method for {@link game.PowerUp#getx()}.
     */
    @Test
    public final void testGetx() {
        assertNotNull(powerH.getx());
    }

    /**
     * Test method for {@link game.PowerUp#gety()}.
     */
    @Test
    public final void testGety() {
        assertNotNull(powerSp.gety());
    }

    /**
     * Test method for {@link game.PowerUp#getpState()}.
     */
    @Test
    public final void testGetpState() {
        assertNotNull(powerH.getpState());
    }


    /**
     * Test method for {@link game.PowerUp#getPowerupStats(game.PowerUp, game.client.Player)}.
     */
    @Test
    public final void testGetPowerupStats() {
        powerH.getPowerupStats(powerH, playerHigh, zombies);
        assertNotNull(playerHigh.getAppearTime());
        assertNotNull(playerHigh.getMoveSpeed());
        assertNotNull(playerHigh.getIsActive());

        playerLow.setHealth(10);
        powerH.getPowerupStats(powerH, playerLow, zombies);
        assertNotNull(playerLow.getAppearTime());
        assertNotNull(playerLow.getMoveSpeed());
        assertNotNull(playerLow.getIsActive());
        assertNotNull(playerLow.getHealth());

        playerHigh.setIsActive(true);
        powerSp.getPowerupStats(powerSp, playerHigh, zombies);
        assertNotNull(playerHigh.getAppearTime());
        assertNotNull(playerHigh.getMoveSpeed());
        assertNotNull(playerHigh.getIsActive());


        playerLow.setIsActive(false);
        powerSp.getPowerupStats(powerSp, playerLow, zombies);
        assertNotNull(playerLow.getAppearTime());
        assertNotNull(playerLow.getMoveSpeed());
        assertNotNull(playerLow.getIsActive());


    }

    /**
     * Test method for {@link game.PowerUp#getPowerdownStats(game.PowerUp, game.client.Player)}.
     */
    @Test
    public final void testGetPowerdownStats() {
        powerSl.getPowerdownStats(powerSl, playerHigh);
        assertNotNull(playerHigh.getAppearTime());
        assertNotNull(playerHigh.getMoveSpeed());
        assertNotNull(playerHigh.getIsActive());

        powerIn.getPowerdownStats(powerIn, playerLow);
        assertNotNull(playerHigh.getAppearTime());
        assertNotNull(playerHigh.getMoveSpeed());
        assertNotNull(playerHigh.getIsActive());

        powerFr.getPowerdownStats(powerFr, player);
        assertNotNull(playerHigh.getAppearTime());
        assertNotNull(playerHigh.getMoveSpeed());
        assertNotNull(playerHigh.getIsActive());


        powerFr.getPowerdownStats(powerFr, player);
        assertNotNull(playerHigh.getAppearTime());
        assertNotNull(playerHigh.getMoveSpeed());
        assertNotNull(playerHigh.getIsActive());
    }

    /**
     * Test method for {@link game.PowerUp#randomPU()}.
     */
    @Test
    public final void testRandomPU() {
        assertNotNull(powerH.randomPU());
        assertNotNull(powerH.randomPU());
        assertNotNull(powerH.randomPU());
        assertNotNull(powerH.randomPU());
        assertNotNull(powerH.randomPU());
        assertNotNull(powerH.randomPU());
        assertNotNull(powerH.randomPU());
        assertNotNull(powerH.randomPU());
        assertNotNull(powerH.randomPU());
        assertNotNull(powerH.randomPU());
        assertNotNull(powerH.randomPU());
        assertNotNull(powerH.randomPU());
    }

    /**
     * Test method for {@link game.PowerUp#normalSpeed(game.client.Player)}.
     */
    @Test
    public final void testNormalSpeed() {
        powerSp.normalSpeed(player);
        assertEquals(0.1f, player.getMoveSpeed(), 0.05);
    }

    /**
     * Test method for {@link game.PowerUp#normalSpeedPD(game.client.Player)}.

     @Test public final void testNormalSpeedPD() {
     powerSp.normalSpeedPD(player);
     assertEquals(0.1f, player.getMoveSpeed(), 0.05);
     }*/

}
