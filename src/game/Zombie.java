package game;

import java.awt.image.BufferedImage;

/**
 * Created by Sam on 20/01/2017.
 */
public class Zombie extends Entity {

    private State state;

    private enum State {
        WILD, PLAYER, OPPONENT;
    }

    public Zombie(float x, float y, BufferedImage image) {
        super(x, y, image);
        this.state = State.WILD;
    }

    public void convert() {
        state = State.PLAYER;
    }

}
