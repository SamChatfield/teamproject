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
        state.updateClientState(updatedState);
    }

    public EntityData getPlayer(){
        Player p = state.getPlayer();
        return new EntityData(p.getHealth(),p.getX(),p.getY(), EntityData.Tag.PLAYER,null);
    }

    public Player getPlayerObj(){
        return state.getPlayer();
    }
    public ArrayList<Zombie> getZombies(){
        return state.getZombies();
    }

    public int getTimeRemaining(){
        return state.getTimeRemaining();
    }


    public MapData getMapData() {
        return state.getMapData();
    }

    public boolean inProgress() {
        return state.inProgress();
    }


    public boolean isReady(){
        return state.isReady();
    }

    public void setPlayer(Player p){
        state.setPlayer(p);
    }

    public boolean isConnected(){
        return state.isConnected();
    }
}
