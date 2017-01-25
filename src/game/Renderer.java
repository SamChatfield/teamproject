package game;

import java.awt.*;
import java.awt.image.BufferStrategy;

/**
 * Created by Sam on 20/01/2017.
 */
public class Renderer {

    private BufferStrategy bufferStrategy;
    private Entity player;
    private Entity[] zombies;

    public Renderer(BufferStrategy bufferStrategy, Entity player) {
        this.bufferStrategy = bufferStrategy;
        this.player = player;
    }

    public void render(float interpolation) {
        Graphics2D g2d = (Graphics2D) bufferStrategy.getDrawGraphics();
        g2d.setColor(g2d.getBackground());
        g2d.fillRect(0, 0, Game.GAME_DIMENSION.width, Game.GAME_DIMENSION.height);

        g2d.drawRect(50, 50, 100, 100);

//        g2d.drawImage(player.getImage(), player.x(), player.y());
        player.draw(g2d, interpolation);

        // Clean up and flip buffer
        g2d.dispose();
        bufferStrategy.show();
    }

    public void render() {
        Graphics2D g2d = (Graphics2D) bufferStrategy.getDrawGraphics();
        g2d.setColor(g2d.getBackground());
        g2d.fillRect(0, 0, Game.GAME_DIMENSION.width, Game.GAME_DIMENSION.height);

        g2d.drawRect(50, 50, 100, 100);

//        g2d.drawImage(player.getImage(), player.x(), player.y());
        player.draw(g2d);

        // Clean up and flip buffer
        g2d.dispose();
        bufferStrategy.show();
    }

}
