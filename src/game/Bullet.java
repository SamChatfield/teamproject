package game;

import game.client.Player;
import game.map.MapData;
import game.util.DataPacket;
import game.util.Vector;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 * Created by Sam on 31/01/2017.
 */
public class Bullet extends Entity {

    public static final float BULLET_SPEED = 0.15f;

//    private float x, y; // the x and y coord of the middle of the bullet
    private float dx, dy; // the change in x and y of the bullet each update before delta
//    private float width, height;
    private double facingAngle;
//    private CollisionBox collisionBox;
//    private Rectangle2D.Float shape;
    public boolean active;
    private Player player;
    private double distance;
    private static final double fadeDistance = 5;
    private static final BufferedImage image = ResourceLoader.bulletImage();
    
    public Bullet(Player player, float aimX, float aimY, MapData mapData) {
        super(player.x(), player.y(), BULLET_SPEED, 0, mapData, DataPacket.Tag.BULLET);
//        x = player.x();
//        y = player.y();

        Vector normalDir = new Vector(aimX, aimY).normalised();
        dx = normalDir.x();
        dy = normalDir.y();

        facingAngle = player.getFacingAngle(); // TODO check the efficiency of this
//        collisionBox = new CollisionBox(this, width, height);
//        shape = new Rectangle2D.Float(x, y, image.getWidth(), image.getHeight());
        active = true;
        this.player = player;
    }

    public void move(double delta) {
    	float deltX = (float) (dx * BULLET_SPEED * delta);
    	float deltY = (float) (dy * BULLET_SPEED * delta);
        setX( x()+ dx * BULLET_SPEED * (float) delta);
        setY( y()+ dy * BULLET_SPEED * (float) delta);
        distance = distance + Math.sqrt((deltX * deltX) + (deltY * deltY));
        
        if(distance > fadeDistance) {
        	this.active = false;
        }
    }


    public void damage(Entity entity, int damageDone, boolean conversionMode) {
    	Zombie zom = (Zombie) entity;
        if (active) {
        	if(conversionMode) {
        		zom.convert();
        		System.out.println("Successfully converted zombie!");
        	}
        	else {
        		// TODO: Add in so converted zombies won't damage player
                entity.setHealth(entity.getHealth() - damageDone);
                active = false;
        	}
        }
    }

    public CollisionBox getCollisionBox() {
        return collisionBox;
    }

    public float getX() {
        return x();
    }

    public float getY() {
        return y();
    }

    public static BufferedImage getImage() {
        return image;
    }

}
