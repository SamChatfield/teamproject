package game;

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
    private static final float MOVE_SPEED = 0.05f;

    private BufferedImage playerImage;
    
    private enum State {
        WILD, PLAYER, OPPONENT;
    }

    public Zombie(float x, float y, BufferedImage image, BufferedImage imagePlayer, Map map) {
//        super(x, y, 1.5f, HEALTH, new CollisionBox(x, y, COLL_BOX_WIDTH, COLL_BOX_HEIGHT), image);
        super(x, y, MOVE_SPEED, HEALTH, image, map);
    	this.playerImage = imagePlayer;
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

    public void newMovingDir() {
        Vector zdv = Vector.randomVector().normalised();

        dx = zdv.x();
        dy = zdv.y();
    }

    public void attack(Entity entity, int damageDone) {
        long now = System.nanoTime();
        
        if(this.state == State.PLAYER) {
        	System.out.println("DEBUG: Player's zombie");
        }
        else {
            if (now - lastAttackTime > 1000000000L) {
                lastAttackTime = now;
                entity.health -= damageDone;
                System.out.println("player health: " + entity.health);
            }
        }
    }

    public void draw(Graphics2D g2d, Map map, Player player) {
        // Width and height of the entity sprite
        int w = image.getWidth();
        int h = image.getHeight();

//        float px = player.x(); // player x pos
//        float py = player.y(); // player y pos
//        float pvr = Game.VIEW_SIZE / 2.0f; // player view radius - 5.0
//        int swr = Game.GAME_DIMENSION.width / 2; // screen width radius
//        int shr = Game.GAME_DIMENSION.height / 2; // screen height radius

//        int drawX = swr + Math.round((x - px) / pvr * swr) - (w / 2); // 320 + ((2 - 6) / 5 * 320)
//        int drawY = shr + Math.round((py - y) / pvr * shr) - (h / 2); // 320 + ((6 - 2) / 5 * 320) y is inverted because our coord system is traditional whereas awt origin is top-left
        Point drawPoint = player.relativeDrawPoint(x, y, w, h);
        int drawX = drawPoint.x;
        int drawY = drawPoint.y;

        if (showCollBox) {
            g2d.setColor(Color.BLUE);
            g2d.draw(collisionBox.getDrawRect(player));
            g2d.setColor(Color.BLACK);
        }
        
        AffineTransform at = g2d.getTransform();
        g2d.rotate(facingAngle, x, y);
        
        if(state == State.PLAYER) {
        	g2d.drawImage(playerImage, drawX, drawY, null);
        }
        else if(state == State.OPPONENT) {
        	// Change this later
        	g2d.drawImage(image, drawX, drawY, null);
        }
        else {
        	g2d.drawImage(image, drawX, drawY, null);
        }
        g2d.setTransform(at);
    }

}
