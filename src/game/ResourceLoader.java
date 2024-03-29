package game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Load resources into the game
 */
public class ResourceLoader {

    public static final String RES_PATH = "src/game/res/";

    /**
     * Get image from a resource path
     *
     * @param image - Name of image to load
     * @return BufferedImage loaded from filepath
     * @throws IOException
     */
    public static BufferedImage imageFromResPath(String image) throws IOException {
        return ImageIO.read(new File(RES_PATH + image));
    }

    private static BufferedImage getImage(String path) {
        BufferedImage image = null;
        try {
            image = imageFromResPath(path);
        } catch (IOException e) {
            System.err.println("Error! Image + /" + path + "/ not found: " + e.getMessage());
            System.exit(1);
        }
        return image;
    }

    /**
     * Load player image into the game
     *
     * @return BufferedImage of the player
     */
    public static BufferedImage playerImage() {
        return getImage("player.png");
    }

    /**
     * Load player image into the game
     *
     * @return BufferedImage of the player
     */
    public static BufferedImage opponentImage() {
        return getImage("playerRed.png");
    }

    /**
     * Load wild zombie image into the game
     *
     * @return BufferedImage of a wild zombie
     */
    public static BufferedImage zombieImage() {
        return getImage("zombie.png");
    }

    /**
     * Load converted zombie image into the game
     *
     * @return BufferedImage of a converted zombie
     */
    public static BufferedImage zombiePlayerImage() {
        return getImage("zombieBlue.png");
    }

    /**
     * Load converted zombie image into the game
     *
     * @return BufferedImage of a converted zombie
     */
    public static BufferedImage zombieOpponentImage() {
        return getImage("zombieRed.png");
    }

    /**
     * Load image for bullets into the game
     *
     * @return BufferedImage of a bullet
     */
    public static BufferedImage bulletImage() {
        return getImage("bullet.png");
    }

    /**
     * Load image for lighting into the game
     *
     * @return BufferedImage for lighting
     */
    public static BufferedImage lightingImage() {
        return getImage("spotlight.png");
    }

    /**
     * Load image for speed up powerup into the game
     *
     * @return BufferedImage for speed up powerup
     */
    public static BufferedImage speedUp() {
        return getImage("/powerups/speedUp.png");
    }

    /**
     * Load image for more health powerup into the game
     *
     * @return BufferedImage for more health powerup
     */
    public static BufferedImage moreHealth() {
        return getImage("/powerups/moreHealth.png");
    }

    /**
     * Load image for invert controls powerup into the game
     *
     * @return BufferedImage for invert controls powerup
     */
    public static BufferedImage invertControls() {
        return getImage("/powerups/invertControls.png");
    }

    /**
     * Load image for freeze other player powerup into the game
     *
     * @return BufferedImage for freeze other player powerup
     */
    public static BufferedImage freezePlayer() {
        return getImage("/powerups/freezePlayer.png");
    }

    /**
     * Load image for speed down powerup into the game
     *
     * @return BufferedImage for speed down powerup
     */
    public static BufferedImage speedDown() {
        return getImage("/powerups/speedDown.png");
    }

    /**
     * Load image for converter powerup into the game
     *
     * @return BufferedImage for converter powerup
     */
    public static BufferedImage coz() {
        return getImage("/powerups/converter.png");
    }

    /**
     * Load image for the blood splatter
     *
     * @return BufferedImage for the blood splatter
     */
    public static BufferedImage splatter1() {
        return getImage("splatter1.png");
    }


    /**
     * Load image for the blood splatter
     *
     * @return BufferedImage for the blood splatter
     */
    public static BufferedImage splatter2() {
        return getImage("splatter2.png");
    }


    /**
     * Load image for the blood splatter
     *
     * @return BufferedImage for the blood splatter
     */
    public static BufferedImage splatter3() {
        return getImage("splatter3.png");
    }

    /**
     * Load image for the machine gun
     *
     * @return BufferedImage for the machine gun
     */
    public static BufferedImage machineGun() {
        return getImage("/weapons/spawns/machineGun.png");
    }

    /**
     * Load image for the shotgun
     *
     * @return BufferedImage for the shotgun
     */
    public static BufferedImage shotgun() {
        return getImage("/weapons/spawns/shotgun.png");
    }

    /**
     * Load image for the converter
     *
     * @return BufferedImage for the converter
     */
    public static BufferedImage converter() {
        return getImage("/weapons/spawns/converter.png");
    }

    /**
     * Load image for the uzi
     *
     * @return BufferedImage for the uzi
     */
    public static BufferedImage uzi() {
        return getImage("/weapons/spawns/uzi.png");
    }

    ///////// HOT BAR IMAGES

    /**
     * Load image for the pistol
     *
     * @return BufferedImage for the pistol
     */
    public static BufferedImage pistol(boolean weaponObtained) {
        if (weaponObtained) {
            return getImage("/weapons/hotbar/pistol.png");
        } else {
            return getImage("/weapons/hotbar/pistolDark.png");
        }
    }

    /**
     * Load image for the machine gun
     *
     * @return BufferedImage for the machine gun
     */
    public static BufferedImage machineGun(boolean weaponObtained) {
        if (weaponObtained) {
            return getImage("/weapons/hotbar/machineGun.png");
        } else {
            return getImage("/weapons/hotbar/machineGunDark.png");
        }
    }

    /**
     * Load image for the shotgun
     *
     * @return BufferedImage for the shotgun
     */
    public static BufferedImage shotgun(boolean weaponObtained) {
        if (weaponObtained) {
            return getImage("/weapons/hotbar/shotgun.png");
        } else {
            return getImage("/weapons/hotbar/shotgunDark.png");
        }
    }

    /**
     * Load image for the converter
     *
     * @return BufferedImage for the converter
     */
    public static BufferedImage converter(boolean weaponObtained) {
        if (weaponObtained) {
            return getImage("/weapons/hotbar/converter.png");
        } else {
            return getImage("/weapons/hotbar/converterDark.png");
        }
    }

    /**
     * Load image for the uzi
     *
     * @return BufferedImage for the uzi
     */
    public static BufferedImage uzi(boolean weaponObtained) {
        if (weaponObtained) {
            return getImage("/weapons/hotbar/uzi.png");
        } else {
            return getImage("/weapons/hotbar/uziDark.png");
        }
    }


    /**
     * Load image for the game icon
     *
     * @return BufferedImage for the icon
     */
    public static BufferedImage iconImage() {
        return getImage("icon.png");
    }

    /**
     * Import Tradewinds font into the game
     *
     * @return Imported Font
     */
    public static Font getTradewindsFont() {
        Font tradeWinds = null;

        try {
            File font_file = new File(RES_PATH + "tradewinds.ttf");
            tradeWinds = Font.createFont(Font.TRUETYPE_FONT, font_file);
            GraphicsEnvironment genv = GraphicsEnvironment.getLocalGraphicsEnvironment();
            genv.registerFont(tradeWinds);
        } catch (FontFormatException | IOException e) {
            System.err.println("Error importing font: " + e.getMessage());
        }

        return tradeWinds;
    }

}