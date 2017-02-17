package game;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Sam on 20/01/2017.
 */
public class ResourceLoader {

    public static final String RES_PATH = "src/game/res/";

    public static BufferedImage imageFromResPath(String image) throws IOException {
        return ImageIO.read(new File(RES_PATH + image));
    }

    public static BufferedImage playerImage() throws IOException {
        return imageFromResPath("player.png");
    }

    // TODO streamline this and player loading since they will do pretty much the same thing
    public static BufferedImage zombieImage() throws IOException {
        return imageFromResPath("zombie.png");
    }
    
    public static BufferedImage zombiePlayerImage() throws IOException {
    	return imageFromResPath("zombieBlue.png");
    }

    public static BufferedImage bulletImage() throws IOException {
        return imageFromResPath("bullet.png");
    }

}
