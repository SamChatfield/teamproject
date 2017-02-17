package game.client;

import game.Player;
import game.Zombie;
import game.map.MapData;

import java.util.ArrayList;

/**
 * Created by Daniel on 16/02/2017.
 *
 * The game state of the client at any one time.
 */
public class ClientGameState {

    private ArrayList<Zombie> zombies;
    private ArrayList<EntityData> players; // we only need to store a lightweight version of the player.
    private MapData mapData;
    private int timeRemaining;


    public ClientGameState(){

    }
    /**
     * Secondary constructor used for copying this class for sending.
     *
     * @param state State to create a copy of.
     */
    public ClientGameState(ClientGameState state){
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

    public void setZombies(ArrayList<Zombie> zombies) {
        this.zombies = zombies;
    }

    public void setPlayers(ArrayList<EntityData> players) {
        this.players = players;
    }

    public void clearGameState(){
        this.zombies = null;
        this.players = null;
    }
}
