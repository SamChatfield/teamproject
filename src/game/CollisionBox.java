package game;

import game.client.Client;
import game.client.Player;

import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * Created by Sam on 31/01/2017.
 */
public class CollisionBox {

    private Entity owner;
    private float width, height; // width and height of the box in the game coord system

//    public CollisionBox(Entity owner, float width, float height) {
public CollisionBox(Entity owner) {
        this.owner = owner;
        width = (float) owner.image.getWidth() / (float) Client.TILE_SIZE;
        height = (float) owner.image.getHeight() / (float) Client.TILE_SIZE;
    }

    public Rectangle2D.Float getRect() {
        return new Rectangle2D.Float(owner.x() - width / 2, owner.y() - height / 2, width, height);
    }

    /**
     * Get the screen rectangle representing this collision box relative to the player
     * @param p player
     * @return rectangle to draw to the screen for this box
     */
    public Rectangle2D.Float getDrawRect(Player p) {
        float px = p.x();
        float py = p.y();

        Rectangle2D.Float drawRect;

        int iwidth = owner.image.getWidth(); // width of the image of the box
        int iheight = owner.image.getHeight(); // height of the image of the box

        // This is the player's collision box if true
        if (owner instanceof Player && getX() == px && getY() == py) {
            drawRect = new Rectangle2D.Float(320.0f - iwidth / 2, 320.0f - iheight / 2, iwidth, iheight);
        } else {
            Point drawPoint = p.relativeDrawPoint(owner.x(), owner.y(), iwidth, iheight);
            drawRect = new Rectangle2D.Float(drawPoint.x, drawPoint.y, iwidth, iheight);
        }

        return drawRect;
    }

    public float getX() {
        return owner.x();
    }

    public float getY() {
        return owner.y();
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public boolean intersects(CollisionBox otherBox) {
        return getRect().intersects(otherBox.getRect());
    }

}
