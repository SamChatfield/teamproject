package game.map;

import game.ResourceLoader;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by Sam on 30/01/2017.
 */
public class MapData {

    private int width, height; // width and height of the mapData in game coords, e.g. for 50x50 tile mapData w=50.0, h=50.0
    private HashMap<String, TileType> tileTypes;
    private Tile[][] map;

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

    public boolean isInMap(float x, float y) {
        float wBound = width - (width / 2.0f);
        float hBound = height - (height / 2.0f);
        return x >= -wBound && x <= wBound && y >= -hBound && y <= hBound;
    }

    public HashMap<String, TileType> getTileTypes() {
        return tileTypes;
    }

}
