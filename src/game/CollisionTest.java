/**
 * 
 */
package game;

import static org.junit.Assert.*;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import org.junit.Test;

import game.PowerUp.PuState;
import game.Weapon.WeaponState;
import game.client.Player;
import game.map.MapData;
import game.util.DataPacket;

//
/**
 * @author ryan-
 *
 */
public class CollisionTest {
		Player player = new Player(1, 1, null, "ryan");
		Player player1 = new Player(3, 3, null, "ryan");
		Bullet bullet = new Bullet(player, 1, 1, 1, 1, null);
		Entity entity = new Entity (1, 1,  0.3f, 50, null, null); 
		Zombie zombie = new Zombie(1,1,null, 10);
		Zombie zombie1 = new Zombie(1,1,null, 0);
		Zombie zombie2 = new Zombie(2,2,null, 0);
		ArrayList<Zombie> zombies = new ArrayList<>();
		
		PuState pu = PuState.HEALTH;
		PuState pu1 = PuState.SPEED_UP;
        
		PowerUp power = new PowerUp(2, 2, null, pu, 0);
		PowerUp power1 = new PowerUp(2, 2, null, pu1, 0);
		Player playerPU = new Player(2, 2, null, "ryan");
		Player opponent = new Player(3, 3, null, "ryan");
		
		Weapon.WeaponState w = WeaponState.PISTOL;
		Weapon weapon = new Weapon (2, 2, null, w, 5);
		Weapon weapon1 = new Weapon (3, 3, null, w, 5);
		
		
		private ArrayList<Bullet> bullets;
	
		protected transient MapData mapData;
		DataPacket data = new DataPacket(1, 1, 0.3f, 50, 10l, null);
		CollisionBox collBox = new CollisionBox(player);
		CollisionBox collBox1 = new CollisionBox(player);
		Rectangle2D.Float rectangle;
		Collision coll = new Collision();

	/**
	 * Test method for {@link game.Collision#checkCollision(game.Zombie, game.client.Player)}.
	 */
	@Test
	public final void testCheckCollision() {
		coll.checkCollision(zombie, player);
		assertEquals(40,player.getHealth(), 0.01);
		coll.checkCollision(zombie, player);
		assertEquals(50,player1.getHealth(), 0.01);
	}
	/**
	 * Test method for {@link game.Collision#checkPlayerCollision(game.Bullet, game.client.Player)}.
	 */
	@Test
	public final void testCheckPlayerCollision() {
		coll.checkPlayerCollision(bullet, player, opponent);
		assertEquals(25,player.getHealth(), 0.01);
		assertEquals(50,player1.getHealth(), 0.01);
	}

	/**
	 * Test method for {@link game.Collision#checkBulletCollision(game.Bullet, java.util.ArrayList, game.client.Player)}.
	 */
	@Test
	public final void testCheckBulletCollision() {
		zombies.add(zombie); 
		zombies.add(zombie1);
		zombies.add(zombie2);
		coll.checkBulletCollision(bullet, zombies,player);
		assertEquals(0,zombies.get(0).getHealth(), 0.01);
		assertEquals(25,zombies.get(1).getHealth(), 0.01);
		assertEquals(25,zombies.get(2).getHealth(), 0.01);
	}
	
	
	@Test
	public final void testCheckPowerupCollision() {
		coll.checkPowerupCollision(power, playerPU, opponent, zombies );
		coll.checkPowerupCollision(power, opponent, playerPU, zombies );
	}
	
	@Test
	public final void testWeaponCollision() {
		coll.checkWeaponCollision(weapon, player);
		coll.checkWeaponCollision(weapon1, opponent);
		
	}
	
	@Test
	public final void testPlayersColliding() {
		coll.playersColliding(player, opponent);
		
	}

}
