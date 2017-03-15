package game;

import game.client.Player;
import game.map.MapData;
import game.util.DataPacket;
import game.util.Vector;

/**
 * Created by Sam on 20/01/2017.
 */
public class Zombie extends Entity {

    private float dx, dy; // Which direction is the zombie moving in every tick?

    public static final float DIRECTION_CHANGE_PROBABILITY = 0.01f;
    private static final float COLL_BOX_WIDTH = 25.0f;
    private static final float COLL_BOX_HEIGHT = 25.0f;
    private static final int HEALTH = 25;
    private static final float MOVE_SPEED = 0.05f;
    public static final float AGGRO_RANGE = 4.0f;
//    private static final BufferedImage image = ResourceLoader.zombieImage();
//    private static final BufferedImage playerZombieImage = ResourceLoader.zombiePlayerImage();

    public Zombie(float x, float y, MapData mapData) {
        super(x, y, MOVE_SPEED, HEALTH, mapData, DataPacket.Type.ZOMBIE);
        setState(DataPacket.State.WILD);
        setUsername("None");
    }

    public float getDx() {
        return dx;
    }

    public float getDy() {
        return dy;
    }

    public void convert(String username) {
        setUsername(username); // change the owner of the zombie to the new player
        setState(DataPacket.State.PLAYER);
    }

    public void move(double delta) {
        float moveX = dx * getMoveSpeed() * (float) delta;
        float moveY = dy * getMoveSpeed() * (float) delta;
        super.move(moveX,moveY);
    }

    // Zombie vector changed to follow player, if wild or if it is an opposing player
    public void followDirection(Player player) {
    	if (getState() == DataPacket.State.WILD || player.getUsername() != getUsername()) {
        	Vector zdv = ArtInt.followPlayer(getX(), getY(), player);
        	Vector znv = zdv.normalised();
        	
            dx = znv.x();
            dy = znv.y();
            
            face((int) zdv.x(), (int) zdv.y());
    	}
    }
    
    public void newMovingDir() {
        Vector zdv = Vector.randomVector();
        Vector znv = zdv.normalised();

        dx = znv.x();
        dy = znv.y();
        face((int) zdv.x(), (int) zdv.y()); // -x because of the original orientation of the zombie image
    }

    public void attack(Entity entity, int damageDone) {
        long now = System.nanoTime();

        if(getState() == DataPacket.State.PLAYER && entity.getUsername().equals(getUsername())) {
        	// Currently, do nothing
        }
        else {
            if (now - getLastAttackTime() > 1000000000L) {
                setLastAttackTime(now);
                entity.setHealth(entity.getHealth() - damageDone);
            }
        }
    }

}
