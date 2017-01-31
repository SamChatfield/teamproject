package game;

import java.awt.*;

/**
 * Created by Sam on 31/01/2017.
 */
public class Bullet {

    private float x, y;

    public Bullet(Player player) {
        x = player.x();
        y = player.y();
    }

    public void draw(Graphics2D g2d) {
        g2d.fillRect((int) x, (int) y, 5, 5);
    }

}
