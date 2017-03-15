package game.client;

import game.Bullet;
import game.CollisionBox;
import game.map.MapData;
import game.map.Tile;
import game.util.DataPacket;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Sam on 20/01/2017.
 */
public class Renderer {

    private BufferStrategy bufferStrategy;
    private Player player;
    private int gameH, gameW;
    private int maxHealth;
    private Font tradeWinds;
    private ClientGameState state;
    private boolean showCollBox = false;

    private BufferedImage lighting;
    
    public Rectangle menuButton = new Rectangle((Client.GAME_DIMENSION.width / 2) - 75, (Client.GAME_DIMENSION.height/10) * 4, 150, 50);
    public Rectangle exitButton = new Rectangle((Client.GAME_DIMENSION.width / 2) - 75, (Client.GAME_DIMENSION.height/10) * 6, 150, 50);
    
    Renderer(BufferStrategy bufferStrategy, ClientGameState state) {
        this.bufferStrategy = bufferStrategy;
       // this.mapData = inter.getMapData();
        this.state = state;
        this.gameH = Client.GAME_DIMENSION.height;
        this.gameW = Client.GAME_DIMENSION.width;
        
        //Breaking DRY. Could be put in a separate class
		File font_file = new File("src/game/res/tradewinds.ttf");
		try {
			tradeWinds = Font.createFont(Font.TRUETYPE_FONT, font_file);
			GraphicsEnvironment genv = GraphicsEnvironment.getLocalGraphicsEnvironment();
			genv.registerFont(tradeWinds);
		} catch (FontFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
       
    
    public void render() {
        this.player = state.getPlayer(); // get the player object now (if render is called, the game definitely knows the state of the game)

        int timeRemaining = state.getTimeRemaining();
        ArrayList<DataPacket> zombiePackets = state.getZombieDataPackets();
        MapData mapData = state.getMapData();

        // Set up the graphics instance for the current back buffer
        Graphics2D g2d = (Graphics2D) bufferStrategy.getDrawGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Clear the screen
        g2d.setColor(g2d.getBackground());
        g2d.fillRect(0, 0, gameH, gameW);

        g2d.setColor(Color.BLACK);

        // Draw the map
        drawMap(g2d, mapData, player);

        player.draw(g2d);

        if(state.getOtherPlayer() != null){
            state.getOtherPlayer().drawRelativeToOtherPlayer(g2d,player);
        }
        //System.out.println(state.getBullets().size());
        for (Bullet b : state.getBullets()) {
            if(b.active) {
                if(b.getUsername().equals(player.getUsername())){
                    b.draw(g2d);
                }else{
                    drawBullet(g2d, player, b.getX(), b.getY(), b.getFacingAngle());

                }
            }
        }

        // Draw the zombies
        for (DataPacket z : zombiePackets) {
            drawZombie(g2d, player, z);
        }

        //lighting
		drawLighting(g2d);

		// Health bar
		Font health = new Font("Arial", Font.BOLD, 10);
		g2d.setFont(health);
		
		g2d.setColor(new Color(0, 0, 0, 120));
		Rectangle healthBar2 = new Rectangle(10, 10, 200, 20);
		g2d.fill(healthBar2);
		
		g2d.setColor(Color.GREEN);
		//System.out.println(player.health);
		//System.out.println(percentage);
		float percentage = player.getHealth() / 50.0f;
		Rectangle healthBarFill = new Rectangle(10, 10, player.getHealth() * 4, 20);
		g2d.fill(healthBarFill);
		g2d.setColor(Color.BLACK);
		Rectangle healthBar = new Rectangle(10,10,200,20);
		g2d.draw(healthBar);
		g2d.setColor(Color.BLACK);
		String healthFormat = String.format("%.2f", percentage * 100);
		g2d.drawString("Health: " + healthFormat + "%", 15, 25);
		
		// Display time remaining
		Font hud = new Font("Arial", Font.BOLD, 15);
		g2d.setFont(hud);
		g2d.setColor(new Color(255, 255, 255, 200));
		//g2d.setColor(Color.WHITE);
		String remainingTime = String.format("Time Remaining - %d:%02d", (timeRemaining/60), (timeRemaining % 60));
		g2d.drawString(remainingTime, gameW - 170, 20);
		
		// Display number of converted zombies
		g2d.drawString("Converted zombies: " + player.getNumConvertedZombies() + "/" + zombiePackets.size() , 450, 630);
		
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
        g2d.fillRect(0, 0, Client.GAME_DIMENSION.width, Client.GAME_DIMENSION.height);

        // Apply text
        g2d.setColor(Color.RED);
		g2d.setFont(tradeWinds.deriveFont(50f));
		String text = "GAME OVER";
		int twidth = g2d.getFontMetrics().stringWidth(text);
		g2d.drawString(text, (Client.GAME_DIMENSION.width / 2) - twidth / 2, Client.GAME_DIMENSION.height / 5);

		g2d.setFont(tradeWinds.deriveFont(30f));
		
		// Play Button
		int width_menu = g2d.getFontMetrics().stringWidth("Menu");
		int cenw_menu = (int) ((menuButton.getWidth() - width_menu) / 2);
		int cenh_menu = (int) ((menuButton.getHeight() / 2));
		g2d.drawString("Menu", menuButton.x + cenw_menu, menuButton.y + 5 + cenh_menu);
		g2d.draw(menuButton);
		
		int width_exit = g2d.getFontMetrics().stringWidth("Exit");
		int cenw_exit = (int) ((exitButton.getWidth() - width_exit) /2);
		int cenh_exit = (int) ((exitButton.getHeight() /2));
		g2d.drawString("Exit", exitButton.x + cenw_exit, exitButton.y + 5 + cenh_exit);
		g2d.draw(exitButton);
		
        // Clean up and flip the buffer
        g2d.dispose();
        bufferStrategy.show();
    	
    }

    public void drawLighting(Graphics2D g2d) {
		try {
			lighting = ImageIO.read(new File("src/game/res/spotlight.png"));
		} catch (IOException ex) {
			ex.printStackTrace();
		}
    	g2d.drawImage(lighting, 0, 0, null);
	}

    public void drawMap(Graphics2D g2d, MapData mapData, Player player) {
        Tile[][] map = mapData.getMap();
        int width = mapData.getWidth();
        int height = mapData.getHeight();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Tile here = map[x][y];
                Point drawPoint = player.relativeDrawPoint(here.getX(), here.getY(), Client.TILE_SIZE, Client.TILE_SIZE);
                g2d.drawImage(here.getType().getImage(), drawPoint.x, drawPoint.y, null);
            }
        }


    }

