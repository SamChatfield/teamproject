package game.server;

import game.util.GameState;
import game.Zombie;
import game.client.EntityData;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Daniel on 16/02/2017.
 *
 * The game state of the server at any one time.
 */
public class ServerGameState extends GameState {

    private ArrayList<Zombie> zombies;
    private ArrayList<EntityData> players;

    public ServerGameState(){
        startNewGame();
    }

    /**
     * Secondary constructor used for copying this class for sending.
     * The main game state is passed into here as a parameter, and then this object is sendable.
     *
     * @param state State to create a copy of.
     */
    public ServerGameState(ServerGameState state){
        this.zombies = state.getZombies();
        this.players = state.getPlayers();
        this.mapImage = state.getMapImage();
        this.timeRemaining = state.getTimeRemaining();
    }

    // TODO: Stop this from terminating the program
    public void startNewGame(){
        // First we want to generate the map
        mapImage = "prototypemap.png";
        int zombieCount = 100;
        try{
            for (int i = 0; i < zombieCount; i++) {
                // Daniel does some random stuff here... (like speaking in the third person)
                Random rand = new Random();
              //  float x = (float) (0.5-rand.nextFloat())*mapData.getWidth();
               // float y = (float) (0.5-rand.nextFloat())*mapData.getHeight();
                //zombies.add(new Zombie(x,y, ResourceLoader.zombieImage(), ResourceLoader.zombiePlayerImage(), mapData));

                //zombies.get(i).newMovingDir();
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
            System.exit(0);
        }

        // Lets start a new game
       // Timer timer = new Timer(180);
       // new Thread(timer).start();
    }

    public void updateTime(int time){
        timeRemaining = time;
    }


}
