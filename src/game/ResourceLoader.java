package game;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Load resources into the game
 */
public class ResourceLoader {

	public static final String RES_PATH = "src/game/res/";

	/**
	 * Get image from a resource path
	 * @param image - Name of image to load
	 * @return BufferedImage loaded from filepath
	 * @throws IOException
	 */
	public static BufferedImage imageFromResPath(String image) throws IOException {
		return ImageIO.read(new File(RES_PATH + image));
	}

	/**
	 * Get image for player
	 * @return BufferedImage of player
	 */
	public static BufferedImage playerImage() {
		BufferedImage image = null;
		try {
			image = imageFromResPath("player.png");
		} catch (IOException e) {
			System.err.println("Error - Player image not found: " + e.getMessage());
			System.exit(1);
		}
		return image;
	}


	/**
	 * Get image for wild zombies
	 * @return BufferedImage of wild zombie
	 */
	public static BufferedImage zombieImage() {
		BufferedImage image = null;
		try {
			image =  imageFromResPath("zombie.png");
		} catch (IOException e) {
			System.err.println("Error - Wild Zombie image not found: " + e.getMessage());
			System.exit(1);
		}
		return image;
	}

	/**
	 * Get image for converted zombies on the player's team
	 * @return BufferedImage of zombie on player's team
	 */
	public static BufferedImage zombiePlayerImage() {
		BufferedImage image = null;
		try {
			image = imageFromResPath("zombieBlue.png");
		} catch (IOException e) {
			System.err.println("Error - Player zombie image not found: " + e.getMessage());
			System.exit(1);
		}
		return image;
	}

	/**
	 * Get image for bullets
	 * @return BufferedImage of bullet
	 */
	public static BufferedImage bulletImage() {
		BufferedImage image = null;
		try {
			return imageFromResPath("bullet.png");
		} catch (IOException e) {
			System.err.println("Error - Bullet image not found: " + e.getMessage());
			System.exit(1);
		}
		return image;
	}

	/**
	 * Import Tradewinds font into the game
	 * @return Imported Font
	 */
	public static Font getTradewindsFont() {
		Font tradeWinds = null;

		try {
			File font_file = new File("src/game/res/tradewinds.ttf");
			tradeWinds = Font.createFont(Font.TRUETYPE_FONT, font_file);
			GraphicsEnvironment genv = GraphicsEnvironment.getLocalGraphicsEnvironment();
			genv.registerFont(tradeWinds);
		} catch (FontFormatException e) {
			System.err.println("Error importing font: " + e.getMessage());
		} catch (IOException e) {
			System.err.println("Error importing font: " + e.getMessage());
		}
		return tradeWinds;
	}
}
