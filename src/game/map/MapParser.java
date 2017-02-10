package game.map;

import game.Game;
import game.ResourceLoader;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by Sam on 10/02/2017.
 */
public class MapParser {

    public static HashMap<String, TileType> parseTileTypes(String tilesheetName, String tileDataName) throws IOException {
        HashMap<String, TileType> tileTypes = new HashMap<>();

        BufferedImage tilesheet = ImageIO.read(new File(ResourceLoader.RES_PATH + tilesheetName));
        BufferedReader br = new BufferedReader(new FileReader(ResourceLoader.RES_PATH + tileDataName));

        String line;

        while ((line = br.readLine()) != null) {
            String[] a = line.split(","); // 0-Name, 1-ColourHex, 2-SheetX, 3-SheetY, 4-Obstacle

            String name = a[0];
            Color colour = Color.decode(a[1]);
            int sheetX = Integer.parseInt(a[2]);
            int sheetY = Integer.parseInt(a[3]);
            int tileSize = Game.TILE_SIZE;
            BufferedImage image = tilesheet.getSubimage(sheetX * tileSize, sheetY * tileSize, tileSize, tileSize);
            boolean obstacle = Boolean.parseBoolean(a[4]);

//            tileTypes.add(new TileType(name, colour, image, obstacle));
            tileTypes.put(name, new TileType(name, colour, image, obstacle));
        }
        System.out.println(tileTypes);

        br.close();

        return tileTypes;
    }

    public static Tile[][] parseMap(HashMap<String, TileType> tileTypes, BufferedImage mapImage){
        int width = mapImage.getWidth();
        int height = mapImage.getHeight();
        Tile[][] map = new Tile[width][height];



        return map;
    }

}
