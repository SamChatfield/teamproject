package game.map;

/**
 * Class that represents individual tiles in the game, each with an X, Y coordinate and a types
 */
public class Tile {

	private float x, y;
	private TileType type;

	/**
	 * Constructor to create a new tile
	 * @param (float) x - X coordinate of tile
	 * @param (float) y - Y coordinate of tile
	 * @param (TileType) type - Type of tile to create
	 */
	public Tile(float x, float y, TileType type) {
		this.x = x;
		this.y = y;
		this.type = type;
	}

	/**
	 * Get X coordinate of tile
	 * @return (float) X coordinate of tile
	 */
	public float getX() {
		return x;
	}

	/**
	 * Get Y coordinate of tile
	 * @return (float) Y coordinate of tile
	 */
	public float getY() {
		return y;
	}

	/**
	 * Get the type of the tile
	 * @return (TileType) Type of tile
	 */
	public TileType getType() {
		return type;
	}

	@Override
	public String toString() {
		return "(" + x + ", " + y + ") - " + type;
	}
}