package game.client;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class MenuRenderer {

    private BufferStrategy bufferStrategy;
    private Font font1;
    private int gameH;
    private int gameW;
    private Font tradeWinds;

    // Buttons
    public Rectangle playButton = new Rectangle((Client.GAME_DIMENSION.width / 2) - 75, (Client.GAME_DIMENSION.height/10) * 4, 150, 50);
    public Rectangle helpButton = new Rectangle((Client.GAME_DIMENSION.width / 2) - 75, (Client.GAME_DIMENSION.height/10) * 6, 150, 50);
    public Rectangle optionsButton = new Rectangle((Client.GAME_DIMENSION.width / 2) - 75, (Client.GAME_DIMENSION.height/10) * 8, 150, 50);

    public Rectangle returnButton = new Rectangle((Client.GAME_DIMENSION.width / 2) - 75, (Client.GAME_DIMENSION.height/10) * 9, 150, 50);

    /**
     * Creates a new MenuRenderer, initialising fonts and sizes
     * @param bufferStrategy BufferStrategy object
     */
    public MenuRenderer(BufferStrategy bufferStrategy) {
        this.bufferStrategy = bufferStrategy;
        font1 = new Font("Comic Sans MS", Font.BOLD, 30);
        gameH = Client.GAME_DIMENSION.height;
        gameW = Client.GAME_DIMENSION.width;

        // Import font
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


    /**
     * Render the options page of the menu
     */
    public void renderOptions() {

        // Create Graphics 2D Object
        Graphics2D g2d = (Graphics2D) bufferStrategy.getDrawGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Fill background
        g2d.setColor(Color.DARK_GRAY);
        g2d.fillRect(0, 0, Client.GAME_DIMENSION.width, Client.GAME_DIMENSION.height);

        // Set font
        g2d.setFont(tradeWinds.deriveFont(50f));
        g2d.setColor(Color.WHITE);

        // Display /filler/ text
        int width = g2d.getFontMetrics().stringWidth("Options");
        g2d.drawString("Options", (Client.GAME_DIMENSION.width / 2) - width / 2, Client.GAME_DIMENSION.height / 7);

        // Display buttons
        g2d.setFont(tradeWinds.deriveFont(25f));
        int width_return = g2d.getFontMetrics().stringWidth("Return");
        int cenw_return = (int) ((returnButton.getWidth() - width_return) / 2);
        int cenh_return = (int) ((returnButton.getHeight() / 2));
        g2d.drawString("Return", returnButton.x + cenw_return, returnButton.y + 5 + cenh_return);
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
        g2d.setColor(Color.DARK_GRAY);
        g2d.fillRect(0, 0, Client.GAME_DIMENSION.width, Client.GAME_DIMENSION.height);

        // Set font
        g2d.setFont(tradeWinds.deriveFont(50f));
        g2d.setColor(Color.WHITE);

        // Display /filler/ text
        int width = g2d.getFontMetrics().stringWidth("Help");
        g2d.drawString("Help", (Client.GAME_DIMENSION.width / 2) - width / 2, Client.GAME_DIMENSION.height / 7);

        // Display buttons
        g2d.setFont(tradeWinds.deriveFont(25f));
        int width_return = g2d.getFontMetrics().stringWidth("Return");
        int cenw_return = (int) ((returnButton.getWidth() - width_return) / 2);
        int cenh_return = (int) ((returnButton.getHeight() / 2));
        g2d.drawString("Return", returnButton.x + cenw_return, returnButton.y + 5 + cenh_return);
        g2d.draw(returnButton);

        // Clean up and flip the buffer
        g2d.dispose();
        bufferStrategy.show();

    }

    public void renderMenu() {
    	
    	BufferedImage background = null;
    	try {
			background = ImageIO.read(new File("src/game/res/background.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	

        // Create Graphics 2D Object
        Graphics2D g2d = (Graphics2D) bufferStrategy.getDrawGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Fill background
       g2d.setColor(Color.BLACK);
       g2d.fillRect(0, 0, Client.GAME_DIMENSION.width, Client.GAME_DIMENSION.height);

        g2d.drawImage(background, 0, 0, this.gameH, this.gameW, null);
        
        // Set font
        g2d.setFont(tradeWinds.deriveFont(60f));
        String gameName = "Outbreak";
        int width = g2d.getFontMetrics().stringWidth(gameName);

        // Display game name
        g2d.setColor(Color.GREEN);
        g2d.drawString(gameName, (Client.GAME_DIMENSION.width / 2) - width / 2, Client.GAME_DIMENSION.height / 5);

        // Buttons

        // Create font
        //Font font_buttons = new Font("Copperplate", Font.PLAIN, 30);
        g2d.setFont(tradeWinds.deriveFont(25f));
        g2d.setColor(Color.WHITE);

        //// TODO: Fix hardcoded verticial allignment of button text

        // Play Button
        int width_play = g2d.getFontMetrics().stringWidth("Play");
        int cenw_play = (int) ((playButton.getWidth() - width_play) / 2);
        int cenh_play = (int) ((playButton.getHeight() / 2));
        g2d.drawString("Play", playButton.x + cenw_play, playButton.y + 5 + cenh_play);
        g2d.draw(playButton);

        // Help button
        int width_help = g2d.getFontMetrics().stringWidth("Help");
        int cenw_help = (int) ((helpButton.getWidth() - width_help) / 2);
        int cenh_help = (int) ((helpButton.getHeight() / 2));
        g2d.drawString("Help", helpButton.x + cenw_help, helpButton.y + 5 + cenh_help);
        g2d.draw(helpButton);

        // Settings button
        int width_options = g2d.getFontMetrics().stringWidth("Options");
        int cenw_options = (int) ((optionsButton.getWidth() - width_options) / 2);
        int cenh_options = (int) ((optionsButton.getHeight() / 2));
        g2d.drawString("Options", optionsButton.x + cenw_options, optionsButton.y + 5 + cenh_options);
        g2d.draw(optionsButton);

        // Clean up and flip the buffer
        g2d.dispose();
        bufferStrategy.show();
    }
}
