package game.server;

import game.Player;
import game.Zombie;

import java.util.ArrayList;

/**
 * Created by Daniel on 16/02/2017.
 *
 * The game state of the server at any one time.
 */
public class ServerGameState {

    private ArrayList<Zombie> zombies;
    private ArrayList<Player> players;

    public ServerGameState(){

    }

    /**
     * Secondary constructor used for copying this class for sending.
     *
     * @param state State to create a copy of.
     */
    public ServerGameState(ServerGameState state){
        this.zombies = state.getZombies();
        this.players = state.getPlayers();
    }

    public ArrayList<Zombie> getZombies() {
        return zombies;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }


}
