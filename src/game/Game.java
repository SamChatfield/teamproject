package game;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

//@SuppressWarnings("serial")
public class Game extends JPanel {

	ArrayList<Zombie> zombieList = new ArrayList<Zombie>();
	ArrayList<Weapon> weaponList = new ArrayList<Weapon>();
	boolean musicOn = true;
	boolean SFXOn = true;

	Player player = new Player(this);
	Random r = new Random();

	private void makeZombie(int n) {
		for (int i = 0; i < n; i++) {
			zombieList.add(new Zombie(this, r.nextInt(641), r.nextInt(641), (r.nextBoolean() ? 1 : -1),
					(r.nextBoolean() ? 1 : -1)));
		}
	}

	/*
	 * public void makeBullet(Weapon w){ weaponList.add(new Weapon(this,
	 * w.direction, w.x, w.y, w.xv, w.yv)); }
	 */

	public Game() {
		addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
				player.keyReleased(e);
			}

			@Override
			public void keyPressed(KeyEvent e) {
				player.keyPressed(e);

			}
			
			
		});


		setFocusable(true);
	}

	
	public void shootBullet(int d, int x, int y){
		weaponList.add( new Weapon(d, x, y, player.xa, player.ya));
		System.out.println(weaponList);
	}
	
	private void move() {

		for (int i = 0; i < zombieList.size(); i++) {
			zombieList.get(i).move();
		}

		for (int i = 0; i < weaponList.size(); i++) {
			weaponList.get(i).move();
		}

		player.move();

	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		for (int i = 0; i < zombieList.size(); i++) {
			zombieList.get(i).paint(g2d);
		}

		for (int i = 0; i < weaponList.size(); i++) {
			weaponList.get(i).paint(g2d);
		}

		player.paint(g2d);

	}

	public void edgeOfMap() {
		JOptionPane.showMessageDialog(null, "at the edge");
		// System.exit(ABORT);
	}

	public void collide() {
		Collision.checkZombiePlayerCol(zombieList, player);
		Collision.checkWeaponCol(weaponList, zombieList);
	}

	public static void gameOver() {

		JOptionPane.showMessageDialog(null, "YOU LOSE");
		System.exit(ABORT);

	}

	public static void main(String[] args) throws InterruptedException {
		JFrame frame = new JFrame("Zombies");
		Game game = new Game();
		frame.add(game);
		frame.setSize(640, 640);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		game.makeZombie(5);

		while (true) {
			game.move();
			game.repaint();
			game.collide();

			// simple thread.sleep probably going to want to change this
			Thread.sleep(10);

		}
	}

}