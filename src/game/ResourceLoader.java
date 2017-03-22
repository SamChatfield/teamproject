package game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

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
	 * Load player image into the game
	 * @return BufferedImage of the player
	 */
	public static BufferedImage opponentImage() {
		return getImage("playerRed.png");
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
	 * Load image for speed up powerup into the game
	 * @return BufferedImage for speed up powerup
	 */
	public static BufferedImage speedUp() {
		return getImage("speedUp.png");
	}

	/**
	 * Load image for more health powerup into the game
	 * @return BufferedImage for more health powerup
	 */
	public static BufferedImage moreHealth() {
		return getImage("moreHealth.png");
	}

	/**
	 * Load image for invert controls powerup into the game
	 * @return BufferedImage for invert controls powerup
	 */
	public static BufferedImage invertControls() {
		return getImage("invertControls.png");
	}

	/**
	 * Load image for freeze other player powerup into the game
	 * @return BufferedImage for freeze other player powerup
	 */
	public static BufferedImage freezePlayer() {
		return getImage("freezePlayer.png");
	}

	/**
	 * Load image for speed down powerup into the game
	 * @return BufferedImage for speed down powerup
	 */
	public static BufferedImage speedDown() {
		return getImage("speedDown.png");
	}

	/**
	 * Load image for the game icon
	 * @return BufferedImage for the icon
	 */
	public static BufferedImage iconImage() {
		return getImage("icon.png");
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
		} catch (FontFormatException | IOException e) {
			System.err.println("Error importing font: " + e.getMessage());
		}

        return tradeWinds;
	}

}