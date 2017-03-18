package game.client;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import game.Bullet;
import game.CollisionBox;
import game.ResourceLoader;
import game.map.MapData;
import game.map.Tile;
import game.util.DataPacket;
import game.util.EndState;

/**
 * Renders the game on screen
 */
public class Renderer {

	private BufferStrategy bufferStrategy;
	private Player player;
	private int gameH, gameW;
	private Font tradeWinds;
	private ClientGameState state;
	private boolean showCollBox = false;

	private BufferedImage lighting;

	public Rectangle menuButton;
	public Rectangle exitButton;
	
	private Color fadedWhite = new Color(255,255,255,190);

	/**
	 * Create a new Renderer
	 * @param bufferStrategy Strategy to use to draw in a buffer
	 * @param state The current ClientGameState
	 */
	Renderer(BufferStrategy bufferStrategy, ClientGameState state) {
		this.bufferStrategy = bufferStrategy;
		this.state = state;
		this.gameH = Client.GAME_DIMENSION.height;
		this.gameW = Client.GAME_DIMENSION.width;

		tradeWinds = ResourceLoader.getTradewindsFont();
	}

	/**
	 * Run the render loop to render everything
	 */    
	public void render() {
		this.player = state.getPlayer(); // Get the player object now (if render is called, the game definitely knows the state of the game)

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


		// Draw the player
		player.draw(g2d);

		// Draw relative to other player
		if(state.getOtherPlayer() != null){
			state.getOtherPlayer().drawRelativeToOtherPlayer(g2d,player);
		}

		// Draw bullets
		for (Bullet b : state.getBullets()) {
			if(b.active) {
				if(b.getUsername().equals(player.getUsername())){
					b.draw(g2d);
				} else{
					drawBullet(g2d, player, b.getX(), b.getY(), b.getFacingAngle());

				}
			}
		}

		// Draw the zombies
		for (DataPacket z : zombiePackets) {
			drawZombie(g2d, player, z);
		}

		// Draw lighting
		drawLighting(g2d);

		// Health bar
		float healthPercentage = (player.getHealth() / 50.0f) * 100;

		g2d.setFont(tradeWinds.deriveFont(15f));

		g2d.setColor(new Color(128, 128, 128, 50));
		Rectangle healthBarBackground = new Rectangle(10, 10, 200, 20);
		g2d.fill(healthBarBackground);

		// Changes red below 10%
		g2d.setColor(new Color(0,100,0, 200));
		if(healthPercentage < 10) {
			g2d.setColor(new Color(102, 0, 0, 200));
		}
		Rectangle healthBarFill = new Rectangle(10, 10, player.getHealth() * 4, 20);
		g2d.fill(healthBarFill);

		// Health bar text display
		g2d.setColor(new Color(255,255,255, 200));
		String healthFormat = String.format("%.2f", healthPercentage);
		g2d.drawString("Health: " + healthFormat + "%", 15, (int) (healthBarBackground.getY() + healthBarBackground.getHeight() + 20));

		// Items box
		g2d.setColor(fadedWhite);

		int xCoord = 10;
		for(int i = 0; i < 5; i++) {
			Rectangle itemsBox = new Rectangle(xCoord, 590, 40, 40);
			g2d.draw(itemsBox);

			g2d.drawString(Integer.toString(i+1), xCoord+4, 628);
			xCoord+= 40;
		}

		// Display time remaining
		g2d.setFont(tradeWinds.deriveFont(35f));
		g2d.setColor(fadedWhite);
		String remainingTime = String.format("Time: %d:%02d", (timeRemaining/60), (timeRemaining % 60));
		g2d.drawString(remainingTime, gameW - (g2d.getFontMetrics().stringWidth(remainingTime) + 20), 40);

		// Display number of converted zombies
		int playerZombies = player.getNumConvertedZombies();
		int opponentZombies = state.getOtherPlayer().getNumConvertedZombies();
		int totalZombies = zombiePackets.size();
		System.out.println(playerZombies + "/" + totalZombies);

		// Zombie counts
		ArrayList<Integer> zombieCounts = new ArrayList<Integer>();
		zombieCounts.add(totalZombies - (opponentZombies + playerZombies));
		zombieCounts.add(playerZombies);
		zombieCounts.add(opponentZombies);

		// Zombie count box labels
		ArrayList<String> zombieCountLabels = new ArrayList<String>();
		zombieCountLabels.add("Wild");
		zombieCountLabels.add("You");
		zombieCountLabels.add("Opponent");

		// Colours for zombie count boxes
		ArrayList<Color> zombieCountColours = new ArrayList<Color>();
		zombieCountColours.add(new Color(191, 90, 30, 120));
		zombieCountColours.add(new Color(3, 49, 134, 120));
		zombieCountColours.add(new Color(196, 0, 5, 120));

		g2d.setColor(fadedWhite);
		g2d.setFont(tradeWinds.deriveFont(10f));
		g2d.drawString("Zombie Counts: ", 535, 520);

		int counter = 0;
		int countsY = 530;
		for(int count : zombieCounts) {
			Rectangle zombieCountBox = new Rectangle(580, countsY, 40, 30);
			g2d.setColor(zombieCountColours.get(counter));
			g2d.fill(zombieCountBox);

			g2d.setColor(fadedWhite);
			g2d.setFont(tradeWinds.deriveFont(18f));
			String zombieCount = "" + count;
			g2d.drawString(zombieCount, (int) (zombieCountBox.getX() + (g2d.getFontMetrics().stringWidth(zombieCount) / 2)),(int) (zombieCountBox.getY() + (zombieCountBox.getHeight() / 2) + 7));

			g2d.setFont(tradeWinds.deriveFont(10f));
			g2d.drawString(zombieCountLabels.get(counter), (int)(zombieCountBox.getX() - (g2d.getFontMetrics().stringWidth(zombieCountLabels.get(counter)) + 5)), (int) (3 +  zombieCountBox.getY() + (zombieCountBox.getHeight() / 2)));
			countsY += 30;
			counter++;
		}

		// Clean up and flip the buffer
		g2d.dispose();
		bufferStrategy.show();
	}

