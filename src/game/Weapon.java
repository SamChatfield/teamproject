package game;

import java.util.Random;
import game.PowerUp.PuState;
import game.client.Player;
import game.map.MapData;

public class Weapon extends Entity {
	private WeaponState wState;
	public long time;
	public float x;
	public float y;

	public enum WeaponState {
		PISTOL, MAC_GUN, SHOTGUN, UZI, CONVERT, FLAME_THROWER //  GRENADE
												// Doesn't work yet
	}

	public Weapon(float x, float y, MapData mapData, WeaponState state, long time) {
		super(x, y, mapData);
		this.setwState(state);
		this.time = time;
		this.x = x;
		this.y = y;
		this.time = time;
	}

	public void getWeaponStats(Weapon weapon, Player player) {
		player.conversionMode = false;
		
		if (weapon.getwState() == WeaponState.MAC_GUN) {
			player.SHOOT_DELAY = 100000000L;
			Bullet.setBulletSpeed(0.3f);
		} if (weapon.getwState() == WeaponState.PISTOL) {
			player.SHOOT_DELAY = 500000000L;
			Bullet.setBulletSpeed( 0.15f);
			Bullet.setFadeDistance(0.4);
		} if (weapon.getwState() == WeaponState.UZI) {
			player.SHOOT_DELAY = 100000000L;
			Bullet.setBulletSpeed(0.3f);
			Bullet.setFadeDistance(3);
		} if (weapon.getwState() == WeaponState.FLAME_THROWER) {
			player.SHOOT_DELAY = 100000L;
			Bullet.setFadeDistance(1);
		} if (weapon.getwState() == WeaponState.CONVERT) {
			player.SHOOT_DELAY = 500000000L;
			Bullet.setBulletSpeed(0.15f);
			player.conversionMode = true;
		} 
		//Doesn't work yet
		if (weapon.getwState() == WeaponState.SHOTGUN) {
			player.SHOOT_DELAY = 500000000L;
			// add 3 angled bullets per shot
		} 
	}

	public static WeaponState randomW() {
		Random r = new Random();
		int chance = r.nextInt(100) + 1;
		if (chance <= 25) {
			return Weapon.WeaponState.MAC_GUN;
		}
		if (25 < chance && chance <= 50) {
			return Weapon.WeaponState.UZI;
		}
		if (50 < chance && chance <= 75) {
			return Weapon.WeaponState.CONVERT;
		}
		return Weapon.WeaponState.PISTOL;
	}

	public WeaponState getwState() {
		return wState;
	}

	public void setwState(WeaponState wState) {
		this.wState = wState;
	}

	public float getx() {
		return x;
	}

	public float gety() {
		return y;
	}

}