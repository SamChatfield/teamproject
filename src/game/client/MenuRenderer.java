package game.client;

import game.client.Client;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferStrategy;

public class MenuRenderer {
	
	private BufferStrategy bufferStrategy;
	private Font font1;
	private int gameH;
	private int gameW;
	 
	// Buttons
	public Rectangle playButton = new Rectangle((Client.GAME_DIMENSION.width / 2) - 75, (Client.GAME_DIMENSION.height/10) * 3, 150, 50);
	public Rectangle helpButton = new Rectangle((Client.GAME_DIMENSION.width / 2) - 75, (Client.GAME_DIMENSION.height/10) * 5, 150, 50);
	public Rectangle optionsButton = new Rectangle((Client.GAME_DIMENSION.width / 2) - 75, (Client.GAME_DIMENSION.height/10) * 7, 150, 50);
	
	public Rectangle returnButton = new Rectangle((Client.GAME_DIMENSION.width / 2) - 75, (Client.GAME_DIMENSION.height/10) * 7, 150, 50);
	
	/**
	 * Creates a new MenuRenderer, initialising fonts and sizes
	 * @param bufferStrategy BufferStrategy object
	 */
	public MenuRenderer(BufferStrategy bufferStrategy) {
		this.bufferStrategy = bufferStrategy;
		font1 = new Font("Comic Sans MS", Font.BOLD, 30);
		gameH = Client.GAME_DIMENSION.height;
		gameW = Client.GAME_DIMENSION.width;
	}
	

	/**
	 * Render the options page of the menu
	 */
	public void renderOptions() {
		
		// Create Graphics 2D Object
		Graphics2D g2d = (Graphics2D) bufferStrategy.getDrawGraphics();
    	g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    	// Fill background
        g2d.setColor(Color.BLUE);
        g2d.fillRect(0, 0, Client.GAME_DIMENSION.width, Client.GAME_DIMENSION.height);
        
        // Set font
        Font title = new Font("Comic Sans MS", Font.BOLD, 40);
		g2d.setFont(title);
		
		// Display /filler/ text
		g2d.setColor(Color.RED);
		String text = "Options";
		int width = g2d.getFontMetrics().stringWidth(text);
		g2d.drawString(text, (Client.GAME_DIMENSION.width / 2) - width / 2, Client.GAME_DIMENSION.height / 7);
		
		// Display buttons
		g2d.drawString("Return", returnButton.x + 20, returnButton.y + 30);
		g2d.draw(returnButton);
		
        // Clean up and flip the buffer
        g2d.dispose();
        bufferStrategy.show();
		
	}
	
	/**
	 * Render the help section of the menu
	 */
	public void renderHelp() {
		
		// Create Graphics 2D Object
		Graphics2D g2d = (Graphics2D) bufferStrategy.getDrawGraphics();
    	g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    	// Fill background
        g2d.setColor(Color.BLUE);
        g2d.fillRect(0, 0, Client.GAME_DIMENSION.width, Client.GAME_DIMENSION.height);
        
        // Set font
        Font title = new Font("Comic Sans MS", Font.BOLD, 40);
		g2d.setFont(title);
		
		// Display /filler/ text
		g2d.setColor(Color.RED);
		String text = "Help";
		int width = g2d.getFontMetrics().stringWidth(text);
		g2d.drawString(text, (Client.GAME_DIMENSION.width / 2) - width / 2, Client.GAME_DIMENSION.height / 7);
		
		// Display buttons
		g2d.drawString("Return", returnButton.x + 20, returnButton.y + 30);
		g2d.draw(returnButton);
		
        // Clean up and flip the buffer
        g2d.dispose();
        bufferStrategy.show();
		
	}
	
	public void renderMenu() {
		
		// Create Graphics 2D Object
		Graphics2D g2d = (Graphics2D) bufferStrategy.getDrawGraphics();
    	g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    	// Fill background
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, Client.GAME_DIMENSION.width, Client.GAME_DIMENSION.height);
        
        // Set font
		Font title = new Font("Comic Sans MS", Font.BOLD, 50);
		g2d.setFont(title);
		String gameName = "Capture the Zom.biz";
		int width = g2d.getFontMetrics().stringWidth(gameName);
		
		// Display game name
		g2d.setColor(Color.GREEN);
		g2d.drawString(gameName, (Client.GAME_DIMENSION.width / 2) - width / 2, Client.GAME_DIMENSION.height / 5);
		
		// Buttons
		
        // Create font
		Font font_buttons = new Font("Copperplate", Font.PLAIN, 30);
		g2d.setFont(font_buttons);
		g2d.setColor(Color.WHITE);
		
		// Play Button
		g2d.drawString("Play", playButton.x + 20, playButton.y + 30);
		g2d.draw(playButton);
		
		// Help button
		g2d.drawString("Help", helpButton.x + 20, helpButton.y + 30);
		g2d.draw(helpButton);
		
		// Settings button
		g2d.drawString("Options", optionsButton.x + 20, optionsButton.y + 30);
		g2d.draw(optionsButton);
			    
        // Clean up and flip the buffer
        g2d.dispose();
        bufferStrategy.show();
		
	}
}
