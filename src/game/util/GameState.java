package game.util;

import game.Bullet;
import game.Zombie;
import game.client.EntityData;
import game.client.Player;
import game.map.MapData;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Daniel on 17/02/2017.
 *
 * A class that ServerGameState and ClientGameState inherit from. This contains many useful methods that can
 * be called on both game states.
 */
public class GameState implements Serializable {

    protected ArrayList<Zombie> zombies;
    protected ArrayList<Bullet> bullets;
    protected Player player1;
    protected Player player2;
    protected String mapImage; // the name of the file being used to create the image.
    protected int timeRemaining;
    protected MapData mapData;
    protected boolean inProgress; // is the game in progress?
    protected boolean isConnected; // is the game connected to the server? If this is false, the server hasn't sent a state yet

    public ArrayList<Bullet> getBullets() {
        return bullets;
    }

    public void setBullets(ArrayList<Bullet> bullets) {
        this.bullets = bullets;
    }

    public MapData getMapData() {
        return mapData;
    }

    public String getMapImage() {
        return mapImage;
    }

    public int getTimeRemaining() {
        return timeRemaining;
    }

    public void updateTime(int time){
        timeRemaining = time;
    }

    public ArrayList<Zombie> getZombies() {
        return zombies;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public boolean inProgress(){
        return inProgress;
    }

    public boolean isConnected(){
        return isConnected;
    }

    public void setInProgress(boolean bool){
        inProgress = bool;
    }
}
