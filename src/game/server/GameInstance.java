package game.server;

import game.Zombie;
import game.client.EntityData;
import game.client.Player;

import java.util.ArrayList;
import java.util.Random;

/**
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

        // Move the zombies around randomly
        Random rand = new Random();

        for (Zombie zombie : zombies) {
            // Change the zombie's direction with given probability
                /*
                for (EntityData player : players) {
                    if (Math.hypot(zombie.getX() - player.getX(), zombie.getY() - player.getY()) <= Zombie.AGGRO_RANGE) {
                        //zombie.followDirection(player);
                    } else {
                        if (rand.nextFloat() < Zombie.DIRECTION_CHANGE_PROBABILITY) {
                           // zombie.newMovingDir();
                        }
                    }
                }
                */

            if (rand.nextFloat() < Zombie.DIRECTION_CHANGE_PROBABILITY) {
                zombie.newMovingDir();
            }
            // System.out.println("BEFORE: "+zombie.getX());
            zombie.move(delta);
            //System.out.println("AFTER: "+zombie.getX());
        }
    }

}
