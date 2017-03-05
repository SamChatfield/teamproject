package game.server;

import game.Bullet;
import game.Collision;
import game.Zombie;
import game.client.EntityData;
import game.client.Player;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Random;

/**
 * A class that runs whilst the game is running. It primarily updates zombie positions.
 *
 * Created by Daniel on 19/02/2017.
 */
public class GameInstance extends Thread {

    private static final long OPTIMAL_TIME_DIFF = 1000000000L / 60;

    private ServerGameState state;
    private boolean running;

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

    private void update(double delta) {
        ArrayList<Zombie> zombies = state.getZombies();
        Player player1 = state.getPlayer1();
        Player player2 = state.getPlayer2();

        ArrayList<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);

        // Move the zombies around randomly
        Random rand = new Random();

        for (Zombie zombie : zombies) {
            // Change the zombie's direction with given probability
                for (Player player : players) {
                    if (Math.hypot(zombie.getX() - player.getX(), zombie.getY() - player.getY()) <= Zombie.AGGRO_RANGE) {
                        zombie.followDirection(player);
                    } else {
                        if (rand.nextFloat() < Zombie.DIRECTION_CHANGE_PROBABILITY) {
                            zombie.newMovingDir();
                        }
                    }
                    Collision.checkCollision(zombie, player);

                }
            zombie.move(delta);
        }

        // Move all of the bullets
        try{
            Iterator<Bullet> bullets = state.getBullets().iterator();
            while (bullets.hasNext()) {
                Bullet b = bullets.next();
                if ((!state.getMapData().isEntityMoveValid(b.getX(), b.getY(), b)) || !b.active) {
                   // state.getBullets().remove(b);
                    continue;
                }
                Player owner = state.getPlayer(b.getUsername());
                Collision.checkBulletCollision(b, state.getBullets(), zombies, owner);
                b.move(delta);
            }
        }catch(ConcurrentModificationException e){
            System.out.println("Error, this shouldn't happen");
        }


    }

}
