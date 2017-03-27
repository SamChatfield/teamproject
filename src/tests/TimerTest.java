package tests; /**
 *
 */

import game.server.ServerGameState;
import game.server.Timer;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Daniel
 */
public class TimerTest {
    ServerGameState servState = new ServerGameState("ryan", "becca", 1);
    Timer t = new Timer(180, servState);

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        servState.startNewGame();
    }

    /**
     * Test method for {@link game.server.Timer#Timer(int, game.server.ServerGameState)}.
     */
    @Test
    public void testTimer() {
        assertEquals(t.getTimeRemaining(), 180);
    }

    /**
     * Test method for {@link game.server.Timer#getTimeRemaining()}.
     */
    @Test
    public void testGetTimeRemaining() {
        servState.setHasFinished(true);
        assertEquals(t.getTimeRemaining(), 180);
    }

}
