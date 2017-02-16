package game.client;

import game.networking.ClientReceiver;
import game.networking.ClientSender;

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
}
