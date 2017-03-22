/**
 * 
 */
package game.client;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import game.Bullet;
import game.Zombie;
import game.server.ServerGameState;
import game.util.EndState;
import game.util.GameState;
import game.util.SendableState;
import game.util.User;

/**
 * @author ryan-
 *
 */
public class ClientGameStateTest {
	ServerGameState serverState;
	Player player = new Player(1, 1, null, "ryan"); 
	Player player1 = new Player(1, 1, null, "becca"); 
	GameState state = new GameState();
	EndState endState = new EndState(false, null, player, player1, null);
	ArrayList<Bullet> bullets;
	ArrayList<Zombie> zombies;
	Zombie zombie = new Zombie(1,1,null, 0);
	Zombie zombie1 = new Zombie(1,1,null, 0);
	User user = new User("ryan", 1);
	SendableState updatedState = new SendableState(serverState);
	
	ClientGameState cgState = new ClientGameState(user);
	boolean isConnected = true;
	/**
	 * Test method for {@link game.client.ClientGameState#ClientGameState(game.util.User)}.
	 */
	@Test
	public final void testClientGameState() {
		
		assertNull(updatedState);
		assertNotNull(user);
		
		//cgState.updateClientState(updatedState);
		
	}

	/**
	 * Test method for {@link game.client.ClientGameState#addSoundManager(game.client.Sound)}.
	 */
	@Test
	public final void testAddSoundManager() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link game.client.ClientGameState#updateClientState(game.util.SendableState)}.
	 */
	@Test
	public final void testUpdateClientState() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link game.client.ClientGameState#setUpMapData(java.lang.String)}.
	 */
	@Test
	public final void testSetUpMapData() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link game.client.ClientGameState#getPlayer()}.
	 */
	@Test
	public final void testGetPlayer() {
		assertNull(cgState.getPlayer());
		
	}

	/**
	 * Test method for {@link game.client.ClientGameState#getOtherPlayer()}.
	 */
	@Test
	public final void testGetOtherPlayer() {
		fail("Not yet implemented"); // TODO
	}

}
