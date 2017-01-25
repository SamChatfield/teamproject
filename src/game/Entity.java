package game;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Sam on 20/01/2017.
 */
public class Entity {

    private float x, y; // x and y position of this entity
    private float lastX, lastY; // the
    private float velX, velY; // the x and y components of the entity's velocity
    private float moveSpeed; // how fast can this entity move through the world (normal speed is 1.0f)
    private float width, height; // width and height of this entity in the game world coord system (i.e. what space does it occupy on the map)
    private BufferedImage image;

    public Entity(float x, float y, BufferedImage image) {
        this.x = lastX = x;
        this.y = lastY = y;
        this.image = image;
        width = image.getWidth();
        height = image.getHeight();
        moveSpeed = 1.0f;
        velX = velY = 0.0f;
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

    public void move(float dx, float dy) {
        x += dx;
        y += dy;
    }

    public void setMoveSpeed(float moveSpeed) {
        this.moveSpeed = moveSpeed;
    }

    public void draw(Graphics2D g2d, float interpolation) {
        g2d.drawImage(image, (int) x, (int) y, null);
    }

    public void draw(Graphics2D g2d) {
        g2d.drawImage(image, (int) x, (int) y, null);
    }

}
