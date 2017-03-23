package game.server;

import game.*;
import game.client.Player;
import game.util.EndState;

import java.util.ArrayList;
import java.util.Random;

/**
 * A class that runs whilst the game is running. It primarily updates zombie
 * positions.
 */
public class GameInstance extends Thread {

	private static final long OPTIMAL_TIME_DIFF = 1000000000L / 60;

	private ServerGameState state;
	private boolean running;

	/**
	 * Constructor to create a new game instance.
	 * @param state The state of the server
	 */
	public GameInstance(ServerGameState state) {
		this.state = state;
		Timer timer = new Timer(180, state);
		new Thread(timer).start();

		state.updateTime(timer.getTimeRemaining());
		running = true;
	}

	// Main thread method
	public void run() {

		// Loop time
		long lastLoopTime = System.nanoTime();

		while (running) {
			long now = System.nanoTime();
			long updateLength = now - lastLoopTime;
			lastLoopTime = now;
			double delta = updateLength / ((double) 1000000000L / 60);

			// Update the game
			update(delta);

			// Check if the game has ended
			EndState end = checkForEnd();
			if (end.hasFinished()) {
				System.out.println("Game ended");
				state.setEndState(end); // Give the game state the end state.
				state.setHasFinished(true); // Will cause the timer thread to
											// close too
				running = false;
			}
			now = System.nanoTime();
			// System.out.println("time diff: " + (now - lastLoopTime));
			if (now - lastLoopTime < OPTIMAL_TIME_DIFF) {
				try {
					Thread.sleep((lastLoopTime - now + OPTIMAL_TIME_DIFF) / 1000000);
				} catch (InterruptedException e) {
					e.printStackTrace();
					System.out.println("Client loop interrupted exception: " + e.getMessage());
				}
			}
		}
	}

