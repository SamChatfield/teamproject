package game.map;

import game.Entity;
import game.ResourceLoader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

/**
 * Class to represent Map Data - The map itself, the size of the map, and the types of tile on the map
 */
public class MapData {

	private int width, height; // width and height of the mapData in game coords, e.g. for 50x50 tile mapData w=50.0, h=50.0
	private HashMap<Color, TileType> tileTypes;
	private Tile[][] map;

	/**
	 * Constructor for MapData
	 * @param mapPath Path to map file
	 * @param tilesheet Name of tile sheet file
	 * @param tileData Name of tile data file
	 */
	public MapData(String mapPath, String tilesheet, String tileData) {

		// Create the set of different types of tile
		try {
			tileTypes = MapParser.parseTileTypes(tilesheet, tileData);
		} catch (IOException e) {
			System.out.println("Failed to parse tile types");
			e.printStackTrace();
			System.exit(1);
		}

		// Parse and create the map
		try {
			BufferedImage mapImage = ResourceLoader.imageFromResPath(mapPath);
			width = mapImage.getWidth();
			height = mapImage.getHeight();
			map = MapParser.parseMap(tileTypes, mapImage);
		} catch (IOException e) {
			System.out.println("Failed to parse map");
			e.printStackTrace();
			System.exit(1);
		}
	}

	/**
	 * Check if the entity (zombie/player/bullet) can move on a specific coordinate
	 * This stops entities going over obstacles such as trees
	 * @param x X coordinate to check
	 * @param y Y coordinate to check
	 * @param e Specific entity (to get the width and height)
	 * @return Whether the entities move is valid
	 */
	public boolean isEntityMoveValid(float x, float y, Entity e) {
		float wBound = width / 2.0f;
		float hBound = height / 2.0f;
		float ewr = e.getCollisionBox().getWidth() / 2.0f; // entity width radius
		float ehr = e.getCollisionBox().getHeight() / 2.0f; // entity height radius

		boolean inBounds = x >= -wBound + ewr && x <= wBound - ewr && y >= -hBound + ehr && y <= hBound - ehr;

		if (!inBounds) {
			return false;
		}

		TileType tileHere = tileTypeAt(x, y);
		boolean nonObstacle = !tileHere.isObstacle();

		if (nonObstacle)
			return true;
		else
			return false;
	}

	/**
	 * Get the type of tile at a specific coordinate
	 * @param x X coordinate
	 * @param y Y coordinate
	 * @return Type of tile at that coordinate
	 */
	public TileType tileTypeAt(float x, float y) {
		int ix = Math.round(x + width / 2.0f - 0.5f);
		int iy = Math.round(height / 2.0f - 0.5f - y);
		//System.out.println("ix:" + ix + ", iy:" + iy);
		return map[ix][iy].getType();
	}

	/**
	 * Get the map
	 * @return 2D array of tiles = the map
	 */
	public Tile[][] getMap() {
		return map;
	}

	/**
	 * Get the width of the map
	 * @return Width of map
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Get the height of the map
	 * @return Height of map
	 */
	public int getHeight() {
		return height;
	}
}