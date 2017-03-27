package game.server;

import game.Bullet;
import game.Entity;
import game.Zombie;
import game.client.Player;
import game.util.DataPacket;
import game.util.PlayerUpdatePacket;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class ServerGameStateTest {
	
	ServerGameState servState = new ServerGameState("ryan", "becca", 1);
	
	Player player1 = new Player(1, 1, null, "ryan"); 
	Player player2 = new Player(1, 1, null, "becca"); 
	
	Bullet bullet = new Bullet(player1, 1, 1, 1, 1, null);
	ArrayList<Bullet> b = new ArrayList<>();
	
	Entity entity = new Entity (1, 1,  0.3f, 50, null, null); 
	Entity entityPU = new Entity(1,1,null);
	
	Zombie zombie = new Zombie(1,1,null, 0);
	ArrayList<Zombie> zombies = new ArrayList<>();

	@Before
	public void setUp() throws Exception {
		servState.startNewGame();
		servState.setZombies(zombies);
		servState.setBullets(b);
	}


	@Test
	public void testGetPlayer() {
		assertEquals(servState.getPlayer("ryan").getUsername(),"ryan");
		assertEquals(servState.getPlayer("becca").getUsername(),"becca");
		assertNull(servState.getPlayer("should return null"));
	}

	@Test
	public void testGetOtherPlayer() {
		assertEquals(servState.getOtherPlayer("ryan").getUsername(),"becca");
		assertEquals(servState.getOtherPlayer("becca").getUsername(),"ryan");
		assertNull(servState.getOtherPlayer("should return null"));
	}

	@Test
	public void testGetSendableZombies() {
		assertEquals(servState.getSendableZombies().size(),zombies.size());
	}

	@Test
	public void testGetSendableBullets() {
		assertEquals(servState.getSendableBullets().size(),b.size());
	}

	@Test
	public void testGetSendablePowerups() {
		assertNotNull(servState.getSendablePowerups());
	}

	@Test
	public void testGetSendableWeapons() {
		assertNotNull(servState.getSendableWeapons());
	}

	@Test
	public void testUpdatePlayer() {
		ArrayList<String> keyPresses = new ArrayList<>();
		keyPresses.add("VK_W");
		keyPresses.add("VK_A");
		keyPresses.add("VK_S");
		keyPresses.add("VK_D");
		keyPresses.add("VK_1");
		keyPresses.add("VK_2");
		keyPresses.add("VK_3");
		keyPresses.add("VK_4");
		keyPresses.add("VK_5");
		keyPresses.add("BUTTON1");

		
		DataPacket d = servState.getPlayer("ryan").getData();
		float x = -100;
		float y = -100;
		PlayerUpdatePacket pup = new PlayerUpdatePacket(d,keyPresses,0.0d,x,y);
		
		float preUpdateX = servState.getPlayer("ryan").getX();
		float preUpdateY = servState.getPlayer("ryan").getY();

		servState.updatePlayer("ryan", pup);
		
		assertEquals(servState.getPlayer("ryan").getData().getX(),preUpdateX,1);
		assertEquals(servState.getPlayer("ryan").getCurrentlyEquipped(),game.Weapon.WeaponState.PISTOL); // gun 1 (loop breaks after first selection)
	}

	@Test
	public void testGetPackagedState() {
		assertNotNull(servState.getPackagedState());
	}

	@Test
	public void testSetPlayer1Username() {
		servState.setPlayer1Username("ryanNew");
	}

	@Test
	public void testSetPlayer2Username() {
		servState.setPlayer2Username("beccaNew");
	}

}
