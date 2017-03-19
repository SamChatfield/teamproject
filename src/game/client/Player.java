package game.client;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import game.Bullet;
import game.Entity;
import game.ResourceLoader;
import game.map.MapData;
import game.util.DataPacket;

/**
 * Class to represent the player in the game
 */
public class Player extends Entity {

	private static final float COLL_BOX_WIDTH = 25.0f;
	private static final float COLL_BOX_HEIGHT = 25.0f;
	public static final int HEALTH = 50;
	private static final long SHOOT_DELAY = 500000000L; // Min time between player shots, 0.5 seconds
	private static final float MOVE_SPEED = 0.1f;
	private static final BufferedImage image = ResourceLoader.playerImage();

	private boolean showCollBox;

	public boolean conversionMode;
    private ArrayList<Bullet> bullets;

    /**
     * Create a new Player object in the game
     * @param x Initial X coordinate
     * @param y Initial Y coordinate
     * @param mapData The game map
     * @param username Username of this player
     */
    public Player(float x, float y, MapData mapData, String username) {
        super(x, y, MOVE_SPEED, HEALTH, mapData, DataPacket.Type.PLAYER);

        showCollBox = false;
        bullets = new ArrayList<>(20);
        setUsername(username);
        
        // Initially cannot convert zombies
        conversionMode = false;
    }

    /**
     * Shoots player's weapon in direction of mouse cursor
     * @param aimX Direction X weapon is aimed in
     * @param aimY Direction Y weapon is aimed in
     * @return Bullet object travelling in specified direction
     */
    public Bullet shoot(float aimX, float aimY) {
        // Limit the player to firing at their shooting speed
        long now = System.nanoTime();
        if (now - getLastAttackTime() > SHOOT_DELAY) {
            setLastAttackTime(now);
            return new Bullet(this, aimX, aimY, mapData);
        } else {
        	return null;
        }
    }

    /**
     * Smaller method than above, used on the client so it knows if it can shoot on the server
     * @return Boolean of whether the player can shoot.
     */
    public boolean canShoot(){
        long now = System.nanoTime();
        if (now - getLastAttackTime() > SHOOT_DELAY) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Draw the player on the screen
     * @param g2d Graphics2D object
     */
    public void draw(Graphics2D g2d) {
        // Width and height of the entity sprite
        int w = image.getWidth();
        int h = image.getHeight();

        if (showCollBox) {
            g2d.setColor(Color.RED);
            g2d.draw(collisionBox.getDrawRect(this));
            g2d.setColor(Color.BLACK);
        }

        // Ensures player is facing the right direction (based on mouse pointer location)
        AffineTransform at = g2d.getTransform();
        g2d.rotate(data.getFacingAngle(), 320, 320);
        
        g2d.drawImage(image, 320 - w / 2, 320 - h / 2, null);
        g2d.setTransform(at);
        
    }

    /**
     * Draw relative to the other player
     * @param g2d Graphics2D object
     * @param player Player object
     */
    public void drawRelativeToOtherPlayer(Graphics2D g2d, Player player) {
        // Width and height of the entity sprite
        int w = image.getWidth();
        int h = image.getHeight();

        Point drawPoint = player.relativeDrawPoint(getX(), getY(), w, h);
        int drawX = drawPoint.x;
        int drawY = drawPoint.y;

        g2d.setColor(Color.GREEN);
        Rectangle healthBarFill = new Rectangle(drawX, drawY + 50, getHealth(), 2);
        g2d.fill(healthBarFill);
        g2d.setColor(Color.BLACK);

        if (showCollBox) {
            g2d.setColor(Color.BLUE);
            g2d.draw(collisionBox.getDrawRect(player));
            g2d.setColor(Color.BLACK);
        }

        AffineTransform at = g2d.getTransform();
        g2d.rotate(data.getFacingAngle(), drawX + w / 2, drawY + h / 2);

        g2d.drawImage(image, drawX, drawY, null);
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
        float px = getX(); // player x pos
        float py = getY(); // player y pos
        float pvr = Client.VIEW_SIZE / 2.0f; // player view radius - 5.0
        int swr = Client.GAME_DIMENSION.width / 2; // screen width radius
        int shr = Client.GAME_DIMENSION.height / 2; // screen height radius

        int drawX = swr + Math.round((x - px) / pvr * swr) - (w / 2); // 320 + ((2 - 6) / 5 * 320)
        int drawY = shr + Math.round((py - y) / pvr * shr) - (h / 2); // 320 + ((6 - 2) / 5 * 320) y is inverted because our coord system is traditional whereas awt origin is top-left
        // System.out.println("Draw x,y: (" + drawX + "," + drawY + ") and actual x,y: (" + x + "," + y + ") and p: (" + px + "," + py + ")");

        return new Point(drawX, drawY);
    }

    /**
     * Get list of bullets currently in the game (shot) that belong to the player
     * @return ArrayList of bullets
     */
    public ArrayList<Bullet> getBullets() {
        return bullets;
    }

    /**
     * Get the image used for the player 
     * @return BufferedImage of player image
     */
    public static BufferedImage getImage() {
        return image;
    }

    /**
     * Set whether to show collision boxes
     * @param showCollBox Boolean of whether to show collision boxes
     */
    public void setShowCollBox(boolean showCollBox) {
        this.showCollBox = showCollBox;
    }

}