	/**
	 * Update the game instance
	 * 
	 * @param delta
	 *            Interpolation
	 */
	private void update(double delta) {
		// System.out.println("delta: " + delta);
		ArrayList<Zombie> zombies = state.getZombies();
		// ArrayList<PowerUp> powerups = state.getPowerups();

		// Update the player states
		Player player1 = state.getPlayer1();
		Player player2 = state.getPlayer2();

		// Add the players
		ArrayList<Player> players = new ArrayList<>();
		players.add(player1);
		players.add(player2);

		// Move the zombies around randomly
		Random rand = new Random();

		ArrayList<Zombie> newZombies = new ArrayList<>();

		for (Zombie z : state.getZombies()) {
			if (z.getHealth() == 0) {
				z.setAlive(false);
			}
            newZombies.add(z);
        }

		for (Zombie z : newZombies) {
		    if(z.isAlive()) {
                for (Player player : players) {
                    if (Math.hypot(z.getX() - player.getX(), z.getY() - player.getY()) <= Zombie.AGGRO_RANGE) {
                        z.followDirection(player);
                    } else {
                        if (rand.nextFloat() < Zombie.DIRECTION_CHANGE_PROBABILITY) {
                            z.newMovingDir();
                        }
                    }
                    // Check if player has collided with a zombie
                    Collision.checkCollision(z, player);
                }
                // Apply zombie movements on map
                z.move(delta);
            }
		}

		state.setZombies(newZombies);

		// RANDOMNESS
		Random r = new Random();
		int chancePU = r.nextInt(100) + 1;
		
		int xP = r.nextInt(40) - 20;
		int yP = r.nextInt(40) - 20;
		
		int xW = r.nextInt(40) - 20;
		int yW = r.nextInt(40) - 20;


		// WEAPONS
		ArrayList<Weapon> newWeapon = new ArrayList<>();
		long now = System.nanoTime();

		for (Weapon w : state.getWeapons()) {
			if (now - w.time <= 10000000000l && !Collision.checkWeaponCollision(w, player1)
					&& !Collision.checkWeaponCollision(w, player2)) {
				newWeapon.add(w);
			}
		}

		if (chancePU == 1) {
			//newWeapon.add(new Weapon(13, -3, state.getMapData(), Weapon.WeaponState.MAC_GUN, System.nanoTime()));
			//newWeapon.add(new Weapon(15, -3, state.getMapData(), Weapon.WeaponState.UZI, System.nanoTime()));
			newWeapon.add(new Weapon(xW, yW, state.getMapData(), Weapon.randomW(), System.nanoTime()));
		}

		state.setWeapons(newWeapon);

		// POWERUPS & DOWNS
		ArrayList<PowerUp> newPowerup = new ArrayList<>();
		long now1 = System.nanoTime();

		for (PowerUp p : state.getPowerups()) {
			if (now1 - p.time <= 5000000000l && !Collision.checkPowerupCollision(p, player1, player2)
					&& !Collision.checkPowerupCollision(p, player2, player1)) {
				newPowerup.add(p);
			}
		}

		if (chancePU == 2) {
			//newPowerup.add(new PowerUp(13, 3, state.getMapData(), PowerUp.randomPU(), System.nanoTime()));			
			newPowerup.add(new PowerUp(xP, yP, state.getMapData(),PowerUp.randomPU(), System.nanoTime()));
		}

		state.setPowerUp(newPowerup);

		// CHECK ACTIVE POWERUPS
		for (Player p : players) {
			long now11 = System.nanoTime();
			if (p.getIsActive()) {
				if (now11 - p.getAppearTime() >= 10000000000l) {
					PowerUp.normalSpeed(p);
				}
			}
			if (p.getIsActivePD()) {
				if (now11 - p.getAppearTimePD() >= 10000000000l) {
					PowerUp.normalSpeedPD(p);
				}
			}
		}

		// BULLETS

		ArrayList<Bullet> newBullets = new ArrayList<>();
		for (Bullet b : state.getBullets()) {
			if (state.getMapData().isEntityMoveValid(b.getX(), b.getY(), b) && b.active) {
				newBullets.add(b);
			}
		}

		for (Bullet b : newBullets) {
			Player owner = state.getPlayer(b.getUsername());
			Collision.checkBulletCollision(b, zombies, owner);
			Collision.checkPlayerCollision(b, state.getOtherPlayer(owner.getUsername()));
			b.move(delta);
		}

		state.setBullets(newBullets);

		// Player converted zombies
		int play1Converted = 0;
		int play2Converted = 0;

		for (int i = 0; i < state.getZombies().size(); i++) {
			if (state.getZombies().get(i).getUsername().equals(state.getPlayer1().getUsername())) {
				play1Converted += 1;
			} else if (state.getZombies().get(i).getUsername().equals(state.getPlayer2().getUsername())) {
				play2Converted += 1;
			}
		}
		state.getPlayer1().setNumConvertedZombies(play1Converted);
		state.getPlayer2().setNumConvertedZombies(play2Converted);
	}

	/**
	 * Method that checks of the end of the game
	 * 
	 * @return EndState of the game
	 */
	private EndState checkForEnd() {
		Player winner;

		// First of all, do any players have 0 health.
		if (state.getPlayer1().getHealth() == 0) {
			winner = state.getPlayer2();
			return new EndState(true, winner.getUsername(), state.getPlayer1(), state.getPlayer2(),
					EndState.EndReason.PLAYER_DIED);
		} else if (state.getPlayer2().getHealth() == 0) {
			winner = state.getPlayer1();
			return new EndState(true, winner.getUsername(), state.getPlayer1(), state.getPlayer2(),
					EndState.EndReason.PLAYER_DIED);
		}

		// Now we should see if the time has ended
		if (state.getTimeRemaining() <= 0) {// time has ended
			// the winner is now who has converted more zombies.
			if (state.getPlayer1().getNumConvertedZombies() > state.getPlayer2().getNumConvertedZombies()) {
				winner = state.getPlayer1();
				return new EndState(true, winner.getUsername(), state.getPlayer1(), state.getPlayer2(),
						EndState.EndReason.TIME_EXPIRED);
			} else if (state.getPlayer2().getNumConvertedZombies() > state.getPlayer1().getNumConvertedZombies()) {
				winner = state.getPlayer2();
				return new EndState(true, winner.getUsername(), state.getPlayer1(), state.getPlayer2(),
						EndState.EndReason.TIME_EXPIRED);

			} else {
				return new EndState(true, "Tie", state.getPlayer1(), state.getPlayer2(),
						EndState.EndReason.TIME_EXPIRED);
			}
		} else {
			return new EndState(false, "InProgress", null, null, null);
		}
	}
}