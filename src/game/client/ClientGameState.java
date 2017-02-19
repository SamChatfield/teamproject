package game.client;

import game.util.GameState;
import game.Zombie;
import game.map.MapData;

import java.util.ArrayList;

/**
 * Created by Daniel on 16/02/2017.
 *
 * The game state of the client at any one time.
 */
public class ClientGameState extends GameState {

    private ArrayList<Zombie> zombies;
    private ArrayList<EntityData> players; // we only need to store a lightweight version of the player.
    private MapData mapData; // we can keep this here, because we won't be sending it back to the server.
    private Player player;

    public ClientGameState(){
        this.mapImage = null;
        this.isReady = false;
    }
    /**
     * Secondary constructor used for copying this class for sending.
     * @param state State to create a copy of.
     */
    public ClientGameState(ClientGameState state){
        this.zombies = state.getZombies();
        this.players = state.getPlayers();
        this.mapImage = state.getMapImage();
        this.timeRemaining = state.getTimeRemaining();
    }

    public void setUpMapData(String mapImage){
        this.mapImage = mapImage;
        mapData = new MapData(mapImage, "tilesheet.png", "tiledata.csv");
        isConnected = true; // we've got our first state send from the server. We are now connected and ready to set up the player objects.
    }

    public MapData getMapData(){
        return mapData;
    }

    public void setPlayer(Player p){
        player = p;
        isReady = true;
        System.out.println("Set isReady to true");
    }

    public Player getPlayer() {
        return player;
    }

}