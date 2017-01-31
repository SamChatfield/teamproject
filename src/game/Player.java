package game;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Created by Sam on 20/01/2017.
 */
public class Player extends Entity {

    private static final float COLL_BOX_WIDTH = 25.0f;
    private static final float COLL_BOX_HEIGHT = 25.0f;
    private static final int HEALTH = 50;
    private static final long SHOOT_DELAY = 500000000L; // Min time between player shots, 0.5 seconds

    private ArrayList<Bullet> bullets;

    public Player(float x, float y, BufferedImage image) {
//        super(x, y, 2.0f, new Rectangle2D.Float((x - COLL_BOX_WIDTH), (y - COLL_BOX_HEIGHT), COLL_BOX_WIDTH, COLL_BOX_HEIGHT), image);
        super(x, y, 2.0f, HEALTH, new CollisionBox(x, y, COLL_BOX_WIDTH, COLL_BOX_HEIGHT), image);
        bullets = new ArrayList<>(20);
    }

    public void shoot() {
        long now = System.nanoTime();
        if (now - lastAttackTime > SHOOT_DELAY) {
            lastAttackTime = now;
            System.out.println("Shoot");
        }
    }

}
