package game;

import java.awt.Rectangle;
import java.util.ArrayList;

public class Collision {

	public static void checkZombiePlayerCol(ArrayList<Zombie> zombies, Player p) {

		for (int i = 0; i < zombies.size(); i++) {

			Zombie z = zombies.get(i);
			Rectangle rz = new Rectangle(z.getX(), z.getY(), z.getDiameter(), z.getDiameter());
			Rectangle rp = new Rectangle(p.getX(), p.getY(), p.getWidth(), p.getHeight());

			if (rz.intersects(rp)) {
				z.xa = p.xa;
				z.ya = p.ya;

				if (p.health == 0) {
					Game.gameOver();
				}

				else {
					p.health -= 1;
					//System.out.println("Player health: " + p.health);
				}

			}

		}

	}
	
	
	public static void checkWeaponCol(ArrayList<Weapon> bullets, ArrayList<Zombie> zombies, Sound soundManager) {
		//Check weapon collision
		for (int i = 0; i < zombies.size(); i++) {
			Zombie z = zombies.get(i);
			
			for (int j = 0; j < bullets.size(); j++) {
				Weapon b = bullets.get(j);
				
				Rectangle rz = new Rectangle(z.getX(), z.getY(), z.getDiameter(), z.getDiameter());
				Rectangle rb = new Rectangle(b.getX(), b.getY(), b.getWidth(), b.getHeight());

				
				if (rz.intersects(rb)) {
					//not great as it plays it multiple times
					soundManager.zombieDeath();
					if (z.health == 0) {
						zombies.remove(i);
						System.out.println(zombies);
					}
					
					else if (zombies.isEmpty()){
						Game.gameOver();
					}

					else {
						z.health -= 50;
						System.out.println("zombie " + i + " "+ z.health);
					}

				}
				
			}
		}
	}
}
