package game;

import game.map.MapData;
import game.util.DataPacket;

import java.io.Serializable;

/**
 * Created by Sam on 20/01/2017.
 */
public class Entity implements Serializable{

    protected CollisionBox collisionBox;
//    protected boolean showCollBox;
    protected transient MapData mapData;

    protected int imageWidth, imageHeight;

    protected DataPacket data;

    public Entity(float x, float y, float moveSpeed, int health, MapData mapData, DataPacket.Type t) {
        this.data = new DataPacket(x,y,moveSpeed,health, 0L,t);
//        showCollBox = false;
        this.mapData = mapData;
        collisionBox = new CollisionBox(this);
    }

    public DataPacket.Type getType(){
        return getType();
    }


    public long getLastAttackTime(){
        return data.getLastAttackTime();
    }

    public void setLastAttackTime(long newTime){
        data.setLastAttackTime(newTime);
    }

    public void setState(DataPacket.State state){
        data.setState(state);
    }

    public DataPacket.State getState(){
        return data.getState();
    }

    public void move(float dx, float dy) {
        float nx = data.getX() + dx;
        float ny = data.getY() + dy;

        if (mapData.isEntityMoveValid(nx, ny, this)) {
            data.setX(nx);
            data.setY(ny);
        } else if (mapData.isEntityMoveValid(nx, data.getY(), this)) {
            data.setX(nx);
        } else if (mapData.isEntityMoveValid(data.getX(), ny, this)) {
            data.setY(ny);
        }
    }

    public void face(float fx, float fy) {
        data.setFacingAngle(Math.atan2(fx, fy) - Math.PI / 2);
    }

    public void setMoveSpeed(float moveSpeed) {
        data.setMoveSpeed(moveSpeed);
    }

    public float getX(){
        return data.getX();
    }

    public float getY(){
        return data.getY();
    }

    public void updateData(DataPacket data2){
        this.data = data2;
    }

    public void updateLocalPlayerData(DataPacket data2){
        setLastAttackTime(data2.getLastAttackTime());
        setHealth(data2.getHealth());
        setNumConvertedZombies(data2.getNumConvertedZombies());
    }

    public void setNumConvertedZombies(int num){
        data.setNumConvertedZombies(num);
    }

    public int getNumConvertedZombies(){
        return data.getNumConvertedZombies();
    }

//    public void setShowCollBox(boolean showCollBox) {
//        this.showCollBox = showCollBox;
//    }

    public CollisionBox getCollisionBox() {
        return collisionBox;
    }

    public double getFacingAngle() {
        return data.getFacingAngle();
    }

    public int getHealth(){
        return data.getHealth();
    }

    public DataPacket getData() {return data;
    }

    public void setUsername(String username){
        data.setUsername(username);
    }

    public String getUsername(){
        return data.getUsername();
    }

    public float getMoveSpeed(){
        return data.getMoveSpeed();
    }

    public void setHealth(int newHealth){
        data.setHealth(newHealth);
    }


}
