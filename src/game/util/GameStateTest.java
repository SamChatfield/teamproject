/**
 * 
 */
package game.util;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import game.Bullet;
import game.Zombie;
import game.client.Player;

/**
 * @author ryan-
 *
 */
public class GameStateTest {

	Player player = new Player(1, 1, null, "ryan"); 
	Player player1 = new Player(1, 1, null, "becca"); 
	GameState state = new GameState();
	EndState endState = new EndState(false, null, player, player1, null);
	ArrayList<Bullet> bullets;
	ArrayList<Zombie> zombies;
	Zombie zombie = new Zombie(1,1,null);
	Zombie zombie1 = new Zombie(1,1,null);
	
	/**
	 * Test method for {@link game.util.GameState#getEndState()}.
	 */
	@Test
	public final void testGetEndState() {
		assertNull(state.getEndState());
	}

	/**
	 * Test method for {@link game.util.GameState#setEndState(game.util.EndState)}.
	 */
	@Test
	public final void testSetEndState() {
		state.setEndState(endState);
		assertNotNull(state.getEndState());
	}

	/**
	 * Test method for {@link game.util.GameState#HasFinished()}.
	 */
	@Test
	public final void testHasFinished() {
		assertTrue(!state.HasFinished());
		state.setHasFinished(true);
		assertTrue(state.HasFinished());
	}

	/**
	 * Test method for {@link game.util.GameState#setHasFinished(boolean)}.
	 */
	@Test
	public final void testSetHasFinished() {
		state.setHasFinished(true);
		assertTrue(state.hasFinished);
	}

	/**
	 * Test method for {@link game.util.GameState#getBullets()}.
	 */
	@Test
	public final void testGetBullets() {
		state.setBullets(null);
		assertNull(state.getBullets());
	}

	/**
	 * Test method for {@link game.util.GameState#getMapData()}.
	 */
	@Test
	public final void testGetMapData() {
		assertNull(state.getMapData());
	}

	/**
	 * Test method for {@link game.util.GameState#getMapImage()}.
	 */
	@Test
	public final void testGetMapImage() {
		assertNull(state.getMapImage());
	}


	/**
	 * Test method for {@link game.util.GameState#updateTime(int)}.
	 */
	@Test
	public final void testUpdateTime() {
		state.updateTime(7);
		assertEquals(7, state.getTimeRemaining());
	}

	/**
	 * Test method for {@link game.util.GameState#getZombies()}.
	 */
	@Test
	public final void testGetZombies() {
		//zombies.add(zombie);
		//zombies.add(zombie1);
		
		assertNull(state.getZombies());
	}

	/**
	 * Test method for {@link game.util.GameState#getZombieDataPackets()}.
	 */
	@Test
	public final void testGetZombieDataPackets() {
		assertNull(state.getZombieDataPackets());
	}

	/**
	 * Test method for {@link game.util.GameState#getBulletDataPackets()}.
	 */
	@Test
	public final void testGetBulletDataPackets() {
		assertNull(state.getBulletDataPackets());
	}

	/**
	 * Test method for {@link game.util.GameState#getPlayer1()}.
	 */
	@Test
	public final void testGetPlayer1() {
		assertNull(state.getPlayer1());
	}

	/**
	 * Test method for {@link game.util.GameState#getPlayer2()}.
	 */
	@Test
	public final void testGetPlayer2() {
		assertNull(state.getPlayer2());
	}


	/**
	 * Test method for {@link game.util.GameState#isConnected()}.
	 */
	@Test
	public final void testIsConnected() {
		assertNotNull(state.isConnected());
	}

	/**
	 * Test method for {@link game.util.GameState#setReady(boolean)}.
	 */
	@Test
	public final void testSetReady() {
		state.setReady(true);
		assertTrue(state.playersReady());
	}

	/**
	 * Test method for {@link game.util.GameState#setInProgress(boolean)}.
	 */
	@Test
	public final void testSetInProgress() {
		state.setInProgress(true);
		assertNotNull(state.inProgress());
		state.setInProgress(false);
		assertNotNull(state.inProgress());
	}

}
