package game.client;

import static org.junit.Assert.*;

import java.awt.image.BufferStrategy;

import org.junit.Test;

public class MenuRendererTest {
	BufferStrategy bs;
	MenuRenderer m = new MenuRenderer(bs);

	@Test
	public void testMenuRenderer() {
		//assertEquals(m.)
	}

	@Test
	public void testRenderHelpOptions() {
		fail("Not yet implemented");
	}

	@Test
	public void testRenderMenu() {
		assertNotNull(m.helpOptionsButton);
	}

}
