package game.map;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

import game.ResourceLoader;
import game.client.Client;

/**
 * Parse and create the map for the game
 */
public class MapParser {

	/**
	 * Parse Tile Types
	 * @param tilesheetName Name of tile sheet
	 * @param tileDataName Name of tile data
	 * @return HashMap of Tile Types with their colour
	 * @throws IOException
	 */
	public static HashMap<Color, TileType> parseTileTypes(String tilesheetName, String tileDataName) throws IOException {
		HashMap<Color, TileType> tileTypes = new HashMap<>();

		BufferedImage tilesheet = ImageIO.read(new File(ResourceLoader.RES_PATH + tilesheetName));
		BufferedReader br = new BufferedReader(new FileReader(ResourceLoader.RES_PATH + tileDataName));

		String line;

		while ((line = br.readLine()) != null) {
			String[] a = line.split(","); // 0-Name, 1-ColourHex, 2-SheetX, 3-SheetY, 4-Obstacle

			String name = a[0];
			Color colour = Color.decode(a[1]);
			int sheetX = Integer.parseInt(a[2]);
			int sheetY = Integer.parseInt(a[3]);
			int tileSize = Client.TILE_SIZE;
			BufferedImage image = tilesheet.getSubimage(sheetX * tileSize, sheetY * tileSize, tileSize, tileSize);
			boolean obstacle = Boolean.parseBoolean(a[4]);

			tileTypes.put(colour, new TileType(name, colour, image, obstacle));
		}
		br.close();
		return tileTypes;
	}

	/**
	 * Parse the map
	 * @param tileTypes Type of tiles
	 * @param mapImage Sprite sheet image of map to use
	 * @return 2D array of Tiles
	 */
	public static Tile[][] parseMap(HashMap<Color, TileType> tileTypes, BufferedImage mapImage){
		int width = mapImage.getWidth();
		int height = mapImage.getHeight();
		Tile[][] map = new Tile[width][height];

		// System.out.println(new Color(mapImage.getRGB(0,0)));
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				Color colour = new Color(mapImage.getRGB(x, y));

				TileType type = tileTypes.get(colour);
				map[x][y] = new Tile(x - width / 2.0f + 0.5f, height / 2.0f - 0.5f - y, type);
			}
		}
		return map;
	}
}