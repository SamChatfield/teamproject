package game.client;

import com.sun.xml.internal.bind.v2.TODO;
import game.Entity;
import game.ResourceLoader;
import game.server.ServerGameState;
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
    private MapData mapData; // we can keep this here, because we won't be sending it back to the server.
    private Player player;

    public ClientGameState(){
        this.mapImage = null;
        this.isReady = false;
    }

    public void updateClientState(ServerGameState updatedState){
        setZombies(updatedState.getZombies());
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

    public Player getPlayer() {
        return player;
    }

    public void setZombies(ArrayList<EntityData> zombieFrame){
        ArrayList<Zombie> done = new ArrayList<>();
        for(EntityData z:zombieFrame){
            try{
                Zombie converted = new Zombie(z.getX(),z.getY(),ResourceLoader.zombieImage(), ResourceLoader.zombiePlayerImage(),mapData);
                converted.setHealth(z.getHealth());
                done.add(converted);
            }catch(Exception e) {
                System.out.println("Failure: ResourceLoader could not load image \n" + e.getMessage());
            }
        }
        this.zombies = done;
    }

    public ArrayList<Zombie> getZombies(){
        return zombies;
    }
}