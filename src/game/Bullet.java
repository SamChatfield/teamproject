package game;

import game.util.Vector;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

/**
 * Created by Sam on 31/01/2017.
 */
public class Bullet {

    public static final float BULLET_SPEED = 4.0f;

    private float x, y; // the x and y coord of the middle of the bullet
    private float dx, dy; // the change in x and y of the bullet each update before delta
    private float width, height;
    private double facingAngle;
    private CollisionBox collisionBox;
    private Rectangle2D.Float shape;
    private boolean active;

    public Bullet(Player player, float aimX, float aimY, float width, float height) {
        x = player.x();
        y = player.y();

        Vector normalDir = new Vector(aimX, aimY).normalised();
        dx = normalDir.x();
        dy = normalDir.y();

        this.width = width;
        this.height = height;
        facingAngle = player.getFacingAngle(); // TODO check the efficiency of this
        collisionBox = new CollisionBox(x, y, width, height);
        shape = new Rectangle2D.Float(x, y, width, height);
        active = true;
    }

    public Bullet(Player player, float aimX, float aimY) {
        new Bullet(player, aimX, aimY, 5.0f, 5.0f);
    }

    public void move(double delta) {
        x += dx * BULLET_SPEED * delta;
        y += dy * BULLET_SPEED * delta;
        collisionBox.move(dx * BULLET_SPEED * (float) delta, dy * BULLET_SPEED * (float) delta);
    }

    public void draw(Graphics2D g2d) {
        int screenX;
        int screenY;

        int drawX = Math.round(x - (width / 2.0f));
        int drawY = Math.round(y - (height / 2.0f));

        g2d.setColor(Color.BLACK);
        AffineTransform at = g2d.getTransform();
        g2d.rotate(facingAngle, x, y);
        g2d.fillRect(drawX, drawY, (int) width, (int) height);
        g2d.setTransform(at);
    }

    public void damage(Entity entity, int damageDone) {
        if (active) {
            System.out.println("Player hit entity");
            entity.health -= damageDone;
            active = false;
        }
    }

    public CollisionBox getCollisionBox() {
        return collisionBox;
    }

    public boolean isActive() {
        return active;
    }

}
