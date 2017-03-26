package game.util;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import game.Bullet;
import game.Entity;
import game.Zombie;
import game.client.ClientGameState;
import game.client.Player;
import game.map.MapData;
import game.server.ServerGameState;
import game.server.Timer;

public class PlayerUpdatePacketTest {

	PlayerUpdatePacket pup;
	ArrayList<String> keyPresses = new ArrayList<>();
	Player p;
	DataPacket d;
	
	// We need some stuff for sendable state
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

	float x,y;
		
	
	@Before
	public void setUp() throws Exception {
		
		servState.startNewGame();
		b.add(bullet);
		zombies.add(zombie);
		servState.setBullets(b);
		sState = servState.getPackagedState();
		
		
		User u = new User("ryan",1);
		ClientGameState g = new ClientGameState(u);
		g.updateClientState(sState);
		p = g.getPlayer();
		d = p.getData();
		keyPresses.add("VK_W");
		
		x = -100;
		y = -100;
		pup = new PlayerUpdatePacket(d,keyPresses,0.0d,x,y);
		assertNotNull(pup);
	}

	@Test
	public void testGetDelta() {
		//assertEquals(pup.getfX(),-100.0f);
	}

	@Test
	public void testGetfX() {
		//assertEquals(pup.getfX(),-100);
	}

	@Test
	public void testGetfY() {
		//fail("Not yet implemented");
	}

	@Test
	public void testGetKeyPresses() {
		assertEquals(keyPresses,pup.getKeyPresses());
	}

	@Test
	public void testGetData() {
		assertEquals(pup.getData(),d);
	}

}
