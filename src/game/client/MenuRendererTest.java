package game.client;

import static org.junit.Assert.assertNotNull;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import org.junit.Before;
import org.junit.Test;

public class MenuRendererTest extends Canvas{
	
	JFrame container;
	Graphics2D g2d;
	MenuRenderer m;
	

	@Before
	public void setUp() {
		container = new JFrame("test");
		JPanel panel = (JPanel) container.getContentPane();
		panel.setPreferredSize(new Dimension(640,640));
		panel.setLayout(null);

		setBounds(0, 0, 640, 640);
		panel.add(this);

			
		setIgnoreRepaint(true);

		container.pack();
		container.setResizable(false);
		container.setVisible(true);
		container.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		createBufferStrategy(2);
		BufferStrategy bs = getBufferStrategy();
		
		g2d = (Graphics2D) bs.getDrawGraphics();
		
		m = new MenuRenderer(bs);

	}
	@Test
	public void testMenuRenderer() {
		assertNotNull(m);
	}

	@Test
	public void testRenderHelpOptions() {
		m.renderHelpOptions();
	}

	@Test
	public void testRenderMenu() {
		m.renderMenu();
	}

}
