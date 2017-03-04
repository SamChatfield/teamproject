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

    private float dx, dy; // the change in x and y of the bullet each update before delta

    public boolean active;
    private Player player;
    private double distance;
    private static final double fadeDistance = 5;
    private static final BufferedImage image = ResourceLoader.bulletImage();
    
    public Bullet(Player player, float aimX, float aimY, MapData mapData) {
        super(player.getX(), player.getY(), BULLET_SPEED, 0, mapData, DataPacket.Type.BULLET);

        setUsername(player.getUsername());

        Vector normalDir = new Vector(aimX, aimY).normalised();
        dx = normalDir.x();
        dy = normalDir.y();

        data.setFacingAngle(player.getFacingAngle()); // TODO check the efficiency of this

        active = true;
        this.player = player;
    }

    public void move(double delta) {
    	float deltX = (float) (dx * BULLET_SPEED * delta);
    	float deltY = (float) (dy * BULLET_SPEED * delta);
    	float incX = (float) (dx * BULLET_SPEED * delta);
        float incY = (float) (dy * BULLET_SPEED * delta);

        data.setX(getX() + incX);
        data.setY(getY() + incY);

        distance = distance + Math.sqrt((deltX * deltX) + (deltY * deltY));
        
        if(distance > fadeDistance) {
        	this.active = false;
        }
    }

    public void draw(Graphics2D g2d) {
        int w = image.getWidth();
        int h = image.getHeight();

        Point drawPoint = player.relativeDrawPoint(getX(), getY(), w, h);
        int drawX = drawPoint.x;
        int drawY = drawPoint.y;

        AffineTransform at = g2d.getTransform();
        g2d.rotate(data.getFacingAngle(), drawX, drawY);
        g2d.drawImage(image, drawX, drawY, null);
        g2d.setTransform(at);
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


    public static BufferedImage getImage() {
        return image;
    }



}
