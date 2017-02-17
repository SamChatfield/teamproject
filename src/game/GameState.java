package game;

import game.client.EntityData;
import game.map.MapData;

import java.util.ArrayList;

/**
 * Created by Daniel on 17/02/2017.
 */
public class GameState {

    protected ArrayList<Zombie> zombies;
    protected ArrayList<EntityData> players; // we only need to store a lightweight version of the player.
    protected MapData mapData;
    protected int timeRemaining;

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
