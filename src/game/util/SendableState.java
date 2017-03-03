package game.util;

import game.client.EntityData;
import game.server.ServerGameState;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Daniel on 28/02/2017.
 */
public class SendableState implements Serializable {

    private int timeRemaining;
    private String mapImage;
    private ArrayList<DataPacket> zombies;
    private ArrayList<EntityData> players;

    public int getTimeRemaining() {
        return timeRemaining;
    }

    public String getMapImage() {
        return mapImage;
    }

    public ArrayList<DataPacket> getZombies() {
        return zombies;
    }

    public ArrayList<EntityData> getPlayers() {
        return players;
    }

    public SendableState(ServerGameState state){
        this.players = state.getPlayers();
        this.zombies = state.getSendableZombies();
        this.timeRemaining = state.getTimeRemaining();
        this.mapImage = state.getMapImage();
    }
}
