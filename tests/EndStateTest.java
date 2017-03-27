/**
 *
 */

import game.Bullet;
import game.Entity;
import game.client.Player;
import game.util.EndState;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
//--

/**
 * @author ryan-
 */
public class EndStateTest {

    EndState.EndReason endReason = EndState.EndReason.PLAYER_DIED;
    EndState.EndReason endReason1 = EndState.EndReason.TIME_EXPIRED;
    Player player = new Player(1, 1, null, "ryan");
    Player opponent = new Player(2, 2, null, "becca");
    Bullet bullet = new Bullet(player, 1, 1, 1, 1, null);
    Entity entity = new Entity(1, 1, 0.3f, 50, null, null);
    //EndState(boolean hasFinished, String winnerName, Player player1, Player player2, EndReason reason)
    EndState endState = new EndState(true, "ryan", player, opponent, endReason);
    EndState endState1 = new EndState(true, "ryan", player, opponent, endReason1);


    /**
     * Test method for {@link game.util.EndState#hasFinished()}.
     */
    @Test
    public final void testHasFinished() {
        assertTrue(endState.hasFinished());
    }

    /**
     * Test method for {@link game.util.EndState#getWinnerName()}.
     */
    @Test
    public final void testGetWinnerName() {
        assertEquals("ryan", endState.getWinnerName());
    }

    /**
     * Test method for {@link game.util.EndState#getPlayer1()}.
     */
    @Test
    public final void testGetPlayer1() {
        assertEquals(player, endState.getPlayer1());
    }

    /**
     * Test method for {@link game.util.EndState#getPlayer2()}.
     */
    @Test
    public final void testGetPlayer2() {
        assertEquals(opponent, endState.getPlayer2());
    }

    /**
     * Test method for {@link game.util.EndState#getReason()}.
     */
    @Test
    public final void testGetReason() {
        assertEquals(EndState.EndReason.PLAYER_DIED, endState.getReason());
    }

}
