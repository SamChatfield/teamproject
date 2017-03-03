package game.map;

import game.Entity;
import game.ResourceLoader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by Sam on 30/01/2017.
 */
public class MapData {

    private int width, height; // width and height of the mapData in game coords, e.g. for 50x50 tile mapData w=50.0, h=50.0
    private HashMap<Color, TileType> tileTypes;
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

    public TileType tileTypeAt(float x, float y) {
        int ix = Math.round(x + width / 2.0f - 0.5f);
        int iy = Math.round(height / 2.0f - 0.5f - y);
        //System.out.println("ix:" + ix + ", iy:" + iy);
        return map[ix][iy].getType();
    }

    public Tile[][] getMap() {
        return map;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

}
