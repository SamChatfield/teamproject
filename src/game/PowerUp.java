package game;

import game.client.Player;
import game.map.MapData;
import game.util.DataPacket;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

//yolo pls
//revert

public class PowerUp extends Entity implements Serializable {
	private PuState pState;
	public float x;
	public float y;
	public long time;

	public enum PuState {
		SPEED_UP, HEALTH, SLOW_DOWN, FREEZE, INVERSE, COZ
	}

	public PuState getpState() {
		return pState;
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

	public void getPowerupStats(PowerUp powerup, Player player, ArrayList<Zombie> zombies) {
		
			if (powerup.pState == PuState.SPEED_UP) {
				player.setMoveSpeed(0.1f);
				
				player.setIsActive(true);
				player.setAppearTime(System.nanoTime());
				player.setMoveSpeed(0.15f);
				player.setCurrentPU(PuState.SPEED_UP);
			}
			if (powerup.pState == PuState.HEALTH) {
				if (player.getHealth() >= 40) {
					player.setHealth(50);
				} else {
					player.setHealth(player.getHealth() + 10);
				}
			}		
			if(powerup.pState == PuState.COZ){
				for(Zombie z : zombies){
					if( z.getState() == DataPacket.State.PLAYER && z.getUsername() != player.getUsername()){
						z.setUsername(player.getUsername());
					}
				}
			}

		
	}

	public void getPowerdownStats(PowerUp powerup, Player opponent) {
		
			opponent.setIsActive(true);
			opponent.setAppearTime(System.nanoTime());
						
			if (powerup.pState == PuState.SLOW_DOWN) {
				opponent.setMoveSpeed(0.1f);
				opponent.setMoveSpeed(opponent.getMoveSpeed() - 0.05f);
				opponent.setCurrentPU(PuState.SLOW_DOWN);
			}
			if (powerup.pState == PuState.FREEZE) {
				opponent.setMoveSpeed(0.1f);
				opponent.setMoveSpeed(opponent.getMoveSpeed() - 0.1f);
				opponent.setCurrentPU(PuState.FREEZE);
			}
			if (powerup.pState == PuState.INVERSE) {
				opponent.setMoveSpeed(0.1f);
				opponent.setMoveSpeed(opponent.getMoveSpeed() - 0.2f);
				opponent.setCurrentPU(PuState.INVERSE);
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

		int chance = r.nextInt(6) + 1;
		if (chance == 1) {
			return PowerUp.PuState.HEALTH;
		}
		if (chance == 2) {
			return PowerUp.PuState.SLOW_DOWN;
		}
		if (chance == 3) {
			return PowerUp.PuState.FREEZE;
		}
		if (chance == 4) {
			return PowerUp.PuState.INVERSE;
		}
		if (chance == 5){
			return PowerUp.PuState.COZ;
		}

		return PowerUp.PuState.SPEED_UP;

	}

	public static void normalSpeed(Player player) {
		player.setMoveSpeed(0.1f);
		player.setIsActive(false);
		player.setAppearTime(0);
		player.setCurrentPU(null);
	}
	
/*	public static void normalSpeedPD(Player player) {
		player.setMoveSpeed(0.1f);
		player.setIsActivePD(false);
		player.setAppearTimePD(0);
	}*/

}