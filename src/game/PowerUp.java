package game;

import java.io.Serializable;
import java.util.Random;

import game.client.Player;
import game.map.MapData;

public class PowerUp extends Entity implements Serializable {
	private PuState pState;
	public float x;
	public float y;
	public long time;

	
	public enum PuState {
		SPEED_UP, HEALTH, SLOW_DOWN, FREEZE, INVERSE, TEST,
	}

	public PowerUp(float x, float y, MapData mapData, long time) {
		super(x, y, mapData);
		// setUsername("None");
		//this.pState = pState;
		this.x = x;
		this.y= y;
		this.time = time;
	}

	public void getPowerupStats(PowerUp powerup, Player player) {

		if (powerup.pState == PuState.TEST) {

		}
	}
	
	
	public float getx(){
		return x;
	}
	
	public float gety(){
		return y;
	}
	
}