package game.util;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Packet sent via the network to update the player
 */
public class PlayerUpdatePacket implements Serializable {

    private DataPacket data;
    private double delta;
    private float fX, fY;
    private ArrayList<String> keyPresses;

    /**
     * Create a new PlayerUpdatePacket
     *
     * @param data       DataPacket
     * @param keyPresses ArrayList of keypresses as strings
     * @param delta      Delta value
     * @param x          facing vector x value
     * @param y          facing vector y value
     */
    public PlayerUpdatePacket(DataPacket data, ArrayList<String> keyPresses, double delta, float x, float y) {
        this.data = data;
        this.delta = delta;
        this.fX = x;
        this.fY = y;

        this.keyPresses = keyPresses;
    }

    /**
     * Get delta value
     *
     * @return Delta
     */
    public double getDelta() {
        return delta;
    }

    /**
     * Get x value of the facing vector
     *
     * @return facing x
     */
    public float getfX() {
        return fX;
    }

    /**
     * Get y value of the facing vector
     *
     * @return facing y
     */
    public float getfY() {
        return fY;
    }

    /**
     * Get keypresses
     *
     * @return ArrayList of strings which correspond to key presses
     */
    public ArrayList<String> getKeyPresses() {
        return keyPresses;
    }

    /**
     * Get DataPacket
     *
     * @return DataPacket
     */
    public DataPacket getData() {
        return data;
    }
}