	/**
	 * Display the game over screen
	 */
	public void renderGameOver(EndState endState) {

		// Set up the graphics instance for the current back buffer
		Graphics2D g2d = (Graphics2D) bufferStrategy.getDrawGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		// Clear the screen
		g2d.setColor(Color.BLACK);
		g2d.fillRect(0, 0, Client.GAME_DIMENSION.width, Client.GAME_DIMENSION.height);

		// Strings for game over screen
		ArrayList<String> gameOverStrings = new ArrayList<String>();
		String gameWinner = "......";
		String gameOverReason = "......";
		String player1Zombies = "0";
		String player2Zombies = "0";
		String wildZombies = "0";

		endState.getPlayer1().getNumConvertedZombies();
		
		try {
			gameWinner = endState.getWinnerName();
			if(endState.getReason() == endState.getReason().PLAYER_DIED) {
				gameOverReason = "Player died";
			}
			else if(endState.getReason() == endState.getReason().TIME_EXPIRED) {
				gameOverReason = "Time over";
			}
			
			player1Zombies = "" + endState.getPlayer1().getNumConvertedZombies();
			player2Zombies = "" + endState.getPlayer2().getNumConvertedZombies();
		}
		catch(NullPointerException e) {
			System.out.println(e.getMessage());
		}

		gameOverStrings.add("Winner of game: " + gameWinner);
		gameOverStrings.add("Reason: " + gameOverReason);
		gameOverStrings.add("Player 1 zombie count: " + player1Zombies);
		gameOverStrings.add("Player 2 zombie count: " + player2Zombies);

		// Display strings on screen
		g2d.setColor(Color.RED);
		g2d.setFont(tradeWinds.deriveFont(25f));
		int y = 200;
		int counter = 0;
		for(String gameOverString : gameOverStrings) {
			int stringWidth = g2d.getFontMetrics().stringWidth(gameOverString);
			g2d.drawString(gameOverString, (this.gameW /2) - (stringWidth /2), y);
			if(counter == 1) {
				y += 70;
			}
			else {
				y+= 40;
			}
			counter++;
		} 

		// Apply text
		g2d.setColor(new Color(102, 0, 0));
		g2d.setFont(tradeWinds.deriveFont(60f));
		String gameOver = "GAME OVER";
		int gameOverWidth = g2d.getFontMetrics().stringWidth(gameOver);
		g2d.drawString(gameOver, (this.gameW / 2) - gameOverWidth / 2, this.gameH / 5);

		// Create buttons and display them and text on screen
		g2d.setFont(tradeWinds.deriveFont(25f));

		ArrayList<Rectangle> gameOverButtons = new ArrayList<Rectangle>();
		menuButton = new Rectangle((this.gameW / 2) - 110, (this.gameH/15) * 10, 220, 50);
		exitButton = new Rectangle((this.gameW / 2) - 110, (this.gameH/15) * 12, 220, 50);
		gameOverButtons.add(menuButton);
		gameOverButtons.add(exitButton);

		ArrayList<String> gameOverButtonStrings = new ArrayList<String>();
		gameOverButtonStrings.add("Return to menu");
		gameOverButtonStrings.add("Exit");

		counter = 0;
		for(String gameOverButtonString : gameOverButtonStrings) {
			Rectangle button = gameOverButtons.get(counter);

			g2d.setColor(new Color(102, 0, 0, 255));
			g2d.fill(button);

			int width_button = g2d.getFontMetrics().stringWidth(gameOverButtonString);
			int cenw_button = (int) ((button.getWidth() - width_button) / 2);
			int cenh_button = (int) ((button.getHeight() / 2));

			g2d.setColor(Color.WHITE);
			g2d.drawString(gameOverButtonString, button.x + cenw_button, button.y + 5 + cenh_button);

			counter++;
		}

		// Clean up and flip the buffer
		g2d.dispose();
		bufferStrategy.show();
	}

