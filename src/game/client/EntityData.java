package game.client;

import java.io.Serializable;

/**
 * Created by Daniel on 16/02/2017.
 */
public class EntityData implements Serializable {

    private int health;
    private String username;
    private float x,y;

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public float getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public EntityData(){
    }

    public EntityData(int health, float x, float y){
        this.health = health;
        this.x = x;
        this.y = y;
    }
}