    private void drawZombie(Graphics2D g2d, Player player, DataPacket z) {
        // Width and height of the entity sprite
        int w = Client.zombieImage.getWidth();
        int h = Client.zombieImage.getHeight();

        Point drawPoint = player.relativeDrawPoint(z.getX(), z.getY(), w, h);
        int drawX = drawPoint.x;
        int drawY = drawPoint.y;

        g2d.setColor(Color.GREEN);
        Rectangle healthBarFill = new Rectangle(drawX, drawY + 50, z.getHealth(), 2);
        g2d.fill(healthBarFill);
        g2d.setColor(Color.BLACK);

        if (showCollBox) {
            g2d.setColor(Color.BLUE);
            g2d.draw(CollisionBox.collBoxRectFromData(z, player));
            g2d.setColor(Color.BLACK);
        }

        AffineTransform at = g2d.getTransform();
        g2d.rotate(z.getFacingAngle(), drawX + w / 2, drawY + h / 2);

        if(z.getState() == DataPacket.State.PLAYER && player.getUsername().equals(z.getUsername())) {
            g2d.drawImage(Client.playerZombieImage, drawX, drawY, null);
        }
        else if(z.getState() == DataPacket.State.PLAYER) {
            // Change this later
            g2d.drawImage(Client.zombieImage, drawX, drawY, null);
        }
        else {
            g2d.drawImage(Client.zombieImage, drawX, drawY, null);
        }
        g2d.setTransform(at);
    }

    public void setShowCollBox(boolean showCollBox) {
        this.showCollBox = showCollBox;
        player.setShowCollBox(showCollBox);
    }
    
    
    
    private void drawBullet(Graphics2D g2d, Player player, float x, float y, double facingAngle) {
        int w = Client.bulletImage.getWidth();
        int h = Client.bulletImage.getHeight();

        Point drawPoint = player.relativeDrawPoint(x, y, w, h);
        int drawX = drawPoint.x;
        int drawY = drawPoint.y;

        AffineTransform at = g2d.getTransform();
        g2d.rotate(facingAngle, drawX, drawY);
        g2d.drawImage(Client.bulletImage, drawX, drawY, null);
        g2d.setTransform(at);
    }
    
    
    

}
