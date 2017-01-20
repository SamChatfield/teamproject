package game;

import java.awt.image.BufferedImage;

/**
 * Created by Sam on 20/01/2017.
 */
public class Player {

    private float x, y, moveSpeed;
    private BufferedImage[] images;

    public Player(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getMoveSpeed() {
        return moveSpeed;
    }

    public void setMoveSpeed(float moveSpeed) {
        this.moveSpeed = moveSpeed;
    }

}
