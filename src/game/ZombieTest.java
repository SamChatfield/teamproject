/**
 * 
 */
package game;

import static org.junit.Assert.*;

import org.junit.Test;

import game.client.Player;

/**
 * @author ryan-
 *
 */
public class ZombieTest {
	Player player = new Player(1, 1, null, "ryan"); 
	Bullet bullet = new Bullet(player, 1, 1, 1, 1, null);
	Entity entity = new Entity (1, 1,  0.3f, 50, null, null); 
	Zombie zombie = new Zombie(1,1,null, 0);
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
		zombie.move(0);
		float moveX = 1;
		float moveY = 1;
		entity.move(moveX,moveY);
	}

	/**
	 * Test method for {@link game.Zombie#followDirection(game.client.Player)}.
	 */
	@Test
	public final void testFollowDirection() {
		zombie.followDirection(player);
		
		
	}

	/**
	 * Test method for {@link game.Zombie#newMovingDir()}.
	 */
	@Test
	public final void testNewMovingDir() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link game.Zombie#attack(game.Entity, int)}.
	 */
	@Test
	public final void testAttack() {
		fail("Not yet implemented"); // TODO
	}

}
