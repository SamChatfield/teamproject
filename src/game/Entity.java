package game;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 * Created by Sam on 20/01/2017.
 */
public class Entity {

    private double facingAngle;
    private float x, y; // x and y position of the centre of this entity in the game coord system
    private float moveSpeed; // how fast can this entity move through the world (normal speed is 1.0f)
    private BufferedImage image;
    private CollisionBox collisionBox;
    private boolean showCollBox;
    protected int health;
    protected long lastAttackTime;

    public Entity(float x, float y, float moveSpeed, int health, CollisionBox collisionBox, BufferedImage image) {
        this.x = x;
        this.y = y;
        this.moveSpeed = moveSpeed;
        this.collisionBox = collisionBox;
        this.image = image;
        showCollBox = false;
        this.health = health;
        lastAttackTime = 0L;
        facingAngle = 0.0d;
    }

    public float x() {
        return x;
    }

    public float y() {
        return y;
    }

    public float getMoveSpeed() {
        return moveSpeed;
    }

    public BufferedImage getImage() {
        return image;
    }

    // TODO Stop things from moving out of the map
    public void move(float dx, float dy) {
        x += dx;
        y += dy;
        collisionBox.move(dx, dy);
//        this.facingAngle = facingAngle;
    }

    public void face(int mx, int my) {
        facingAngle = -Math.atan2(mx - x, my - y);
    }

    public void setMoveSpeed(float moveSpeed) {
        this.moveSpeed = moveSpeed;
    }

    public void setShowCollBox(boolean showCollBox) {
        this.showCollBox = showCollBox;
    }

    public CollisionBox getCollisionBox() {
        return collisionBox;
    }

    public double getFacingAngle() {
        return facingAngle;
    }

    public void draw(Graphics2D g2d, Map map) {
        int screenX;
        int screenY;

        // Width and height of the entity sprite
        int w = image.getWidth();
        int h = image.getHeight();

        int drawX = Math.round(x - (image.getWidth() / 2.0f));
        int drawY = Math.round(y - (image.getHeight() / 2.0f));

        if (showCollBox) {
            g2d.setColor(Color.RED);
            g2d.draw(collisionBox.getRect());
            g2d.setColor(Color.BLACK);
        }

        AffineTransform at = g2d.getTransform();
//        at.translate(-drawX, -drawY);
//        g2d.rotate(facingAngle);
        g2d.rotate(facingAngle, x, y);
//        at.translate(drawX, drawY);
//        g2d.drawImage(image, drawX, drawY, null);
//        g2d.drawImage(image, at, null);
        g2d.drawImage(image, drawX, drawY, null);
        g2d.setTransform(at);
//        g2d.drawImage(image, (int) x, (int) y, null);
    }

}
