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

	public PowerUp(float x, float y, MapData mapData, PuState pState, long time) {
		super(x, y, mapData);
		// setUsername("None");
		// this.pState = pState;
		this.x = x;
		this.y = y;
		this.time = time;
		this.pState = pState;
	}

	public void getPowerupStats(PowerUp powerup, Player player) {
		if (!player.getIsActive()) {
			if (powerup.pState == PuState.SPEED_UP) {
				player.setIsActive(true);
				player.setAppearTime(System.nanoTime());
				player.setMoveSpeed(player.getMoveSpeed() + 0.05f);
			}
			if (powerup.pState == PuState.HEALTH) {
				if (player.getHealth() >= 40) {
					player.setHealth(50);
				} else {
					player.setHealth(player.getHealth() + 10);
				}
			}
		}
	}

	public void getPowerdownStats(PowerUp powerup, Player opponent) {
		if (!opponent.getIsActivePD()) {
			opponent.setIsActivePD(true);
			opponent.setAppearTimePD(System.nanoTime());
						
			if (powerup.pState == PuState.SLOW_DOWN) {
				opponent.setMoveSpeed(opponent.getMoveSpeed() - 0.05f);
			}
			if (powerup.pState == PuState.FREEZE) {
				opponent.setMoveSpeed(opponent.getMoveSpeed() - 0.1f);
			}
			if (powerup.pState == PuState.INVERSE) {
				opponent.setMoveSpeed(opponent.getMoveSpeed() - 0.2f);
			}
		}

	}

	public float getx() {
		return x;
	}

	public float gety() {
		return y;
	}

	public static PuState randomPU() {
		Random r = new Random();
		int chance = r.nextInt(100) + 1;
		if (chance <= 20) {
			return PowerUp.PuState.SPEED_UP;
		}
		if (20 < chance && chance <= 40) {
			return PowerUp.PuState.SLOW_DOWN;
		}
		if (40 < chance && chance <= 60) {
			return PowerUp.PuState.FREEZE;
		}
		if (60 < chance && chance <= 80) {
			return PowerUp.PuState.INVERSE;
		}

		return PowerUp.PuState.HEALTH;
	}

	public static void normalSpeed(Player player) {
		player.setMoveSpeed(0.1f);
		player.setIsActive(false);
		player.setAppearTime(0);
	}
	
	public static void normalSpeedPD(Player player) {
		player.setMoveSpeed(0.1f);
		player.setIsActivePD(false);
		player.setAppearTimePD(0);
	}

}