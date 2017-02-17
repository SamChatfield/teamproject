package game;

import game.map.MapData;

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
    protected MapData mapData;

//    public Entity(float x, float y, float moveSpeed, int health, CollisionBox collisionBox, BufferedImage image) {
    public Entity(float x, float y, float moveSpeed, int health, BufferedImage image, MapData mapData) {
        this.x = x;
        this.y = y;
        this.moveSpeed = moveSpeed;
        this.image = image;
        showCollBox = false;
        this.health = health;
        lastAttackTime = 0L;
        facingAngle = 0.0d;
        this.mapData = mapData;
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

    public void move(float dx, float dy) {
        float nx = x + dx;
        float ny = y + dy;

        if (mapData.isEntityMoveValid(nx, ny, this)) {
            x = nx;
            y = ny;
        } else if (mapData.isEntityMoveValid(nx, y, this)) {
            x = nx;
        } else if (mapData.isEntityMoveValid(x, ny, this)) {
            y = ny;
        }
    }

    public void face(float fx, float fy) {
        facingAngle = Math.atan2(fx, fy) - Math.PI / 2;
//        System.out.println("fx: " + fx + " fy: " + fy + " = " + facingAngle);
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

    public int getHealth(){
        return health;
    }

}
