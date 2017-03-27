/**
 * 
 */
package game.util;

import game.Bullet;
import game.Entity;
import game.Zombie;
import game.client.Player;
import game.map.MapData;
import game.server.ServerGameState;
import game.server.Timer;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

//--
/**
 * @author ryan-
 *
 */
public class SendableStateTest {
	ServerGameState servState = new ServerGameState("ryan", "becca", 1);
	SendableState sState; 
	
	Player player1 = new Player(1, 1, null, "ryan"); 
	Player player2 = new Player(1, 1, null, "becca"); 
	
	Bullet bullet = new Bullet(player1, 1, 1, 1, 1, null);
	ArrayList<Bullet> b = new ArrayList<>();
	
	Entity entity = new Entity (1, 1,  0.3f, 50, null, null); 
	Entity entityPU = new Entity(1,1,null);
	
	Zombie zombie = new Zombie(1,1,null, 0);
	ArrayList<Zombie> zombies = new ArrayList<>();
	
	private ArrayList<DataPacket> bullets;
	protected transient MapData mapData;
	//DataPacket data = new DataPacket(1, 1, 0.3f, 50, 10l, null);
	DataPacket data = new DataPacket(1, 1, 0.3f, 50, 100l, null, true, 100, false, 1000);
	
	Timer t = new Timer(180,servState);

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		servState.startNewGame();
		b.add(bullet);
		zombies.add(zombie);
		servState.setBullets(b);
		//servState.setZombies(zombies);
		sState = servState.getPackagedState();
	}


	/**
	 * Test method for {@link game.util.SendableState#getBullets()}.
	 */
	@Test
	public final void testGetBullets() {
		assertEquals(b,servState.getBullets());
	}

	/**
	 * Test method for {@link game.util.SendableState#getTimeRemaining()}.
	 */
	@Test
	public final void testGetTimeRemaining() {
		assertEquals(sState.getTimeRemaining(),180);
	}

	/**
	 * Test method for {@link game.util.SendableState#getMapImage()}.
	 */
	@Test
	public final void testGetMapImage() {
		assertNotNull(sState.getMapImage());
	}


	/**
	 * Test method for {@link game.util.SendableState#getZombies()}.
	 */
	@Test
	public final void testGetZombies() {
		assertEquals(sState.getZombies(),servState.getSendableZombies());
	}

	/**
	 * Test method for {@link game.util.SendableState#getPowerups()}.
	 */
	@Test
	public final void testGetPowerups() {
		assertEquals(sState.getPowerups(),servState.getPowerups());
	}

	/**
	 * Test method for {@link game.util.SendableState#getWeapons()}.
	 */
	@Test
	public final void testGetWeapons() {
		assertEquals(sState.getPowerups(),servState.getWeapons());
	}

	/**
	 * Test method for {@link game.util.SendableState#getPlayer(java.lang.String)}.
	 */
	@Test
	public final void testGetPlayer() {
		assertEquals(sState.getPlayer("ryan"),servState.getPlayer1().getData());
	}

	/**
	 * Test method for {@link game.util.SendableState#getPlayer1()}.
	 */
	@Test
	public final void testGetPlayer1() {
		assertEquals(sState.getPlayer1(),servState.getPlayer1().getData());
	}

	/**
	 * Test method for {@link game.util.SendableState#getPlayer2()}.
	 */
	@Test
	public final void testGetPlayer2() {
		assertEquals(sState.getPlayer2(),servState.getPlayer2().getData());
	}

	/**
	 * Test method for {@link game.util.SendableState#HasFinished()}.
	 */
	@Test
	public final void testHasFinished() {
		assertEquals(sState.HasFinished(),false);
	}

	/**
	 * Test method for {@link game.util.SendableState#SendableState(game.server.ServerGameState)}.
	 */
	@Test
	public final void testSendableState() {
		assertNotNull(sState);
	}

}
