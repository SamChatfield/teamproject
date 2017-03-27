package game;

import game.client.Player;
import game.map.MapData;

import java.util.Random;

public class Weapon extends Entity {
    public long time;
    public float x;
    public float y;
    private WeaponState wState;

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

        System.out.println("getWeaponStats called");

        if (w != null) {

            player.setCurrentlyEquipped(w);
            System.out.println(player.getCurrentlyEquipped());

            if (w == WeaponState.MAC_GUN) {
                player.setShootDelay(200000000L);
                Bullet.setBulletSpeed(0.3f);
                Bullet.setFadeDistance(5);
            }
            if (w == WeaponState.PISTOL) {
                player.setShootDelay(500000000L);
                Bullet.setBulletSpeed(0.15f);
                Bullet.setFadeDistance(5);
            }
            if (w == WeaponState.UZI) {
                player.setShootDelay(10000000L);
                Bullet.setBulletSpeed(0.3f);
                Bullet.setFadeDistance(2);
            }
            if (w == WeaponState.CONVERT) {
                player.setShootDelay(500000000L);
                Bullet.setBulletSpeed(0.15f);
                Bullet.setFadeDistance(5);
                player.conversionMode = true;
            }
            // Doesn't work yet
            if (w == WeaponState.SHOTGUN) {
                player.setShootDelay(1000000000L);
                Bullet.setBulletSpeed(0.1f);
                Bullet.setFadeDistance(2);
            }
        }
    }

    public static WeaponState randomW() {
        Random r = new Random();
        int chance = r.nextInt(4) + 1;
        if (chance == 1) {
            return Weapon.WeaponState.SHOTGUN;
        }
        if (chance == 2) {
            return Weapon.WeaponState.UZI;
        }
        if (chance == 3) {
            return Weapon.WeaponState.MAC_GUN;
        }
        return Weapon.WeaponState.CONVERT;

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

    public WeaponState getwState() {
        return wState;
    }

    public void setwState(WeaponState wState) {
        this.wState = wState;
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

    public enum WeaponState {
        PISTOL, UZI, SHOTGUN, MAC_GUN, CONVERT
    }

}