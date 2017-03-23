package game;

import game.client.Player;
import game.map.MapData;

import java.util.Random;

public class Weapon extends Entity {
	private WeaponState wState;
	public long time;
	public float x;
	public float y;

	public enum WeaponState {
		PISTOL, MAC_GUN, SHOTGUN, UZI, CONVERT, FLAME_THROWER // GRENADE
		// Doesn't work yet
	}

	public WeaponState getwState() {
		return wState;
	}

	public void setwState(WeaponState wState) {
		this.wState = wState;
	}

	public Weapon(float x, float y, MapData mapData, WeaponState state, long time) {
		super(x, y, mapData);
		this.wState = state;
		this.time = time;
		this.x = x;
		this.y = y;
		this.time = time;
	}

	public static void getWeaponStats(WeaponState w, Player player) {
		player.conversionMode = false;

		if (w != null) {

			if (w == WeaponState.MAC_GUN) {
				player.SHOOT_DELAY = 100000000L;
				Bullet.setBulletSpeed(0.3f);
				Bullet.setFadeDistance(5);
			} if (w == WeaponState.PISTOL) {
				player.SHOOT_DELAY = 500000000L;
				Bullet.setBulletSpeed( 0.15f);
				Bullet.setFadeDistance(5);
			} if (w == WeaponState.UZI) {
				player.SHOOT_DELAY = 100000000L;
				Bullet.setBulletSpeed(0.3f);
				Bullet.setFadeDistance(2);
			} if (w == WeaponState.FLAME_THROWER) {
				player.SHOOT_DELAY = 100000L;
				Bullet.setBulletSpeed(0.3f);
				Bullet.setFadeDistance(1);
			} if (w == WeaponState.CONVERT) {
				player.SHOOT_DELAY = 500000000L;
				Bullet.setBulletSpeed( 0.15f);
				Bullet.setFadeDistance(5);
				player.conversionMode = true;
			} 
			//Doesn't work yet
			if (w == WeaponState.SHOTGUN) {
			//	player.SHOOT_DELAY = 500000000L;
				// add 3 angled bullets per shot
			} 
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
		return WeaponState.SHOTGUN;
	}

	public static int getIndex(WeaponState w) {
		switch (w) {
		case PISTOL:
			return 0;
		case UZI:
			return 1;
		case SHOTGUN:
			return 2;
		case MAC_GUN:
			return 3;
		case CONVERT:
			return 4;
		default:
			break;
		}
		return 0;

	}

	public void addToInventory(WeaponState w, Player p) {
		if (p.getInventory()[0] == null) {
			p.getInventory()[0] = WeaponState.PISTOL;
		}

		int index = Weapon.getIndex(w);

		if (p.getInventory()[index] == null) {
			p.getInventory()[index] = w;
		}

		System.out.println(p.getInventory()[0]);
		System.out.println(p.getInventory()[1]);
		System.out.println(p.getInventory()[2]);
		System.out.println(p.getInventory()[3]);
		System.out.println(p.getInventory()[4]);

	}

	public float getx() {
		return x;
	}

	public float gety() {
		return y;
	}

}