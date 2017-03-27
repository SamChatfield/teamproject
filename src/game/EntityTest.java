package game;

import static org.junit.Assert.*;



import game.Bullet;
import game.Entity;
import game.ResourceLoader;
import game.Zombie;
import game.map.MapData;
import game.server.ServerGameState;
import game.util.DataPacket;
import game.util.DataPacket.Type;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import game.Bullet;
import game.Entity;
import game.client.Player;
import java.awt.*;
//import game.map.MapData;
import java.util.ArrayList;

import game.map.MapData;
import game.util.DataPacket;


/**
 * @author ryan-
 *
 */
public class EntityTest {

//	------------------------------------------------------------------
//-----
//Player Tests
	

	/**
	 * Test method for {@link game.client.Player#Player(float, float, game.map.MapData, java.lang.String)}.
	 */
	
	ServerGameState s;
	
	Player player;
	Player player1; 
	Bullet bullet; 
	Entity entity;
	Entity entityPU; 
	Zombie zombie; 
	private ArrayList<Bullet> bullets;
	protected transient MapData mapData;
	//DataPacket data = new DataPacket(1, 1, 0.3f, 50, 10l, null);
	DataPacket data = new DataPacket(1, 1, 0.3f, 50, 100l, null, true, 100, false, 1000);
	
	@Before
	public final void setUp(){
		s = new ServerGameState("ryan", "becca", 1);
		s.startNewGame();
		MapData m = s.getMapData();
		
		player = new Player(1, 1, m, "ryan"); 
		player1 = new Player(1, 1, m, "becca");
		
		bullet = new Bullet(player, 1, 1, 1, 1, null);
		entity = new Entity (1, 1,  0.3f, 50, m, null);
		entityPU = new Entity(1,1,m);
		zombie = new Zombie(1,1,m, 0);
	}
	
	@Test
	public final void testPlayer() {
		//Player player = new Player(1, 1, null, null); 
		assertEquals(1, player.getX(), 0);
		assertEquals(1, player.getY(), 0);
		assertTrue(!player.conversionMode);
		player.setMoveSpeed(0.3f);
		assertEquals(0.3, player.getMoveSpeed(), 0.01);
		assertEquals(player.getData().getType(),game.util.DataPacket.Type.PLAYER);
	}

	/**
	 * Test method for {@link game.client.Player#shoot(float, float, float, float)}.
	 */
	@Test
	public final void testShoot() {
		//Bullet bullet = new Bullet(player, 1, 1, 1, 1, null);
		//long now = System.nanoTime();
		long time = 1l;
		assertNotNull(time);
		player.setLastAttackTime(time);
		assertNotNull(player.getLastAttackTime());
		assertEquals(0.3, bullet.BULLET_SPEED, 0.001);
		
		float aimX = 1;
		float aimY = 1;
		float pdx = 1;
		float pdy = 1;
		Bullet testBullet = player.shoot(aimX, aimY, pdx, pdy);
		assertNotSame(testBullet, bullet);
		assertNotNull(testBullet);
		
		Player player1 = new Player(1, 1, null, null); 
		time = 10000000000000000l;
		player1.setLastAttackTime(time);
		//player1.getLastAttackTime();
		assertEquals( bullet.getX(), (player1.getX()), 0.1);
		assertEquals( bullet.getY(), (player1.getY()), 0.1);
		
		assertNull(player1.shoot(aimX, aimY, pdx, pdy));
	}

	/**
	 * Test method for {@link game.client.Player#canShoot()}.
	 */
	@Test
	public final void testCanShoot() {
		//assertTrue(!player.canShoot());
		
		long time = 100000000000l;
		player.setLastAttackTime(time);
		assertTrue(player.canShoot());
		time = 999999999999999l;
		player.setLastAttackTime(time);
		assertTrue(!player.canShoot());
	}

	Graphics2D g2d;
	//g2d.setColour(Color.BLACK);
	/**
	 * Test method for {@link game.client.Player#draw(java.awt.Graphics2D)}.
	 */
	@Test
	public final void testDraw() {
		player.setShowCollBox(true);
		//player.draw(g2d);
		
		
		player.setShowCollBox(false);
		//player.draw(g2d);
		
		
		
	}

