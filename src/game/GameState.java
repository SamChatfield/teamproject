package game;

import game.client.EntityData;
import game.map.MapData;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Daniel on 17/02/2017.
 */
public class GameState implements Serializable {

    //protected ArrayList<Zombie> zombies;
    protected ArrayList<EntityData> players; // we only need to store a lightweight version of the player.
    protected String mapImage;
    protected int timeRemaining;
    protected boolean inProgress; // is the game in progress?



    public String getMapImage() {
        return mapImage;
    }

    public int getTimeRemaining() {
        return timeRemaining;
    }

    public ArrayList<Zombie> getZombies() {
        return null;
    }

    public ArrayList<EntityData> getPlayers() {
        return players;
    }

    public void setZombies(ArrayList<Zombie> zombies) {
    }

    public void setPlayers(ArrayList<EntityData> players) {
        this.players = players;
    }

    public void clearGameState(){
        this.players = null;
    }

    public boolean inProgress(){
        return inProgress;
    }
}
