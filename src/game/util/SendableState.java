package game.util;

import game.client.EntityData;
import game.server.ServerGameState;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Daniel on 25/02/2017.
 */
public class SendableState implements Serializable {

    private ArrayList<DataPacket> zombies;
    private ArrayList<EntityData> players;
    private String mapImage;
    private int timeRemaining;

    public int getTimeRemaining() {
        return timeRemaining;
    }

    public ArrayList<DataPacket> getZombies() {
        return zombies;
    }

    public ArrayList<EntityData> getPlayers() {
        return players;
    }

    public String getMapImage() {
        return mapImage;
    }

    public SendableState(ServerGameState state){
        this.mapImage = state.getMapImage();
        this.zombies = state.getZombieData();
        this.players = state.getPlayers();
        this.timeRemaining = state.getTimeRemaining();

    }

}
