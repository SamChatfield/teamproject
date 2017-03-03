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
    private DataPacket player1;
    private DataPacket player2;

    public int getTimeRemaining() {
        return timeRemaining;
    }

    public String getMapImage() {
        return mapImage;
    }

    public ArrayList<DataPacket> getZombies() {
        return zombies;
    }

    public DataPacket getPlayer(String username){
        if(username.equals(player1.getUsername())){
            return player1;
        }else if (username.equals(player2.getUsername())){
            return player2;
        }else{
            return null;
        }
    }

    public DataPacket getPlayer1(){
        return player1;
    }

    public DataPacket getPlayer2(){
        return player2;
    }

    public SendableState(ServerGameState state){
        this.player1 = state.getPlayer1().getData();
        this.player2 = state.getPlayer2().getData();

        this.zombies = state.getSendableZombies();
        this.timeRemaining = state.getTimeRemaining();
        this.mapImage = state.getMapImage();
    }
}
