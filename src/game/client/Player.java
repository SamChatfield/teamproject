package game.client;

import game.Bullet;
import game.Entity;
import game.ResourceLoader;
import game.client.Client;
import game.map.MapData;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Sam on 20/01/2017.
 */
public class Player extends Entity {
	
	// TODO: Fix issue of spawning on top of zombies

    private static final float COLL_BOX_WIDTH = 25.0f;
    private static final float COLL_BOX_HEIGHT = 25.0f;
    private static final int HEALTH = 100;
    private static final long SHOOT_DELAY = 500000000L; // Min time between player shots, 0.5 seconds
    private static final float MOVE_SPEED = 0.1f;
	public boolean conversionMode;

    private ArrayList<Bullet> bullets;
    private MapData mapData;

    public Player(float x, float y, BufferedImage image, MapData mapData) {
//        super(x, y, 2.0f, new Rectangle2D.Float((x - COLL_BOX_WIDTH), (y - COLL_BOX_HEIGHT), COLL_BOX_WIDTH, COLL_BOX_HEIGHT), image);
//        super(x, y, 2.0f, HEALTH, new CollisionBox(x, y, COLL_BOX_WIDTH, COLL_BOX_HEIGHT), image);
        super(x, y, MOVE_SPEED, HEALTH, image, mapData);
        bullets = new ArrayList<>(20);
        conversionMode = false;
    }

    public void shoot(float aimX, float aimY) {
        // Limit the player to firing at their shooting speed
        long now = System.nanoTime();
        if (now - lastAttackTime > SHOOT_DELAY) {
            lastAttackTime = now;
            try {
                bullets.add(new Bullet(this, aimX, aimY, ResourceLoader.bulletImage(), mapData));
            } catch (IOException e) {
                System.out.println("Couldn't get bullet image. RIP");
                e.printStackTrace();
                System.exit(1);
            }
        }
    }

    public void draw(Graphics2D g2d, MapData mapData) {
        int screenX;
        int screenY;

        // Width and height of the entity sprite
        int w = image.getWidth();
        int h = image.getHeight();

        if (showCollBox) {
            g2d.setColor(Color.RED);
            g2d.draw(collisionBox.getDrawRect(this));
            g2d.setColor(Color.BLACK);
        }

        AffineTransform at = g2d.getTransform();
        g2d.rotate(facingAngle, 320, 320);
        g2d.drawImage(image, 320 - w / 2, 320 - h / 2, null);
        g2d.setTransform(at);
    }

    /**
     * Calculate the point relative to the player at which the given entity will be drawn
     * @param x x coord of entity
     * @param y y coord of entity
     * @param w width of entity sprite
     * @param h height of entity sprite
     * @return the screen point at which to draw the entity
     */
    public Point relativeDrawPoint(float x, float y, int w, int h) {
        float px = this.x; // player x pos
        float py = this.y; // player y pos
        float pvr = Client.VIEW_SIZE / 2.0f; // player view radius - 5.0
        int swr = Client.GAME_DIMENSION.width / 2; // screen width radius
        int shr = Client.GAME_DIMENSION.height / 2; // screen height radius

        int drawX = swr + Math.round((x - px) / pvr * swr) - (w / 2); // 320 + ((2 - 6) / 5 * 320)
        int drawY = shr + Math.round((py - y) / pvr * shr) - (h / 2); // 320 + ((6 - 2) / 5 * 320) y is inverted because our coord system is traditional whereas awt origin is top-left
//        System.out.println("Draw x,y: (" + drawX + "," + drawY + ") and actual x,y: (" + x + "," + y + ") and p: (" + px + "," + py + ")");

        return new Point(drawX, drawY);
    }

    public ArrayList<Bullet> getBullets() {
        return bullets;
    }

}