	/**
	 * Draw the lighting effect
	 * @param g2d Graphics2D object
	 */
	public void drawLighting(Graphics2D g2d) {
		try {
			lighting = ImageIO.read(new File("src/game/res/spotlight.png"));
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		g2d.drawImage(lighting, 0, 0, null);
	}

	/**
	 * Draw the map on screen
	 * @param g2d Graphics2D object
	 * @param mapData MapData to draw
	 * @param player Player object
	 */
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

	/**
	 * Draw zombies on the screen
	 * @param g2d Graphics2D object
	 * @param player Player object
	 * @param z Zombies DataPacket
	 */
	private void drawZombie(Graphics2D g2d, Player player, DataPacket z) {

		// Width and height of the entity sprite
		int w = Client.wildZombieImage.getWidth();
		int h = Client.wildZombieImage.getHeight();

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
			g2d.drawImage(Client.opponentZombieImage, drawX, drawY, null);
		}
		else {
			g2d.drawImage(Client.wildZombieImage, drawX, drawY, null);
		}
		g2d.setTransform(at);
	}

	/**
	 * DEBUG METHOD: Set whether to show collision boxes
	 * @param showCollBox Boolean to set
	 */
	public void setShowCollBox(boolean showCollBox) {
		this.showCollBox = showCollBox;
		player.setShowCollBox(showCollBox);
	}

	/**
	 * Draw bullets on the screen
	 * @param g2d Graphics2D object
	 * @param player Player object
	 * @param x X coordinate of bullet
	 * @param y Y coordinate of bullet 
	 * @param facingAngle Angle the bullet is facing
	 */
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