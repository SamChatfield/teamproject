package game.client;

import game.ResourceLoader;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Renders the game menus on screen (main menu and options/help screen)
 */
public class MenuRenderer {

	private BufferStrategy bufferStrategy;
	private int gameH;
	private int gameW;
	private Font tradeWinds;

	// Buttons
	public Rectangle playButton;
	public Rectangle helpOptionsButton;
	public Rectangle sfxButton;
	public Rectangle musicButton;
	public Rectangle returnButton = new Rectangle((Client.GAME_DIMENSION.width / 2) - 75, (Client.GAME_DIMENSION.height/10) * 9, 150, 50);

	/**
	 * Creates a new MenuRenderer, initialising fonts and sizes
	 * @param bufferStrategy BufferStrategy object
	 */
	public MenuRenderer(BufferStrategy bufferStrategy) {
		this.bufferStrategy = bufferStrategy;
		gameH = Client.GAME_DIMENSION.height;
		gameW = Client.GAME_DIMENSION.width;

		tradeWinds = ResourceLoader.getTradewindsFont();
	}

	/**
	 * Render the help and options section of the menu
	 */
	public void renderHelpOptions() {

		// Create Graphics 2D Object
		Graphics2D g2d = (Graphics2D) bufferStrategy.getDrawGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		// Fill background
		g2d.setColor(Color.BLACK);
		g2d.fillRect(0, 0, Client.GAME_DIMENSION.width, Client.GAME_DIMENSION.height);

		// Set font
		g2d.setFont(tradeWinds.deriveFont(50f));
		g2d.setColor(Color.WHITE);

		// Display title of page
		int width = g2d.getFontMetrics().stringWidth("Help & Options");
		g2d.drawString("Help & Options", (Client.GAME_DIMENSION.width / 2) - width / 2, Client.GAME_DIMENSION.height / 7);

		g2d.setFont(tradeWinds.deriveFont(20f));

		// Setup buttons
		ArrayList<Rectangle> helpButtons = new ArrayList<Rectangle>();

		musicButton = new Rectangle(50, 480, 170, 60);
		sfxButton = new Rectangle(this.gameW - (200 + 50), 480, 170, 60);

		helpButtons.add(returnButton);
		helpButtons.add(sfxButton);
		helpButtons.add(musicButton);

		ArrayList<String> helpButtonStrings = new ArrayList<String>();
		helpButtonStrings.add("Return");

		//// Buttons to turn sound on and off
		if(Sound.sfxPlayback) {
			g2d.setColor(new Color(0, 100, 0));
			helpButtonStrings.add("SFX On");
		} else {
			g2d.setColor(new Color(102, 0, 0));
			helpButtonStrings.add("SFX Off");
		}
		g2d.fill(sfxButton);

		if(Sound.musicPlayback) {
			g2d.setColor(new Color(0, 100, 0));
			helpButtonStrings.add("Music On");
		} else {
			g2d.setColor(new Color(102, 0, 0));
			helpButtonStrings.add("Music Off");
		}
		g2d.fill(musicButton);

		int counter = 0;
		for(Rectangle helpButton : helpButtons) {
			g2d.setFont(tradeWinds.deriveFont(25f));
			g2d.setColor(Color.WHITE);
			int helpButton_width = g2d.getFontMetrics().stringWidth(helpButtonStrings.get(counter));
			int helpButton_cenw = (int) ((helpButton.getWidth() - helpButton_width) / 2);
			int helpButton_cenh = (int) ((helpButton.getHeight() / 2));
			g2d.drawString(helpButtonStrings.get(counter), helpButton.x + helpButton_cenw, helpButton.y + 5 + helpButton_cenh);
			counter++;    
		}

		// Strings on page
		g2d.setFont(tradeWinds.deriveFont(20f));
		ArrayList<String> helpStrings = new ArrayList<String>();
		helpStrings.add("Press W,A,S,D to move in the corresponding 4 directions");
		helpStrings.add("Use the mouse pointer to aim");
		helpStrings.add("Left click to shoot in the direction of the player");
		helpStrings.add("Use number keys 1-5 to use the corresponding item");
		helpStrings.add("O = Music and SFX on / P = Music and SFX off");
		int helpX = 175;

		for(String helpString : helpStrings) {
			int helpStringWidth = g2d.getFontMetrics().stringWidth(helpString);
			g2d.drawString(helpString, (Client.GAME_DIMENSION.width / 2) - helpStringWidth / 2, helpX);
			helpX += 60;
		}

		// Clean up and flip the buffer
		g2d.dispose();
		bufferStrategy.show();
	}

	/**
	 * Render the main menu
	 */
	public void renderMenu() {

		// Create Graphics 2D Object
		Graphics2D g2d = (Graphics2D) bufferStrategy.getDrawGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		// Read background image
		BufferedImage background = null;
		try {
			background = ImageIO.read(new File("src/game/res/background.png"));
		} catch (IOException e) {
			System.err.println("Error reading background image: " + e.getMessage());
		}

		// Setup buttons
		ArrayList<Rectangle> buttons = new ArrayList<Rectangle>();

		playButton = new Rectangle((Client.GAME_DIMENSION.width / 2) - 85, (this.gameH/12) * 8, 170, 50);
		helpOptionsButton = new Rectangle(this.gameW - (170 + 20), 600, 170, 40);

		buttons.add(playButton);
		buttons.add(helpOptionsButton);

		// Fill background and apply background image
		g2d.setColor(Color.BLACK);
		g2d.fillRect(0, 0, this.gameW, this.gameH);
		g2d.drawImage(background, 0, 0, this.gameH, this.gameW, null);

		// Set font and display game name
		g2d.setFont(tradeWinds.deriveFont(70f));
		String gameName = "Outbreak";
		int gameNameWidth = g2d.getFontMetrics().stringWidth(gameName);
		g2d.setColor(new Color(0, 100, 0));
		g2d.drawString(gameName, (Client.GAME_DIMENSION.width / 2) - gameNameWidth / 2, Client.GAME_DIMENSION.height / 8);

		// Display buttons
		g2d.setFont(tradeWinds.deriveFont(24f));
		ArrayList<String> buttonStrings = new ArrayList<String>();
		buttonStrings.add("Play");
		buttonStrings.add("Options & Help");

		// Goes through and creates all the buttons and the button labels
		int counter = 0;
		for(String buttonString : buttonStrings) {
			Rectangle button = buttons.get(counter);

			g2d.setColor(new Color(0, 0, 0, 175));
			g2d.fill(button);

			int width_button = g2d.getFontMetrics().stringWidth(buttonString);
			int cenw_button = (int) ((button.getWidth() - width_button) / 2);
			int cenh_button = (int) ((button.getHeight() / 2));

			g2d.setColor(new Color(0, 100, 0));
			g2d.drawString(buttonString, button.x + cenw_button, button.y + 5 + cenh_button);

			counter++;
		}

		// Clean up and flip the buffer
		g2d.dispose();
		bufferStrategy.show();
	}
}