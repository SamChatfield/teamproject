package game;

import java.awt.*;
import java.awt.image.BufferStrategy;

/**
 * Created by Sam on 20/01/2017.
 */
public class Renderer {

    private BufferStrategy bufferStrategy;
    private Map map;
    private Entity player;
    private Zombie[] zombies;

    public Renderer(BufferStrategy bufferStrategy, Map map, Entity player, Zombie[] zombies) {
        this.bufferStrategy = bufferStrategy;
        this.map = map;
        this.player = player;
        this.zombies = zombies;
    }

    public void render() {
        // Set up the graphics instance for the current back buffer
        Graphics2D g2d = (Graphics2D) bufferStrategy.getDrawGraphics();

        // Clear the screen
        g2d.setColor(g2d.getBackground());
        g2d.fillRect(0, 0, Game.GAME_DIMENSION.width, Game.GAME_DIMENSION.height);

        g2d.setColor(Color.BLACK);

        player.draw(g2d, map);

        for (Zombie z : zombies) {
            z.draw(g2d, map);
        }

        // Clean up and flip the buffer
        g2d.dispose();
        bufferStrategy.show();
    }

}
