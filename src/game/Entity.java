package game;

import java.awt.image.BufferedImage;

/**
 * Created by Sam on 20/01/2017.
 */
public class Entity {

    protected double facingAngle;
    protected float x, y; // x and y position of the centre of this entity in the game coord system
    protected float moveSpeed; // how fast can this entity move through the world (normal speed is 1.0f)
    protected BufferedImage image;
    protected CollisionBox collisionBox;
    protected boolean showCollBox;
    protected int health;
    protected long lastAttackTime;
    protected Map map;

//    public Entity(float x, float y, float moveSpeed, int health, CollisionBox collisionBox, BufferedImage image) {
    public Entity(float x, float y, float moveSpeed, int health, BufferedImage image, Map map) {
        this.x = x;
        this.y = y;
        this.moveSpeed = moveSpeed;
//        this.collisionBox = collisionBox;
//        collisionBox = new CollisionBox(this, image.getWidth(), image.getHeight());
        this.image = image;
        showCollBox = false;
        this.health = health;
        lastAttackTime = 0L;
        facingAngle = 0.0d;
        this.map = map;
        collisionBox = new CollisionBox(this);
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

    // TODO Stop things from moving out of the map
    public void move(float dx, float dy) {
        float nx = x + dx;
        float ny = y + dy;

        if (map.isInMap(nx, ny)) {
            x += dx;
            y += dy;
        }
//        collisionBox.move(dx, dy);
//        this.facingAngle = facingAngle;
    }

    public void face(int mx, int my) {
        facingAngle = -Math.atan2(mx - 320, my - 320);
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

}
