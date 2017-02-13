package game;

import game.map.MapData;
import game.map.Tile;

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
    private int gameH, gameW;
    
Renderer(BufferStrategy bufferStrategy, MapData mapData, Player player, ArrayList<Zombie> zombies) {
        this.bufferStrategy = bufferStrategy;
        this.mapData = mapData;
        this.player = player;
        this.zombies = zombies;
        this.gameH = Game.GAME_DIMENSION.height;
        this.gameW = Game.GAME_DIMENSION.width;
    }
       
    
    public void render(Timer timer) {
    	int timeRemaining = timer.getTimeRemaining();
    	
        // Set up the graphics instance for the current back buffer
        Graphics2D g2d = (Graphics2D) bufferStrategy.getDrawGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Clear the screen
        g2d.setColor(g2d.getBackground());
        g2d.fillRect(0, 0, gameH, gameW);

        g2d.setColor(Color.BLACK);

        // Draw the map
        drawMap(g2d, mapData, player);

        player.draw(g2d, mapData);
        
        ArrayList<Bullet> deleteBullets = new ArrayList<Bullet>();

        for (Bullet b : player.getBullets()) {
            if(b.active) {
            	b.draw(g2d);
            }
            else {
            	deleteBullets.add(b);
            }
        }
        
        for(Bullet b : deleteBullets) {
        	player.getBullets().remove(b);
        }

        for (Zombie z : zombies) {
            z.draw(g2d, mapData, player);
        }
        
		// Health bar
		Font health = new Font("Arial", Font.BOLD, 10);
		g2d.setFont(health);
		g2d.setColor(Color.GREEN);
		Rectangle healthBarFill = new Rectangle(10, 10, 250 - (250/100) * -player.health, 20);
		g2d.fill(healthBarFill);
		g2d.setColor(Color.BLACK);
		Rectangle healthBar = new Rectangle(10,10,250,20);
		g2d.draw(healthBar);
		g2d.drawString("Health: " + player.health, 10, 40);
		
		
		// Display time remaining
		Font hud = new Font("Arial", Font.BOLD, 15);
		g2d.setFont(hud);
		g2d.setColor(Color.BLACK);
		String remainingTime = String.format("Time Remaining - %d:%02d", (timeRemaining/60), (timeRemaining % 60));
		g2d.drawString(remainingTime, gameW - 170, 20);
		

        // Clean up and flip the buffer
        g2d.dispose();
        bufferStrategy.show();
    }
    
    public void renderGameOver() {
    	
    	// Set up the graphics instance for the current back buffer
        Graphics2D g2d = (Graphics2D) bufferStrategy.getDrawGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Clear the screen
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, Game.GAME_DIMENSION.width, Game.GAME_DIMENSION.height);

        // Apply text
        g2d.setColor(Color.RED);
		Font title = new Font("Comic Sans MS", Font.BOLD, 50);
		g2d.setFont(title);
		String text = "YOU DIED";
		int twidth = g2d.getFontMetrics().stringWidth(text);
		g2d.drawString(text, (Game.GAME_DIMENSION.width / 2) - twidth / 2, Game.GAME_DIMENSION.height / 5);
		
        // Clean up and flip the buffer
        g2d.dispose();
        bufferStrategy.show();
    	
    }

    public void drawMap(Graphics2D g2d, MapData mapData, Player player) {
        Tile[][] map = mapData.getMap();
        int width = mapData.getWidth();
        int height = mapData.getHeight();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Tile here = map[x][y];
                Point drawPoint = player.relativeDrawPoint(here.getX(), here.getY(), Game.TILE_SIZE, Game.TILE_SIZE);
                g2d.drawImage(here.getType().getImage(), drawPoint.x, drawPoint.y, null);
            }
        }
    }

}
