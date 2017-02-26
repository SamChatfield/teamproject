package game.client;

import game.Zombie;
import game.map.MapData;
import game.server.ServerGameState;
import game.util.DataPacket;
import game.util.GameState;
import game.util.SendableState;

import javax.xml.crypto.Data;
import java.util.ArrayList;

/**
 * Created by Daniel on 16/02/2017.
 *
 * The game state of the client at any one time.
 */
public class ClientGameState extends GameState {

    private MapData mapData; // we can keep this here, because we won't be sending it back to the server.
    private ArrayList<DataPacket> zombies;
    private Player player;

    public ClientGameState(){
        this.mapImage = null;
        this.isReady = false;
    }

    public void updateClientState(SendableState updatedState){
        this.zombies = updatedState.getZombies();
        setPlayers(players);
        setInProgress(true);
        updateTime(updatedState.getTimeRemaining());
        System.out.println("Updated client side time "+getTimeRemaining());
        if(getMapImage()==null){
            setUpMapData(updatedState.getMapImage());
            isConnected = true; // we've got our first state send from the server. We are now connected and ready to set up the player objects.
        }
    }

    public void setUpMapData(String mapImage){
        this.mapImage = mapImage;
        mapData = new MapData(mapImage, "tilesheet.png", "tiledata.csv");
    }

    public MapData getMapData(){
        return mapData;
    }

    public void setPlayer(Player p){
        player = p;
        isReady = true;
        System.out.println("Set isReady to true");
    }

    public ArrayList<DataPacket> getZombies() {
        return zombies;
    }

    public Player getPlayer() {
        return player;
    }
}