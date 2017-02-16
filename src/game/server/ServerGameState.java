package game.server;

import game.Player;
import game.ResourceLoader;
import game.Zombie;
import game.map.MapData;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Daniel on 16/02/2017.
 *
 * The game state of the server at any one time.
 */
public class ServerGameState {

    private ArrayList<Zombie> zombies;
    private ArrayList<Player> players;
    private MapData mapData;

    public ServerGameState(){

    }

    /**
     * Secondary constructor used for copying this class for sending.
     *
     * @param state State to create a copy of.
     */
    public ServerGameState(ServerGameState state){
        this.zombies = state.getZombies();
        this.players = state.getPlayers();
    }

    public ArrayList<Zombie> getZombies() {
        return zombies;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void startNewGame(){
        // First we want to generate the map
        mapData = new MapData("testmap.png", "tilesheet.png", "tiledata.csv");

        int zombieCount = 100;
        try{
            for (int i = 0; i < zombieCount; i++) {
                // Daniel does some random stuff here... (like speaking in the third person)
                Random rand = new Random();
                float x = (float) (0.5-rand.nextFloat())*mapData.getWidth();
                float y = (float) (0.5-rand.nextFloat())*mapData.getHeight();
                zombies.add(new Zombie(x,y, ResourceLoader.zombieImage(), ResourceLoader.zombiePlayerImage(), mapData));
                zombies.get(i).newMovingDir();
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
            System.exit(0);
        }

    }


}
