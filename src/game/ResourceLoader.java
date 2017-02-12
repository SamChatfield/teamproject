package game;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Sam on 20/01/2017.
 */
public class ResourceLoader {

    private static final String RES_PATH = "src/game/res/";

    public static BufferedImage[] playerImages() throws IOException {
        String[] playerImages = {"playerFront.png", "playerBack.png","playerBack2.png", "playerLeft.png", "playerRight.png"};
        BufferedImage[] images = new BufferedImage[playerImages.length];

        for (int i = 0; i < images.length; i++) {
            images[i] = imageFromResPath(playerImages[i]);
        }

        return images;
    }

    private static BufferedImage imageFromResPath(String image) throws IOException {
        return ImageIO.read(new File(RES_PATH + image));
    }

    public static BufferedImage playerImage() throws IOException {
        return imageFromResPath("playerFront.png");
    }

    // TODO streamline this and player loading since they will do pretty much the same thing
    public static BufferedImage zombieImage() throws IOException {
        return imageFromResPath("playerBack.png");
    }
    
    public static BufferedImage zombiePlayerImage() throws IOException {
    	return imageFromResPath("playerBack2.png");
    }

    public static BufferedImage bulletImage() throws IOException {
        return imageFromResPath("bullet.png");
    }

}
