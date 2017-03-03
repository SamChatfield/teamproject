package game.client;

import game.Entity;
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

    private String username;

    public ClientGameState(String username){
        this.username = username;
        this.mapImage = null;
        this.isConnected = false;
        this.zombies = new ArrayList<Zombie>();

    }

    public void updateClientState(SendableState updatedState){

        if(!isConnected){
            this.mapImage = updatedState.getMapImage();
            setUpGame();
            player2.setUsername(updatedState.getPlayer2().getUsername()); // we now know the username of the other player.
        }

        player1.updateData(updatedState.getPlayer1());
        player2.updateData(updatedState.getPlayer2());


        ArrayList<DataPacket> sentZombies = updatedState.getZombies();
        for(int i = 0; i<100; i++){
            zombies.get(i).updateData(sentZombies.get(i));
        }
        updateTime(updatedState.getTimeRemaining());
        //System.out.println("Updated client side time "+getTimeRemaining());

    }

    private void setUpGame(){
        setUpMapData(mapImage);

        // Set up two player objects that we can update later.
        this.player1 = new Player(0,0,mapData,username);
        this.player2 = new Player(0,0,mapData,null); // We'll set this later

        for(int i = 0; i<100; i++){
            zombies.add(new Zombie(10,10,mapData));
        }
        isConnected = true; // we've got our first state send from the server. We are now connected and ready to receive states.
    }


    public void setUpMapData(String mapImage){
        this.mapImage = mapImage;
        mapData = new MapData(mapImage, "tilesheet.png", "tiledata.csv");
    }


    /**
     * Finds the player that this state is local to.
     * @return The local player
     */
    public Player getPlayer() {
        if(player1.getUsername().equals(username)){
            return player1;
        }else if(player2.getUsername().equals(username)) {

            return player2;
        }
        return null;
    }
}