package game.map;
import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 * Represents tiles in the game
 */
public class TileType {

	private String name;
	private Color repColour;
	private BufferedImage image;
	private boolean obstacle;

	/**
	 * Constructor to make new tile
	 * @param (String) name - Name of tile
	 * @param (Color) repColour - Colour of tile
	 * @param (BufferedImage) image - Tile image
	 * @param (Boolean) obstacle - If tile is an obstacle
	 */
	public TileType(String name, Color repColor, BufferedImage image, boolean obstacle) {
		this.name = name;
		this.repColour = repColor;
		this.image = image;
		this.obstacle = obstacle;
	}

	/**
	 * Get name of tile
	 * @return (String) - Name of tile
	 */
	public String getName() {
		return name;
	}

	/**
	 * Get colour of tile
	 * @return (Color) Colour of tile
	 */
	public Color getRepColour() {
		return repColour;
	}

	/**
	 * Get the image of the tile
	 * @return (BufferedImage) Image of tile
	 */
	public BufferedImage getImage() {
		return image;
	}

	/**
	 * Get if the tile is an obstacle (cannot be walked upon)
	 * @return (boolean) If tile is obstacle
	 */
	public boolean isObstacle() {
		return obstacle;
	}

	@Override
	public String toString() {
		return name;
	}
}