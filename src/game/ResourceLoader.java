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

	private static BufferedImage getImage(String path) {
		BufferedImage image = null;
		try {
			image = imageFromResPath(path);
		} catch (IOException e) {
			System.err.println("Error! Image + /" + path + "/ not found: " + e.getMessage() );
			System.exit(1);
		}
		return image;
	}

	/**
	 * Load player image into the game
	 * @return BufferedImage of the player
	 */
	public static BufferedImage playerImage() {
		return getImage("player.png");
	}

	/**
	 * Load wild zombie image into the game
	 * @return BufferedImage of a wild zombie
	 */
	public static BufferedImage zombieImage() {
		return getImage("zombie.png");
	}

	/**
	 * Load converted zombie image into the game
	 * @return BufferedImage of a converted zombie
	 */
	public static BufferedImage zombiePlayerImage() {
		return getImage("zombieBlue.png");
	}
	
	/**
	 * Load converted zombie image into the game
	 * @return BufferedImage of a converted zombie
	 */
	public static BufferedImage zombieOpponentImage() {
		return getImage("zombieRed.png");
	}

	/**
	 * Load image for bullets into the game
	 * @return BufferedImage of a bullet
	 */
	public static BufferedImage bulletImage() {
		return getImage("bullet.png");
	}

	/**
	 * Load image for lighting into the game
	 * @return BufferedImage for lighting
	 */
	public static BufferedImage lightingImage() {
		return getImage("spotlight.png");
	}

	/**
	 * Import Tradewinds font into the game
	 * @return Imported Font
	 */
	public static Font getTradewindsFont() {
		Font tradeWinds = null;

		try {
			File font_file = new File(RES_PATH + "tradewinds.ttf");
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