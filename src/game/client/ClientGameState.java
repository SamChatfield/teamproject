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

    private Player player;


    public ClientGameState(){
        this.mapImage = null;
        this.isReady = false;
        this.isConnected = false;
        this.zombies = new ArrayList<Zombie>();

    }

    public void updateClientState(SendableState updatedState){

        if(!isConnected){
            setUpMapData(updatedState.getMapImage());
            for(int i = 0; i<100; i++){
                zombies.add(new Zombie(10,10,mapData));
            }
            isConnected = true; // we've got our first state send from the server. We are now connected and ready to set up the player objects.
        }


        setPlayers(players);
        setInProgress(true);

        ArrayList<DataPacket> sentZombies = updatedState.getZombies();
        for(int i = 0; i<100; i++){
            zombies.get(i).updateData(sentZombies.get(i));
        }
        updateTime(updatedState.getTimeRemaining());
        //System.out.println("Updated client side time "+getTimeRemaining());

    }

    public EntityData getPlayerEntity(){
        return new EntityData(player.getHealth(),player.getX(),player.getY(), EntityData.Tag.PLAYER,null);
    }

    public void setUpMapData(String mapImage){
        this.mapImage = mapImage;
        System.out.println("updated the mapImage");
        mapData = new MapData(mapImage, "tilesheet.png", "tiledata.csv");
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