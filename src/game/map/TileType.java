package game.map;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;

/**
 * Created by Sam on 10/02/2017.
 */
public class TileType {

    private String name;
    private Color repColour;
    private BufferedImage image;
    private boolean obstacle;

    public TileType(String name, Color repColor, BufferedImage image, boolean obstacle) {
        this.name = name;
        this.repColour = repColor;
        this.image = image;
        this.obstacle = obstacle;
    }

    public String getName() {
        return name;
    }

    public Color getRepColour() {
        return repColour;
    }

    public BufferedImage getImage() {
        return image;
    }

    public boolean isObstacle() {
        return obstacle;
    }

    @Override
    public String toString() {
        return name;
    }

}
