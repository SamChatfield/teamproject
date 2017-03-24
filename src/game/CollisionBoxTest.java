/**
 * 
 */
package game;

import static org.junit.Assert.*;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import org.junit.Test;

import game.client.Player;
import game.map.MapData;
import game.util.DataPacket;

/**
 * @author ryan-
 *
 */
public class CollisionBoxTest {
	Player player = new Player(1, 1, null, "ryan");
	//Player player1 = new Player(1, 1, null, "ryan");
	Bullet bullet = new Bullet(player, 1, 1, 1, 1, null);
	Entity entity = new Entity (1, 1,  0.3f, 50, null, null); 
	Zombie zombie = new Zombie(1,1,null, 0);
	private ArrayList<Bullet> bullets;
	protected transient MapData mapData;
	DataPacket data = new DataPacket(1, 1, 0.3f, 50, 10l, null);
	CollisionBox collBox = new CollisionBox(player);
	CollisionBox collBox1 = new CollisionBox(player);
	Rectangle2D.Float rectangle;
	/**
	 * Test method for {@link game.CollisionBox#getRect()}.
	 */
	@Test
	public final void testGetRect() {
		assertNotNull(collBox.getRect());
	}

	/**
	 * Test method for {@link game.CollisionBox#getDrawRect(game.client.Player)}.
	 */
	@Test
	public final void testGetDrawRect() {
		
		assertEquals((collBox.getDrawRect(player)),(collBox1.getDrawRect(player)));
		
	}


	/**
	 * Test method for {@link game.CollisionBox#getWidth()}.
	 */
	@Test
	public final void testGetWidth() {
		assertNotNull(collBox.getWidth());
	}

	/**
	 * Test method for {@link game.CollisionBox#getHeight()}.
	 */
	@Test
	public final void testGetHeight() {
		assertNotNull(collBox.getHeight());
	}

	/**
	 * Test method for {@link game.CollisionBox#intersects(game.CollisionBox)}.
	 */
	@Test
	public final void testIntersects() {
		assertTrue(collBox.intersects(collBox1));
	}

	/**
	 * Test method for {@link game.CollisionBox#collBoxRectFromData(game.util.DataPacket, game.client.Player)}.
	 */
	@Test
	public final void testCollBoxRectFromData() {
		collBox.collBoxRectFromData(data, player);
		
	}

}
