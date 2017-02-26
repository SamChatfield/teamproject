package game.util;

import java.awt.*;
import java.awt.geom.AffineTransform;

/**
 * Created by Daniel on 25/02/2017.
 */
public class DataPacket {

    public enum Tag { PLAYER, ZOMBIE, BULLET }

    private int health;
    private float x,y; // x and y position of the centre of this entity in the game coord system
    private double facingDirection;
    public Tag tag;

    public DataPacket(int health, float x, float y, double facingDirection, Tag tag) {
        this.health = health;
        this.x = x;
        this.y = y;
        this.facingDirection = facingDirection;
        this.tag = tag;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
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

    public double getFacingDirection() {
        return facingDirection;
    }

    public void setFacingDirection(double facingDirection) {
        this.facingDirection = facingDirection;
    }

    public void draw(Graphics2D g2d) {
        int w = image.getWidth();
        int h = image.getHeight();
        switch(tag){
            case PLAYER:
                // Width and height of the entity sprite
                if (showCollBox) {
                    g2d.setColor(Color.RED);
                    g2d.draw(collisionBox.getDrawRect(this));
                    g2d.setColor(Color.BLACK);
                }

                AffineTransform at = g2d.getTransform();
                g2d.rotate(getFacingDirection(), 320, 320);

                g2d.drawImage(image, 320 - w / 2, 320 - h / 2, null);
                g2d.setTransform(at);

                break;

            case BULLET:
                //        int drawX = Math.round(x - (width / 2.0f));
                //        int drawY = Math.round(y - (height / 2.0f));

                Point drawPoint = player.relativeDrawPoint(x(), y(), w, h);
                int drawX = drawPoint.x;
                int drawY = drawPoint.y;

                AffineTransform at2 = g2d.getTransform();
                g2d.rotate(getFacingDirection(), drawX, drawY);
                g2d.drawImage(image, drawX, drawY, null);
                g2d.setTransform(at2);

                break;

            case ZOMBIE:
                Point drawPoint = player.relativeDrawPoint(x, y, w, h);
                int drawX = drawPoint.x;
                int drawY = drawPoint.y;

                g2d.setColor(Color.GREEN);
                Rectangle healthBarFill = new Rectangle(drawX, drawY + 50, this.health, 2);
                g2d.fill(healthBarFill);
                g2d.setColor(Color.BLACK);
                if (showCollBox) {
                    g2d.setColor(Color.BLUE);
                    g2d.draw(collisionBox.getDrawRect(player));
                    g2d.setColor(Color.BLACK);
                }
                AffineTransform at = g2d.getTransform();
                g2d.rotate(getFacingDirection(), drawX + w / 2, drawY + h / 2);
                if(state == State.PLAYER) {
                    g2d.drawImage(playerImage, drawX, drawY, null);
                }
                else if(state == State.OPPONENT) {
                    // Change this later
                    g2d.drawImage(image, drawX, drawY, null);
                }
                else {
                    g2d.drawImage(image, drawX, drawY, null);
                }
                g2d.setTransform(at);

        }
    }

}
