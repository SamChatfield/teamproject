package game;

import java.awt.geom.Rectangle2D;

/**
 * Created by Sam on 31/01/2017.
 */
public class CollisionBox {

    private float x, y;
    private float width, height;

    public CollisionBox(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public Rectangle2D.Float getRect() {
        return new Rectangle2D.Float(x - width / 2, y - height / 2, width, height);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void move(float dx, float dy) {
        x += dx;
        y += dy;
    }

    public boolean intersects(CollisionBox otherBox) {
        return getRect().intersects(otherBox.getRect());
    }

}
