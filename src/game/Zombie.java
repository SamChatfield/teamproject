package game;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

public class Zombie {
	private static final int DIAMETER = 20;

	public int x;
	public int y;
	public int xa;
	public int ya;
	private Game game;
	
	int health = 500;

	public Zombie(Game game, int x, int y, int xa, int ya) {
		this.game = game;
		this.x = x;
		this.y = y;
		this.xa = xa;
		this.ya = ya;
	}

	public void paint(Graphics2D g) {
		g.fillOval(x, y, DIAMETER, DIAMETER);
	}

	void move() {
		if (x + xa < 0) {
			xa = 2;
			// game.edgeOfMap();
		}
		if (x + xa > game.getWidth() - DIAMETER) {
			xa = -2;
			// game.edgeOfMap();
		}
		if (y + ya < 0) {
			ya = 2;
			// game.edgeOfMap();
		}
		if (y + ya > game.getHeight() - DIAMETER) {
			ya = -2;
			// game.edgeOfMap();
		}

		x = x + xa;
		y = y + ya;
	}

	
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getXa() {
		return xa;
	}

	public int getYa() {
		return ya;
	}

	public int getDiameter() {
		return DIAMETER;
	}

}
