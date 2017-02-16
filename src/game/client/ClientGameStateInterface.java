package game.client;

import game.Player;
import game.Zombie;
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
        ArrayList<Player> players = updatedState.getPlayers();

        state.setZombies(zom);
        state.setPlayers(players);
    }

    public Player getPlayer(String username){
        ArrayList<Player> plrs = state.getPlayers();
        for(Player p:plrs){
            if(p.getUsername().equals(username)){
                return p;
            }
        }
        return null; // player not found
    }
}
