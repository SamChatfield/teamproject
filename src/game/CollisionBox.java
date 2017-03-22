package game;

import game.client.Client;
import game.client.Player;
import game.client.Renderer;
import game.util.DataPacket;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;

/**
 * Created by Sam on 31/01/2017.
 */
public class CollisionBox implements Serializable {

	private Entity owner;
	private float width, height; // width and height of the box in the game coord system
	private int iwidth, iheight;

	/**
	 * Create a new collision box
	 * @param owner Owner of this collision box (entity)
	 */
	public CollisionBox(Entity owner) {
		this.owner = owner;

		if (owner instanceof Player) {
			width = (float) Player.getImage().getWidth() / (float) Client.TILE_SIZE;
			height = (float) Player.getImage().getHeight() / (float) Client.TILE_SIZE;

			iwidth = Player.getImage().getWidth();
			iheight = Player.getImage().getHeight();

		} else if (owner instanceof Zombie){
			width = (float) Renderer.wildZombieImage.getWidth() / (float) Client.TILE_SIZE;
			height = (float) Renderer.wildZombieImage.getHeight() / (float) Client.TILE_SIZE;

			iwidth = Renderer.wildZombieImage.getWidth();
			iheight = Renderer.wildZombieImage.getHeight();

		} else {
			width = (float) Renderer.bulletImage.getWidth() / (float) Client.TILE_SIZE;
			height = (float) Renderer.bulletImage.getHeight() / (float) Client.TILE_SIZE;

			iwidth = Renderer.bulletImage.getWidth();
			iheight = Renderer.bulletImage.getHeight();
		}
	}

	/**
	 * Get a rectangle representing the collision box
	 * @return Rectangle of collision box
	 */
	public Rectangle2D.Float getRect() {
		return new Rectangle2D.Float(owner.getX() - width / 2, owner.getY() - height / 2, width, height);
	}

	/**
	 * Get the screen rectangle representing this collision box relative to the player
	 * @param p player
	 * @return rectangle to draw to the screen for this box
	 */
	public Rectangle2D.Float getDrawRect(Player p) {
		float px = p.getX();
		float py = p.getY();

		Rectangle2D.Float drawRect;

		// This is the player's collision box if true
		if (owner instanceof Player && getX() == px && getY() == py) {
			drawRect = new Rectangle2D.Float(320.0f - iwidth / 2, 320.0f - iheight / 2, iwidth, iheight);
		} else {
			Point drawPoint = p.relativeDrawPoint(owner.getX(), owner.getY(), iwidth, iheight);
			drawRect = new Rectangle2D.Float(drawPoint.x, drawPoint.y, iwidth, iheight);
		}

		return drawRect;
	}

	/**
	 * Get X coordinate of owner of collision box
	 * @return X coordinate of owner
	 */
	public float getX() {
		return owner.getX();
	}

	/**
	 * Get Y coordinate of owner of collision box
	 * @return Y coordinate of owner
	 */
	public float getY() {
		return owner.getY();
	}

	/**
	 * Get width of collision box
	 * @return Width
	 */
	public float getWidth() {
		return width;
	}

	/**
	 * Get height of collision box
	 * @return Height
	 */
	public float getHeight() {
		return height;
	}

	/**
	 * Get whether this collision box intersects with another
	 * @param otherBox Another CollisionBox
	 * @return Boolean of whether collided
	 */
	public boolean intersects(CollisionBox otherBox) {
		return getRect().intersects(otherBox.getRect());
	}

	/**
	 * Get collision box rectanngle from DataPacket
	 * @param z DataPacket object
	 * @param p Player object
	 * @return Rectangle
	 */
	public static Rectangle2D.Float collBoxRectFromData(DataPacket z, Player p) {
		Rectangle2D.Float drawRect;

		float width = (float) Renderer.wildZombieImage.getWidth() / (float) Client.TILE_SIZE;
		float height = (float) Renderer.wildZombieImage.getHeight() / (float) Client.TILE_SIZE;

		int iwidth = Renderer.wildZombieImage.getWidth();
		int iheight = Renderer.wildZombieImage.getHeight();

		Point drawPoint = p.relativeDrawPoint(z.getX(), z.getY(), iwidth, iheight);
		drawRect = new Rectangle2D.Float(drawPoint.x, drawPoint.y, iwidth, iheight);

		return drawRect;
	}
}