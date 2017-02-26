package game;

import game.map.MapData;
import game.util.DataPacket;

import javax.xml.crypto.Data;
import java.io.Serializable;

/**
 * Created by Sam on 20/01/2017.
 */
public class Entity implements Serializable {

    protected float moveSpeed; // how fast can this entity move through the world (normal speed is 1.0f)
    protected CollisionBox collisionBox;
    protected boolean showCollBox;
    protected long lastAttackTime;
    protected MapData mapData;
    protected int imageWidth, imageHeight;

    // A lot of data is then wrapped up in here:
    private DataPacket data;


    //    public Entity(float x, float y, float moveSpeed, int health, CollisionBox collisionBox, BufferedImage image) {
    public Entity(float x, float y, float moveSpeed, int health, MapData mapData, DataPacket.Tag tag) {
        this.data = new DataPacket(health,x,y,0.0d,tag);

        this.moveSpeed = moveSpeed;
        showCollBox = false;
        lastAttackTime = 0L;
        this.mapData = mapData;
        collisionBox = new CollisionBox(this);
    }
    
    public float x() {
        return data.getX();
    }

    public float y() {
        return data.getY();
    }

    public void setX(float x){
        data.setX(x);
    }

    public void setY(float y){
        data.setY(y);
    }
    public float getMoveSpeed() {
        return moveSpeed;
    }

    public void move(float dx, float dy) {
        float nx = y() + dx;
        float ny = x() + dy;

        if (mapData.isEntityMoveValid(nx, ny, this)) {
            setX(nx);
            setY(ny);
        } else if (mapData.isEntityMoveValid(nx, y(), this)) {
            setX(nx);
        } else if (mapData.isEntityMoveValid(x(), ny, this)) {
            setY(ny);
        }
    }

    public void face(float fx, float fy) {
        data.setFacingDirection(Math.atan2(fx, fy) - Math.PI / 2);
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
        return data.getFacingDirection();
    }

    public int getHealth(){
        return data.getHealth();
    }

    public void setHealth(int newHealth){
        data.setHealth(newHealth);
    }

    public DataPacket getData(){
        return data;
    }

}
