package game.util;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Daniel on 03/03/2017.
 */
public class PlayerUpdatePacket implements Serializable {

    private String username;
    private float x,y;
    private ArrayList<String> keyPresses;


    public PlayerUpdatePacket(String username, float x, float y, ArrayList<String> keyPresses) {
        this.username = username;
        this.x = x;
        this.y = y;
        this.keyPresses = keyPresses;
    }

    public String getUsername() {
        return username;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public ArrayList<String> getKeyPresses() {
        return keyPresses;
    }
}
