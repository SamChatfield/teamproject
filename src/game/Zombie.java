package game;

import game.util.Vector;

import java.awt.image.BufferedImage;

/**
 * Created by Sam on 20/01/2017.
 */
public class Zombie extends Entity {

    private State state;
    private float dx, dy; // Which direction is the zombie moving in every tick?

    public static final float DIRECTION_CHANGE_PROBABILITY = 0.01f;
    private static final float COLL_BOX_WIDTH = 25.0f;
    private static final float COLL_BOX_HEIGHT = 25.0f;
    private static final int HEALTH = 25;

    private enum State {
        WILD, PLAYER, OPPONENT;
    }

    public Zombie(float x, float y, BufferedImage image) {
        super(x, y, 1.5f, HEALTH, new CollisionBox(x, y, COLL_BOX_WIDTH, COLL_BOX_HEIGHT), image);
        this.state = State.WILD;
    }

    public float getDx() {
        return dx;
    }

    public float getDy() {
        return dy;
    }

    public void convert() {
        state = State.PLAYER;
    }

    public void move(double delta) {
        super.move(dx * getMoveSpeed() * (float) delta, dy * getMoveSpeed() * (float) delta);
    }

    public void newMovingDir() {
        Vector zdv = Vector.randomVector().normalised();

        dx = zdv.x();
        dy = zdv.y();
    }

    public void attack(Entity entity, int damageDone) {
        long now = System.nanoTime();
        if (now - lastAttackTime > 1000000000L) {
            lastAttackTime = now;
            entity.health -= damageDone;
            System.out.println("player health: " + entity.health);
        }
    }

}
