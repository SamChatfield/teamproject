package game;

import game.client.Player;
import game.map.MapData;
import game.util.DataPacket;
import game.util.Vector;

import java.awt.*;
import java.awt.geom.AffineTransform;
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
    private static final float MOVE_SPEED = 0.06f;
    public static final float AGGRO_RANGE = 4.0f;
    private static final BufferedImage image = ResourceLoader.zombieImage();
    private static final BufferedImage playerZombieImage = ResourceLoader.zombiePlayerImage();
    
    public enum State {
        WILD, PLAYER, OPPONENT;
    }

    public Zombie(float x, float y, MapData mapData) {
//        super(x, y, 1.5f, HEALTH, new CollisionBox(x, y, COLL_BOX_WIDTH, COLL_BOX_HEIGHT), image);
        super(x, y, MOVE_SPEED, HEALTH, mapData, DataPacket.Tag.ZOMBIE);
        this.state = State.WILD;
    }

    public float getDx() {
        return dx;
    }

    public float getDy() {
        return dy;
    }
    

    public State getState() {
    	return state;
    }

    public void convert() {
        state = State.PLAYER;
    }

    public void move(double delta) {
        super.move(dx * moveSpeed * (float) delta, dy * moveSpeed * (float) delta);
    }

    // Zombie vector changed to follow player, if wild.
    public void followDirection(Player player) {
    	if (state == State.WILD) {
        	Vector zdv = ArtInt.followPlayer(x(), y(), player);
        	Vector znv = zdv.normalised();
        	
            dx = znv.x();
            dy = znv.y();
            
            face((int) zdv.x(), (int) zdv.y());
    	}
    }
    
    public void newMovingDir() {
        Vector zdv = Vector.randomVector();
        Vector znv = zdv.normalised();

        dx = znv.x();
        dy = znv.y();
        face((int) zdv.x(), (int) zdv.y()); // -x because of the original orientation of the zombie image
    }

    public void attack(Entity entity, int damageDone) {
        long now = System.nanoTime();
        
        if(this.state == State.PLAYER) {
        	// Currently, do nothing
        }
        else {
            if (now - lastAttackTime > 1000000000L) {
                lastAttackTime = now;
                entity.setHealth(entity.getHealth() - damageDone);
            }
        }
    }
    

    public static BufferedImage getImage() {
        return image;
    }

}
