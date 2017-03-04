package game.util;

import java.io.Serializable;

/**
 * Created by Daniel on 28/02/2017.
 */
public class DataPacket implements Serializable{

    private float x,y;
    private int health;
    private double facingAngle;
    private float moveSpeed;
    protected long lastAttackTime;
    private String username;
    private Type type;

    public enum Type{
        ZOMBIE,PLAYER,BULLET
    }

    public Type getType(){
        return type;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public double getFacingAngle() {
        return facingAngle;
    }

    public void setFacingAngle(double facingAngle) {
        this.facingAngle = facingAngle;
    }

    public float getMoveSpeed() {
        return moveSpeed;
    }

    public void setMoveSpeed(float moveSpeed) {
        this.moveSpeed = moveSpeed;
    }

    public long getLastAttackTime() {
        return lastAttackTime;
    }

    public void setLastAttackTime(long lastAttackTime) {
        this.lastAttackTime = lastAttackTime;
    }

    public DataPacket(float x, float y, float moveSpeed, int health, long lastAttackTime, Type t) {
        this.lastAttackTime = lastAttackTime;

        this.x = x;
        this.y = y;
        this.moveSpeed = moveSpeed;
        this.health = health;
        this.type = t;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }
}
