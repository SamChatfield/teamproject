package game.map;

import game.client.Client;
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

//            tileTypes.add(new TileType(name, colour, image, obstacle));
            tileTypes.put(colour, new TileType(name, colour, image, obstacle));
        }
        System.out.println(tileTypes);

        br.close();

        return tileTypes;
    }

    public static Tile[][] parseMap(HashMap<Color, TileType> tileTypes, BufferedImage mapImage){
        int width = mapImage.getWidth();
        int height = mapImage.getHeight();
        Tile[][] map = new Tile[width][height];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color colour = new Color(mapImage.getRGB(x, y));
                TileType type = tileTypes.get(colour);
                map[x][y] = new Tile(x - width / 2.0f + 0.5f, height / 2.0f - 0.5f - y, type);
            }
            System.out.println();
        }

        return map;
    }

}
