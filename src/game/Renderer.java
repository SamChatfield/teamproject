package game;

import game.map.MapData;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

/**
 * Created by Sam on 20/01/2017.
 */
public class Renderer {

    private BufferStrategy bufferStrategy;
    private MapData mapData;
    private Player player;
    private ArrayList<Zombie> zombies;

    public Renderer(BufferStrategy bufferStrategy, MapData mapData, Player player, ArrayList<Zombie> zombies) {
        this.bufferStrategy = bufferStrategy;
        this.mapData = mapData;
        this.player = player;
        this.zombies = zombies;
    }

    public void render() {
        // Set up the graphics instance for the current back buffer
        Graphics2D g2d = (Graphics2D) bufferStrategy.getDrawGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Clear the screen
        g2d.setColor(g2d.getBackground());
        g2d.fillRect(0, 0, Game.GAME_DIMENSION.width, Game.GAME_DIMENSION.height);

        g2d.setColor(Color.BLACK);

        player.draw(g2d, mapData);

        for (Bullet b : player.getBullets()) {
            b.draw(g2d);
        }

        for (Zombie z : zombies) {
            z.draw(g2d, mapData, player);
        }

        // Clean up and flip the buffer
        g2d.dispose();
        bufferStrategy.show();
    }

}
