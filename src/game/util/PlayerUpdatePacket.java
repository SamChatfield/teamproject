package game.util;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Packet sent via the network to update the player
 */
public class PlayerUpdatePacket implements Serializable {

	private DataPacket data;
	private double delta;
	private float fX,fY;
	private ArrayList<String> keyPresses;

	/**
	 * Get delta value
	 * @return Delta
	 */
	public double getDelta() {
		return delta;
	}

	/**
	 * Get change in X value
	 * @return Change in X
	 */
	public float getfX() {
		return fX;
	}

	/**
	 * Get change in Y value
	 * @return Change in Y
	 */
	public float getfY() {
		return fY;
	}

	/**
	 * Create a new PlayerUpdatePacket
	 * @param data DataPacket
	 * @param keyPresses ArrayList of keypresses as strings
	 * @param delta Delta value
	 * @param x X value
	 * @param y Y value
	 */
	public PlayerUpdatePacket(DataPacket data, ArrayList<String> keyPresses, double delta, float x, float y) {
		this.data = data;
		this.delta = delta;
		this.fX = x;
		this.fY = y;

		this.keyPresses = keyPresses;
	}

	/**
	 * Get keypresses
	 * @return ArrayList of strings which correspond to key presses
	 */
	public ArrayList<String> getKeyPresses() {
		return keyPresses;
	}

	/**
	 * Get DataPacket
	 * @return DataPacket
	 */
	public DataPacket getData(){
		return data;
	}
}