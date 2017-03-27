package game.map;

import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.junit.Assert.assertEquals;

public class TileTypeTest {
	
	TileType type;
	Color color = new Color(0, 150, 0);

	@Before
	public void setUp() {
		type = new TileType("cake", color, null, true);
	}

	@Test
	public void testGetName() {
		String name = type.getName();
		assertEquals(name, "cake");
	}
	
	@Test
	public void testgetRepColour() {
		Color c = type.getRepColour();
		assertEquals(c, color);
	}
	
	@Test
	public void testGetImage() {
		BufferedImage img = type.getImage();
		assertEquals(img, null);
	}
	
	@Test
	public void testIsObstacle() {
		boolean isObstacle = type.isObstacle();
		assertEquals(isObstacle, true);
	}

}
