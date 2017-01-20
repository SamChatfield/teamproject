package game;

import java.awt.image.BufferedImage;

/**
 * Created by Sam on 20/01/2017.
 */
public class Zombie extends Entity {

    private Team team;

    public Zombie(float x, float y, BufferedImage image) {
        super(x, y, image);
        this.team = Team.WILD;
    }

    public void convert() {
        team = Team.PLAYER;
    }

}
