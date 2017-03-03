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

    public static BufferedImage playerImage() {
        BufferedImage image = null;
        try {
            image = imageFromResPath("player.png");
        } catch (IOException e) {
            System.out.println("RIP. Player image not found");
            System.exit(0);
        }
        return image;
    }

    // TODO streamline this and player loading since they will do pretty much the same thing
    public static BufferedImage zombieImage() {
        BufferedImage image = null;
        try {
            image =  imageFromResPath("zombie.png");
        } catch (IOException e) {
            System.out.println("RIP. Zombie image not found");
            System.exit(0);
        }
        return image;
    }
    
    public static BufferedImage zombiePlayerImage() {
        BufferedImage image = null;
        try {
            image = imageFromResPath("zombieBlue.png");
        } catch (IOException e) {
            System.out.println("RIP. Zombie player image not found");
            System.exit(0);
        }
        return image;
    }

    public static BufferedImage bulletImage() {
        BufferedImage image = null;
        try {
            return imageFromResPath("bullet.png");
        } catch (IOException e) {
            System.out.println("RIP. Bullet image not found");
            System.exit(0);
        }
        return image;
    }

}
