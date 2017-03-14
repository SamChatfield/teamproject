package game;

import java.awt.Graphics2D;

public class Weapon {
	private static final int WIDTH = 5;
	private static final int HEIGHT = 5;

	int x = 10;
	int y = 10;
	int xv = 10000000;
	int yv = 10000000;
	int direction = 0;

	public Weapon(int direction, int x, int y, int xv, int yv) {

		this.direction = direction;
		this.x = x;
		this.y = y;
		this.yv = yv;
		this.xv = xv;

	}

	public void paint(Graphics2D g) {
		g.fillRect(x, y, WIDTH, HEIGHT);
	}

	public void move() {
		if (direction == -2) {
			x = x + xv;
		}
		if (direction == 2) {
			x = x + xv;
		}

		if (direction == 1) {
			y = y + yv;
		}
		if (direction == -1) {
			y = y + yv;
		}


	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public static int getWidth() {
		return WIDTH;
	}

	public static int getHeight() {
		return HEIGHT;
	}

	
	
}
