/**
 * 
 */
package game.util;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import game.Bullet;
import game.Entity;
import game.Zombie;
import game.client.Player;
import game.map.MapData;
import game.server.ServerGameState;

//--
/**
 * @author ryan-
 *
 */
public class SendableStateTest {
	ServerGameState servState = new ServerGameState("ryan", "becca", 1);
	SendableState sState = new SendableState(servState);
	
	Player player = new Player(1, 1, null, "ryan"); 
	Player player1 = new Player(1, 1, null, "becca"); 
	Bullet bullet = new Bullet(player, 1, 1, 1, 1, null);
	Entity entity = new Entity (1, 1,  0.3f, 50, null, null); 
	Entity entityPU = new Entity(1,1,null);
	Zombie zombie = new Zombie(1,1,null, 0);
	private ArrayList<DataPacket> bullets;
	protected transient MapData mapData;
	//DataPacket data = new DataPacket(1, 1, 0.3f, 50, 10l, null);
	DataPacket data = new DataPacket(1, 1, 0.3f, 50, 100l, null, true, 100, false, 1000);
	
	

	/**
	 * Test method for {@link game.util.SendableState#getBullets()}.
	 */
	@Test
	public final void testGetBullets() {
		fail("Not yet implemented"); // TODO
		//assertNotNull(sState.getBullets());
	}

	/**
	 * Test method for {@link game.util.SendableState#getTimeRemaining()}.
	 */
	@Test
	public final void testGetTimeRemaining() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link game.util.SendableState#getMapImage()}.
	 */
	@Test
	public final void testGetMapImage() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link game.util.SendableState#getDeadZombies()}.
	 */
	@Test
	public final void testGetDeadZombies() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link game.util.SendableState#getZombies()}.
	 */
	@Test
	public final void testGetZombies() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link game.util.SendableState#getPowerups()}.
	 */
	@Test
	public final void testGetPowerups() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link game.util.SendableState#getWeapons()}.
	 */
	@Test
	public final void testGetWeapons() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link game.util.SendableState#getPlayer(java.lang.String)}.
	 */
	@Test
	public final void testGetPlayer() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link game.util.SendableState#getPlayer1()}.
	 */
	@Test
	public final void testGetPlayer1() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link game.util.SendableState#getPlayer2()}.
	 */
	@Test
	public final void testGetPlayer2() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link game.util.SendableState#HasFinished()}.
	 */
	@Test
	public final void testHasFinished() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link game.util.SendableState#SendableState(game.server.ServerGameState)}.
	 */
	@Test
	public final void testSendableState() {
		fail("Not yet implemented"); // TODO
	}

}
