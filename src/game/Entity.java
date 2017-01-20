package game;

import java.awt.image.BufferedImage;

/**
 * Created by Sam on 20/01/2017.
 */
public class Entity {

    private float x, y; // x and y position of this entity
    private float moveSpeed; // how fast can this entity move through the world (normal speed is 1.0f)
    private float width, height; // width and height of this entity in the game world coord system (i.e. what space does it occupy on the map)
    private BufferedImage image;

    public Entity(float x, float y, BufferedImage image) {
        this.x = x;
        this.y = y;
        this.image = image;
        moveSpeed = 1.0f;
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

    public void setMoveSpeed(float moveSpeed) {
        this.moveSpeed = moveSpeed;
    }

}
