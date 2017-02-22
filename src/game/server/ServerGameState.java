package game.server;

import game.Entity;
import game.util.GameState;
import game.Zombie;
import game.client.EntityData;
import jdk.nashorn.internal.runtime.regexp.joni.constants.EncloseType;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Daniel on 16/02/2017.
 *
 * The game state of the server at any one time.
 */
public class ServerGameState extends GameState {

    private ArrayList<EntityData> zombies;
    private int threads =0;
    public ServerGameState(){
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
        this.inProgress = true;
        int zombieCount = 100;
        ArrayList<EntityData> zombieFactory = new ArrayList<>();
        try{
            threads++;
            System.out.println("Currently there are "+threads+" threads");
            for (int i = 0; i < zombieCount; i++) {
              //  System.out.println("Zombie");

                // Daniel does some random stuff here... (like speaking in the third person)
                Random rand = new Random();
                float x = (float) (0.5-rand.nextFloat())*50;
                float y = (float) (0.5-rand.nextFloat())*50;
                //System.out.println(x+" "+y);
                zombieFactory.add(new EntityData(100,x,y, EntityData.Tag.ZOMBIE, EntityData.ZombieState.WILD));
                //zombies.get(i).newMovingDir();
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
            System.exit(0);
        }
        this.zombies = zombieFactory;
        // Start up a new game instance
        GameInstance instance = new GameInstance(this);
        instance.start();

    }

    public ArrayList<EntityData> getZombies(){
        return zombies;
    }

}
