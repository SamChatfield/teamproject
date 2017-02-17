package game.server;

import game.ResourceLoader;
import game.Zombie;
import game.client.EntityData;
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
    private ArrayList<EntityData> players;
    private MapData mapData;
    private int timeRemaining;

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
        this.mapData = state.getMapData();
        this.timeRemaining = state.getTimeRemaining();

    }

    public MapData getMapData() {
        return mapData;
    }

    public int getTimeRemaining() {
        return timeRemaining;
    }

    public ArrayList<Zombie> getZombies() {
        return zombies;
    }

    public ArrayList<EntityData> getPlayers() {
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

    public void updateTime(int time){
        timeRemaining = time;
    }


}
