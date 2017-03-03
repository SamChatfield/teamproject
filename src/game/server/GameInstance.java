package game.server;

import game.Zombie;
import game.client.EntityData;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Daniel on 19/02/2017.
 */
public class GameInstance extends Thread {

    private ServerGameState state;

    public GameInstance(ServerGameState state){
        this.state = state;
        Timer timer = new Timer(180,state);
        new Thread(timer).start();

        state.updateTime(timer.getTimeRemaining());
    }

    public void run() {
        while(true) {
            ArrayList<Zombie> zombies = state.getZombies();
            ArrayList<EntityData> players = state.getPlayers();
            // Move the zombies around randomly
            Random rand = new Random();

            try {
                Thread.sleep(100);
            } catch (Exception e) {

            }
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
                zombie.move(60);
                //System.out.println("AFTER: "+zombie.getX());



            }

        }

    }

}
