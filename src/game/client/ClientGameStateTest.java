package game.client;

import game.Bullet;
import game.Zombie;
import game.server.ServerGameState;
import game.util.EndState;
import game.util.GameState;
import game.util.SendableState;
import game.util.User;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ClientGameStateTest {
	
	ServerGameState servState = new ServerGameState("ryan","becca",1);
	Player player = new Player(1, 1, null, "ryan"); 
	Player player1 = new Player(1, 1, null, "becca"); 
	GameState state = new GameState();
	EndState endState = new EndState(false, null, player, player1, null);
	ArrayList<Bullet> bullets;
	ArrayList<Zombie> zombies;
	Zombie zombie = new Zombie(1,1,null, 0);
	Zombie zombie1 = new Zombie(1,1,null, 0);
	User user = new User("ryan", 1);
	Sound m = new Sound();
	
	SendableState sState;
	
	ClientGameState cgState; 
	boolean isConnected = true;

	@Before
	public void setUp() throws Exception {
		servState.startNewGame();
		sState = servState.getPackagedState();
	
		cgState = new ClientGameState(user);
		cgState.updateClientState(sState);

	}



	@Test
	public void testResetState() {
		cgState.resetState(user);
		assertNull(cgState.getMapImage());
	}

	@Test
	public void testAddSoundManager() {
		cgState.addSoundManager(m);
	}

	@Test
	public void testGetPlayer() {
		assertEquals(cgState.getPlayer().getData().getUsername(),"ryan");
	}

	@Test
	public void testGetOtherPlayer() {
		assertEquals(cgState.getOtherPlayer().getData().getUsername(),"becca");
	}

	@Test
	public void testGetAliveZombies() {
		assertEquals(cgState.getAliveZombies().size(),20);
	}

}
