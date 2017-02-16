package game.client;

import game.Player;
import game.Zombie;

import java.util.ArrayList;

/**
 * Created by Daniel on 16/02/2017.
 *
 * The game state of the client at any one time.
 */
public class ClientGameState {

    private ArrayList<Zombie> zombies;
    private ArrayList<Player> players;

    public ClientGameState(){

    }
    /**
     * Secondary constructor used for copying this class for sending.
     *
     * @param state State to create a copy of.
     */
    public ClientGameState(ClientGameState state){
        this.zombies = state.getZombies();
        this.players = state.getPlayers();
    }

    public ArrayList<Zombie> getZombies() {
        return zombies;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setZombies(ArrayList<Zombie> zombies) {
        this.zombies = zombies;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public void clearGameState(){
        this.zombies = null;
        this.players = null;
    }
}
