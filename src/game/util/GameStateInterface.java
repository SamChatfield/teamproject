package game.util;

import game.Zombie;
import game.map.MapData;

import java.util.ArrayList;

/**
 * Created by Daniel on 18/02/2017.
 */
public abstract class GameStateInterface {

    protected GameState state;

    public GameStateInterface(){

    }

    // Some methods all classes should have
    public ArrayList<Zombie> getZombies(){
        return state.getZombies();
    }

    public int getTimeRemaining(){
        return state.getTimeRemaining();
    }

    public boolean inProgress() {
        return state.inProgress();
    }


    // Some methods all interfaces should implement
    abstract MapData getMapData();

}
