package game.server;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Random;

import game.Bullet;
import game.Collision;
import game.Zombie;
import game.client.Player;
import game.util.EndState;

/**
 * A class that runs whilst the game is running. It primarily updates zombie positions.
 */
public class GameInstance extends Thread {

	private static final long OPTIMAL_TIME_DIFF = 1000000000L / 60;

	private ServerGameState state;
	private boolean running;

	/**
	 * Constructor to create a new game instance
	 * @param (ServerGameState) state - The state of the server
	 */
	public GameInstance(ServerGameState state){
		this.state = state;
		Timer timer = new Timer(180,state);
		new Thread(timer).start();


		state.updateTime(timer.getTimeRemaining());
		running = true;
	}

	public void run() {

		long lastLoopTime = System.nanoTime();

		while (running) {
			long now = System.nanoTime();
			long updateLength = now - lastLoopTime;
			lastLoopTime = now;
			double delta = updateLength / ((double) 1000000000L / 60);

			update(delta);
			EndState end = checkForEnd();
			if(end.hasFinished()){
				state.setHasFinished(true); // will cause the timer thread to close too
				Thread.currentThread().interrupt(); // close the thread
			}

			now = System.nanoTime();
			if (now - lastLoopTime < OPTIMAL_TIME_DIFF) {
				try {
					Thread.sleep((lastLoopTime - now + OPTIMAL_TIME_DIFF) / 1000000);
				} catch (InterruptedException e) {
					e.printStackTrace();
					System.out.println("Client loop interrupted exception");
				}
			}
		}
	}

	/**
	 * Update the game instance
	 * @param delta - Interpolation
	 */
	private void update(double delta) {
		ArrayList<Zombie> zombies = state.getZombies();

		// Update the player states
		Player player1 = state.getPlayer1();
		Player player2 = state.getPlayer2();

		// Add the players
		ArrayList<Player> players = new ArrayList<>();
		players.add(player1);
		players.add(player2);

		// Move the zombies around randomly
		Random rand = new Random();
		Iterator<Zombie> zombs = state.getZombies().iterator();
		while (zombs.hasNext()) {
			Zombie zombie = zombs.next();
			// Change the zombie's direction with given probability
			if(zombie.getHealth() == 0){
				zombs.remove();
			}
			for (Player player : players) {
				if (Math.hypot(zombie.getX() - player.getX(), zombie.getY() - player.getY()) <= Zombie.AGGRO_RANGE) {
					zombie.followDirection(player);
				} else {
					if (rand.nextFloat() < Zombie.DIRECTION_CHANGE_PROBABILITY) {
						zombie.newMovingDir();
					}
				}
				// Check if player has collided with a zombie
				Collision.checkCollision(zombie, player);
			}
			zombie.move(delta);
		}

		// Move all of the bullets, remove them if they've gone off of the map/hit an object
		try {
			Iterator<Bullet> bullets = state.getBullets().iterator();
			while (bullets.hasNext()) {
				Bullet b = bullets.next();
				if ((!state.getMapData().isEntityMoveValid(b.getX(), b.getY(), b)) || !b.active) {
					bullets.remove();
					continue;
				}
				Player owner = state.getPlayer(b.getUsername());
				Collision.checkBulletCollision(b, state.getBullets(), zombies, owner);
				Collision.checkPlayerCollision(b, state.getBullets(), owner, state.getOtherPlayer(owner.getUsername()));
				b.move(delta);
			}
		} catch(ConcurrentModificationException e){
			System.out.println("Error, this shouldn't happen");
		}
	}

	/**
	 * Method that checks of the end of the game
	 */
	private EndState checkForEnd(){
		Player winner;

		// First of all, do any players have 0 health.
		if(state.getPlayer1().getHealth() == 0){
			winner = state.getPlayer2();
			return new EndState(true,winner.getUsername(),state.getPlayer1(),state.getPlayer2(), EndState.EndReason.PLAYER_DIED);
		}else if(state.getPlayer2().getHealth() == 0){
			winner = state.getPlayer1();
			return new EndState(true,winner.getUsername(),state.getPlayer1(),state.getPlayer2(), EndState.EndReason.PLAYER_DIED);
		}

		// Now we should see if the time has ended
		if(state.getTimeRemaining() <= 0){// time has ended
			// the winner is now who has converted more zombies.
			if(state.getPlayer1().getNumConvertedZombies() > state.getPlayer2().getNumConvertedZombies()){
				winner = state.getPlayer1();
				return new EndState(true,winner.getUsername(),state.getPlayer1(),state.getPlayer2(), EndState.EndReason.TIME_EXPIRED);
			}else if( state.getPlayer2().getNumConvertedZombies() > state.getPlayer1().getNumConvertedZombies()){
				winner = state.getPlayer2();
				return new EndState(true,winner.getUsername(),state.getPlayer1(),state.getPlayer2(), EndState.EndReason.TIME_EXPIRED);

			}else{
				winner = null;
				return new EndState(true,"Tie",state.getPlayer1(),state.getPlayer2(), EndState.EndReason.TIME_EXPIRED);

			}

		}else{
			return new EndState(false,"InProgress",null,null,null);
		}
	}
}