	/**
	 * Test method for {@link game.client.Player#setShowCollBox(boolean)}.
	 */
	@Test
	public final void testSetShowCollBox() {
		player.setShowCollBox(false);
		//assertTrue(!Player.showCollBox);
	}

// ---------------------------------------------------------------------------------------------
	
	//Zombie Tests
	/**
	 * Test method for {@link game.Zombie#Zombie(float, float, game.map.MapData)}.
	 */
	@Test
	public final void testZombie() {
		Zombie zombie = new Zombie(1,1,null, 0);
		assertNotNull(zombie);
	}

	/**
	 * Test method for {@link game.Zombie#getDx()}.
	 */
	@Test
	public final void testGetDx() {
		assertNotNull(zombie.getDx());
	}

	/**
	 * Test method for {@link game.Zombie#getDy()}.
	 */
	@Test
	public final void testGetDy() {
		
		assertNotNull(zombie.getDy());
	}

	/**
	 * Test method for {@link game.Zombie#convert(java.lang.String)}.
	 */
	@Test
	public final void testConvert() {
		String username = "ryan";
		zombie.convert(username);
		zombie.setUsername(username);
		assertEquals("ryan", zombie.getUsername());	
	}

	/**
	 * Test method for {@link game.Zombie#move(double)}.
	 */
	@Test
	public final void testMoveDouble() {
		float moveX = 1;
		float moveY = 1;
		zombie.move(1f);
	}

	/**
	 * Test method for {@link game.Zombie#followDirection(game.client.Player)}.
	 
	@Test
	public final void testFollowDirection() {
		zombie.followDirection(player);
		
		
	}
*/
	/**
	 * Test method for {@link game.Zombie#newMovingDir()}.
	 */
	@Test
	public final void testNewMovingDir() {
		
		assertNotNull(zombie.getDx());
		assertNotNull(zombie.getDy());
		zombie.newMovingDir();
		assertNotNull(zombie.getDx());
		assertNotNull(zombie.getDy());
	}

	/**
	 * Test method for {@link game.Zombie#attack(game.Entity, int)}.
	 */
	@Test
	public final void testAttack() {
		assertEquals(player.getHealth(), 50);
		zombie.attack(player, 10);
		assertEquals(player.getHealth(), 40);
		assertEquals(40,player.getHealth());
	}


	
	

//   -----------------------------------------------------------------------------
	//Test Entity 


	
	
	/**
	 * Test method for {@link game.Entity#getState()}.
	 */
	@Test
	public final void testGetState() {
		assertNull(player.getState());
	}

	/**
	 * Test method for {@link game.Entity#move(float, float)}.
	 */
	@Test
	public final void testMove() {
		player.move(0.01f, 0.01f, bullet);
		
	}

	/**
	 * Test method for {@link game.Entity#updateData(game.util.DataPacket)}.
	 */
	@Test
	public final void testUpdateData() {
		player.updateData(data);
		assertNotNull(player.getData());
	}

	
	@Test
	public final void testGetAppearTimePD() {
		assertNotNull(player.getAppearTimePD());
	}
	
	@Test
	public final void testGetAppearTime() {
		assertNotNull(player.getAppearTime());
	}
	
	@Test
	public final void testSetAlive() {
		assertTrue(data.isAlive());
		data.setAlive(false);
		assertTrue(!data.isAlive());
		player.setAlive(true, 0);
		assertTrue(player.isAlive());
	}
	
	@Test
	public final void testGetCurrentlyEquipped() {
		assertNotNull(player.getCurrentlyEquipped());
	}

	@Test
	public final void testGetx() {
		assertNotNull(player.getx());
	}
	
	@Test
	public final void testGety() {
		assertNotNull(player.gety());
	}
	
	/**
	 * Test method for {@link game.Entity#updateLocalPlayerData(game.util.DataPacket)}.
	 */
	@Test
	public final void testUpdateLocalPlayerData() {
		player.updateLocalPlayerData(data);
		
	}


	/**
	 * Test method for {@link game.Entity#getNumConvertedZombies()}.
	 */
	@Test
	public final void testGetNumConvertedZombies() {
		entity.setNumConvertedZombies(10);
		assertEquals(10, entity.getNumConvertedZombies());
		
	}

	/**
	 * Test method for {@link game.Entity#getCollisionBox()}.
	 */
	@Test
	public final void testGetCollisionBox() {
		assertNotNull(player.getCollisionBox());
	}

}
