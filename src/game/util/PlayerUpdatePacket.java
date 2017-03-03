package game.util;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Daniel on 03/03/2017.
 */
public class PlayerUpdatePacket implements Serializable {

    private DataPacket data;
    private double delta;
    private ArrayList<String> keyPresses;

    public double getDelta() {
        return delta;
    }

    public PlayerUpdatePacket(DataPacket data, ArrayList<String> keyPresses, double delta) {
        this.data = data;
        this.delta = delta;

        this.keyPresses = keyPresses;
    }

    public ArrayList<String> getKeyPresses() {
        return keyPresses;
    }

    public DataPacket getData(){
        return data;
    }
}
