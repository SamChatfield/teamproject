package game.util;

import game.Zombie;
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

    protected boolean isReady;
    protected boolean inProgress; // is the game in progress?
    protected boolean isConnected;

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

    /**
     * Returns whether or not the interface has been initialised with values from the server
     * @return true if the server has sent down the map data.
     */
    public boolean isReady(){
        return isReady;
    }

    public boolean isConnected(){
        return isConnected;
    }

    public void setInProgress(boolean bool){
        inProgress = bool;
    }
}
