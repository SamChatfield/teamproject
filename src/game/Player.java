package game;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

public class Player {
	// private static final int Y = 330;
	private static final int WIDTH = 30;
	private static final int HEIGHT = 30;
	int x = 295;
	int xa = 0;
	int y = 295;
	int ya = 0;
	private Game game;
	static int direction;

	Weapon weapon;

	int health = 1000;

	public Player(Game game) {
		this.game = game;
	}

	public void paint(Graphics2D g) {
		g.fillRect(x, y, WIDTH, HEIGHT);
	}
	// going to the sides will bump you back in

	public void move() {
		if (x + xa >= 0) {
			x = x + xa;
		} else {
			// game.edgeOfMap(); // do whatever when it reaches the edge of the
			// map
			xa = 1;
		}
		if (x + xa < game.getWidth() - WIDTH) {
			x = x + xa;
		} else {
			// game.edgeOfMap(); // do whatever when it reaches the edge of the
			// map
			xa = -1;
		}

		// going to the top or bottom will stop you moving off the map
		if (y + ya > 0) {
			y = y + ya;
		} else {
			// game.edgeOfMap(); // do whatever when it reaches the edge of the
			// map
			ya = 1;
		}
		if (y + ya < game.getHeight() - HEIGHT) {
			y = y + ya;
		} else {
			// game.edgeOfMap(); // do whatever when it reaches the edge of the
			// map
			ya = -1;
		}

	}

	public void keyReleased(KeyEvent e) {
		xa = 0;
		ya = 0;
	}

	public void keyPressed(KeyEvent e) {

		if (e.getKeyCode() == KeyEvent.VK_W) {
			ya = -1;
			direction = 1;
		}
		if (e.getKeyCode() == KeyEvent.VK_A) {
			xa = -1;
			direction = -2;
		}
		if (e.getKeyCode() == KeyEvent.VK_S) {
			ya = 1;
			direction = -1;
		}
		if (e.getKeyCode() == KeyEvent.VK_D) {
			xa = 1;
			direction = 2;
		}
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			game.shootBullet(direction, x, y);
		}

	}

	public int getTopY() {
		return y;
	}

	public int getBottomY() {
		return y - HEIGHT;
	}

	public int getWidth() {
		return WIDTH;
	}

	public int getHeight() {
		return HEIGHT;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

}