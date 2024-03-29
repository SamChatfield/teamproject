package game;

import game.client.Player;
import game.map.MapData;
import game.util.DataPacket;
import game.util.Vector;

import java.io.Serializable;

/**
 * Class that represents bullets in the game that can harm zombies
 */
public class Bullet extends Entity implements Serializable {

    public static float BULLET_SPEED = 0.3f; // Speed of bullets
    private static double fadeDistance = 5;
    public boolean active;
    private float dx, dy; // Change in x and y of the bullet each update before delta
    private Player player;
    private double distance;


    /**
     * Create a new bullet
     *
     * @param player  Player object
     * @param aimX    X coordinate aimed in
     * @param aimY    Y coordinate aimed in
     * @param pdx     player's x velocity
     * @param pdy     player's y velocity
     * @param mapData Current mapdata used to know when bullet is over obstacles
     */
    public Bullet(Player player, float aimX, float aimY, float pdx, float pdy, MapData mapData) {
        super(player.getX(), player.getY(), BULLET_SPEED, 0, mapData, DataPacket.Type.BULLET);

        setUsername(player.getUsername());

        Vector normalDir = new Vector(aimX, aimY).normalised();
        dx = normalDir.x() * BULLET_SPEED + pdx;
        dy = normalDir.y() * BULLET_SPEED + pdy;

        data.setFacingAngle(player.getFacingAngle());

        active = true;
        this.player = player;
    }

    public static double getFadeDistance() {
        return fadeDistance;
    }

    public static void setFadeDistance(double fadeDistance) {
        Bullet.fadeDistance = fadeDistance;
    }

    public static float getBulletSpeed() {
        return BULLET_SPEED;
    }

    public static void setBulletSpeed(float bulletspeed) {
        Bullet.BULLET_SPEED = bulletspeed;
    }

    /**
     * Move zombie on the map
     *
     * @param delta Interpolation
     */
    public void move(double delta) {
        float deltX = (float) (dx * delta);
        float deltY = (float) (dy * delta);

        data.setX(getX() + deltX);
        data.setY(getY() + deltY);

        distance = distance + Math.sqrt((deltX * deltX) + (deltY * deltY));

        if (distance > getFadeDistance()) {
            this.active = false;
        }
    }

    /**
     * Apply damage to the zombie done by the player
     *
     * @param entity         The entity that damaged the zombie
     * @param damageDone     The amount of damage done
     * @param conversionMode Whether conversion mode is enabled
     */
    public void damage(Entity entity, int damageDone, boolean conversionMode) {
        Zombie zom = (Zombie) entity;
        if (active) {
            if (conversionMode) {
                zom.convert(player.getUsername()); // pass the shooting player
                // to the zombie.
                System.out.println("Successfully converted zombie!");
            } else {
                // TODO: Add in so converted zombies won't damage player
                if (entity.getHealth() <= damageDone) {
                    System.out.println("Critically injured");
                    entity.setHealth(0);
                } else {
                    entity.setHealth(entity.getHealth() - damageDone);
                }
                active = false;
            }
        }
    }

    /**
     * Apply damage to the player done by the bullet
     *
     * @param player     Player object
     * @param damageDone The amount of damage done
     */
    public void damagePlayer(Player player, int damageDone) {
        if (active) {
            if (player.getHealth() <= damageDone) {
                System.out.println("Critically injured");
                player.setHealth(0);
            } else {
                player.setHealth(player.getHealth() - damageDone);
            }
            active = false;
        }
    }

    public CollisionBox getCollisionBox() {
        return collisionBox;
    }

}