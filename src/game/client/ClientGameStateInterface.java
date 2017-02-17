package game.client;

import game.Zombie;
import game.map.MapData;
import game.server.ServerGameState;
import java.util.ArrayList;

/**
 * Created by Daniel on 16/02/2017.
 */
public class ClientGameStateInterface {

    private ClientGameState state;
    private ClientReceiver receiver;
    private ClientSender sender;

    public ClientGameStateInterface(ClientGameState state, ClientReceiver receiver, ClientSender sender){
        this.state = state;
        this.receiver =receiver;
        this.sender = sender;
    }

    public void update(ServerGameState updatedState){
        ArrayList<Zombie> zom = updatedState.getZombies();
        ArrayList<EntityData> players = updatedState.getPlayers();

        state.setZombies(zom);
        state.setPlayers(players);
    }

    public EntityData getPlayer(String username){
        ArrayList<EntityData> plrs = state.getPlayers();
        for(EntityData p:plrs){
            if(p.getUsername().equals(username)){
                return p;
            }
        }
        return null; // player not found
    }

    public ArrayList<Zombie> getZombies(){
        return state.getZombies();
    }

    public int getTimeRemaining(){
        return state.getTimeRemaining();
    }

    public MapData getMapData(){
        return state.getMapData();
    }
}
