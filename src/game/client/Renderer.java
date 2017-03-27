package game.client;

import game.PowerUp;
import game.ResourceLoader;
import game.Weapon;
import game.Weapon.WeaponState;
import game.map.MapData;
import game.map.Tile;
import game.util.DataPacket;
import game.util.EndState;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Renders the game on screen
 */
public class Renderer {

    // Load main iamges into the game
    public static final BufferedImage wildZombieImage = ResourceLoader.zombieImage();
    public static final BufferedImage playerZombieImage = ResourceLoader.zombiePlayerImage();
    public static final BufferedImage opponentZombieImage = ResourceLoader.zombieOpponentImage();
    public static final BufferedImage bulletImage = ResourceLoader.bulletImage();
    public static final BufferedImage lightingImage = ResourceLoader.lightingImage();
    public static final BufferedImage speedUp = ResourceLoader.speedUp();
    public static final BufferedImage speedDown = ResourceLoader.speedDown();
    public static final BufferedImage moreHealth = ResourceLoader.moreHealth();
    public static final BufferedImage invertControls = ResourceLoader.invertControls();
    public static final BufferedImage freezePlayer = ResourceLoader.freezePlayer();
    public static final BufferedImage coz = ResourceLoader.coz();
    //Blood splatters
    public static final BufferedImage splatter1 = ResourceLoader.splatter1();
    public static final BufferedImage splatter2 = ResourceLoader.splatter2();
    public static final BufferedImage splatter3 = ResourceLoader.splatter3();
    //Weapons
    public static final BufferedImage machineGun = ResourceLoader.machineGun();
    public static final BufferedImage shotgun = ResourceLoader.shotgun();
    public static final BufferedImage converter = ResourceLoader.converter();
    public static final BufferedImage uzi = ResourceLoader.uzi();
    public Rectangle menuButton;
    public Rectangle exitButton;
    public Color fadedWhite = new Color(255, 255, 255, 190);
    private BufferStrategy bufferStrategy;
    private Player player;
    private int gameH, gameW;
    private Font tradeWinds;
    private ClientGameState state;

    /**
     * Create a new Renderer
     *
     * @param bufferStrategy Strategy to use to draw in a buffer
     * @param state          The current ClientGameState
     */
    public Renderer(BufferStrategy bufferStrategy, ClientGameState state) {
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
        ArrayList<DataPacket> bulletPackets = state.getBulletDataPackets();

        MapData mapData = state.getMapData();
        ArrayList<PowerUp> powerups = state.getPowerups();
        ArrayList<Weapon> weapons = state.getWeapons();

        // Set up the graphics instance for the current back buffer
        Graphics2D g2d = (Graphics2D) bufferStrategy.getDrawGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Clear the screen
        g2d.setColor(g2d.getBackground());
        g2d.fillRect(0, 0, gameH, gameW);

        g2d.setColor(Color.BLACK);

        // Draw the map
        drawMap(g2d, mapData, player);


        // Draw the zombies that are dead first
        for (DataPacket z : zombiePackets) {
            if (!z.isAlive()) {
                drawDeadZombie(g2d, player, z);
            }
        }

        // Draw the player
        player.draw(g2d);

        // Draw relative to other player
        if (state.getOtherPlayer() != null) {
            state.getOtherPlayer().drawRelativeToOtherPlayer(g2d, player, state.getOtherPlayer());
        }

        // Draw the bullets
        for (DataPacket b : bulletPackets) {
            drawBullet(g2d, player, b);
        }

        // Draw the zombies
        for (DataPacket z : zombiePackets) {
            if (z.isAlive()) { // we don't want to draw the dead zombies again.
                drawZombie(g2d, player, z);
            }
        }

        for (PowerUp p : powerups) {
            drawPowerup(g2d, p, player);
        }

        for (Weapon w : weapons) {
            drawWeapon(g2d, w, player);
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
        g2d.setColor(new Color(0, 100, 0, 200));
        if (healthPercentage < 10) {
            g2d.setColor(new Color(102, 0, 0, 200));
        }
        Rectangle healthBarFill = new Rectangle(10, 10, player.getHealth() * 4, 20);
        g2d.fill(healthBarFill);

        // Health bar text display
        g2d.setColor(new Color(255, 255, 255, 200));
        String healthFormat = String.format("%.2f", healthPercentage);
        g2d.drawString("Health: " + healthFormat + "%", 15, (int) (healthBarBackground.getY() + healthBarBackground.getHeight() + 20));

        ////// Items box

        WeaponState[] weaponStates = player.getInventory(); // Get states of weapons
        //WeaponState currentWeapon = player.getCurrentlyEquipped(); // Get currently equipped weapon

        g2d.setColor(fadedWhite);
        g2d.setFont(tradeWinds.deriveFont(11f));
        int xCoord = 10;

        switch (player.getCurrentlyEquipped()) {
            case PISTOL:
                g2d.drawString("Current Weapon: Pistol", xCoord + 5, 580);
                break;
            case UZI:
                g2d.drawString("Current Weapon:  Uzi", xCoord + 5, 580);
                break;
            case SHOTGUN:
                g2d.drawString("Current Weapon: Shotgun", xCoord + 5, 580);
                break;
            case MAC_GUN:
                g2d.drawString("Current Weapon: Machine Gun", xCoord + 5, 580);
                break;
            case CONVERT:
                g2d.drawString("Current Weapon: Zombie Converter", xCoord + 5, 580);
                break;
            default:
                break;
        }

        if (player.getCurrentPU() == PowerUp.PuState.SPEED_UP) {
            g2d.drawString("Current PowerUp: Speed Up", xCoord + 5, 560);
        }
        if (player.getCurrentPU() == PowerUp.PuState.FREEZE) {
            g2d.drawString("Current PowerUp: Frozen", xCoord + 5, 560);
        }
        if (player.getCurrentPU() == PowerUp.PuState.SLOW_DOWN) {
            g2d.drawString("Current PowerUp: Slow Down", xCoord + 5, 560);
        }
        if (player.getCurrentPU() == PowerUp.PuState.INVERSE) {
            g2d.drawString("Current PowerUp: Invert Controls", xCoord + 5, 560);
        }


        // Loop to draw each box and weapon image
        int i = 0;
        for (WeaponState weapon : WeaponState.values()) {

            // See if weapon obtained
            boolean weaponObtained = false;

            try {
                if (weaponStates[i] != null) {
                    weaponObtained = true;
                }
            } catch (NullPointerException e) {
                System.err.println("Error in Renderer: NullPointer in checking weaponStates, this shouldn't happen!");
            }


            // Highlight selected weapon
            if (player.getCurrentlyEquipped() == weapon) {
                g2d.setColor(new Color(50, 205, 50, 150));
            } else if (weaponObtained) {
                g2d.setColor(new Color(255, 255, 255, 150));
            } else {
                g2d.setColor(new Color(255, 255, 255, 50));
            }

            Rectangle itemsBox = new Rectangle(xCoord, 590, 45, 45);
            g2d.fill(itemsBox);

            // Draw border and number
            g2d.setColor(new Color(0, 0, 0, 180));
            g2d.setFont(tradeWinds.deriveFont(10f));
            Rectangle itemsBox2 = new Rectangle(xCoord - 1, 590 - 1, 45 + 1, 45 + 1);
            g2d.draw(itemsBox2);
            g2d.drawString(Integer.toString(i + 1), xCoord + 4, 632);

            int weaponX = xCoord + 3;
            int weaponY = 587;

            switch (weapon) {

                case PISTOL:
                    g2d.drawImage(ResourceLoader.pistol(weaponObtained), weaponX, weaponY, null);
                    break;
                case UZI:
                    g2d.drawImage(ResourceLoader.uzi(weaponObtained), weaponX, weaponY, null);
                    break;
                case SHOTGUN:
                    g2d.drawImage(ResourceLoader.shotgun(weaponObtained), weaponX, weaponY, 40, 40, null);
                    break;
                case MAC_GUN:
                    g2d.drawImage(ResourceLoader.machineGun(weaponObtained), weaponX, weaponY, 38, 38, null);
                    break;
                case CONVERT:
                    g2d.drawImage(ResourceLoader.converter(weaponObtained), weaponX, weaponY, null);
                    break;

            }

            i++;
            xCoord += 45;
        }

        // Display time remaining
        g2d.setFont(tradeWinds.deriveFont(35f));
        g2d.setColor(fadedWhite);
        String remainingTime = String.format("Time: %d:%02d", (timeRemaining / 60), (timeRemaining % 60));
        g2d.drawString(remainingTime, gameW - (g2d.getFontMetrics().stringWidth(remainingTime) + 20), 40);

        // Display number of converted zombies
        int playerZombies = player.getNumConvertedZombies();
        int opponentZombies = state.getOtherPlayer().getNumConvertedZombies();
        int totalZombies = state.getAliveZombies().size();


        // Zombie counts
        ArrayList<Integer> zombieCounts = new ArrayList<Integer>();
        zombieCounts.add(totalZombies - (opponentZombies + playerZombies));
        zombieCounts.add(playerZombies);
        zombieCounts.add(opponentZombies);

        // Zombie count box labels
        ArrayList<String> zombieCountLabels = new ArrayList<String>();
        zombieCountLabels.add("Wild");
        zombieCountLabels.add(state.getPlayer().getUsername());
        zombieCountLabels.add(state.getOtherPlayer().getUsername());

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
        for (int count : zombieCounts) {
            Rectangle zombieCountBox = new Rectangle(580, countsY, 40, 30);
            g2d.setColor(zombieCountColours.get(counter));
            g2d.fill(zombieCountBox);

            g2d.setColor(fadedWhite);
            g2d.setFont(tradeWinds.deriveFont(18f));
            String zombieCount = "" + count;
            g2d.drawString(zombieCount, (int) (zombieCountBox.getX() + (g2d.getFontMetrics().stringWidth(zombieCount) / 2)), (int) (zombieCountBox.getY() + (zombieCountBox.getHeight() / 2) + 7));

            g2d.setFont(tradeWinds.deriveFont(10f));
            g2d.drawString(zombieCountLabels.get(counter), (int) (zombieCountBox.getX() - (g2d.getFontMetrics().stringWidth(zombieCountLabels.get(counter)) + 5)), (int) (3 + zombieCountBox.getY() + (zombieCountBox.getHeight() / 2)));
            countsY += 30;
            counter++;
        }

        // Clean up and flip the buffer
        g2d.dispose();
        bufferStrategy.show();
    }

    public void renderWaitingForOpponent() {

        // Set up the graphics instance for the current back buffer
        Graphics2D g2d = (Graphics2D) bufferStrategy.getDrawGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Clear the screen
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, Client.GAME_DIMENSION.width, Client.GAME_DIMENSION.height);

        // Apply text
        g2d.setColor(Color.WHITE);
        g2d.setFont(tradeWinds.deriveFont(20f));
        String text = "Matching you with an opponent...";
        int twidth = g2d.getFontMetrics().stringWidth(text);
        g2d.drawString(text, (Client.GAME_DIMENSION.width / 2) - twidth / 2, Client.GAME_DIMENSION.height / 5);

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

        //		System.out.println(endState.getWinnerName());
        // endState.getPlayer1().getNumConvertedZombies();

        try {
            gameWinner = endState.getWinnerName();
            if (endState.getReason() == endState.getReason().PLAYER_DIED) {
                gameOverReason = "Player died";
            } else if (endState.getReason() == endState.getReason().TIME_EXPIRED) {
                gameOverReason = "Time over";
            }

            player1Zombies = "" + endState.getPlayer1().getNumConvertedZombies();
            player2Zombies = "" + endState.getPlayer2().getNumConvertedZombies();
        } catch (NullPointerException e) {
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
        for (String gameOverString : gameOverStrings) {
            int stringWidth = g2d.getFontMetrics().stringWidth(gameOverString);
            g2d.drawString(gameOverString, (this.gameW / 2) - (stringWidth / 2), y);
            if (counter == 1) {
                y += 70;
            } else {
                y += 40;
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
        menuButton = new Rectangle((this.gameW / 2) - 110, (this.gameH / 15) * 10, 220, 50);
        exitButton = new Rectangle((this.gameW / 2) - 110, (this.gameH / 15) * 12, 220, 50);
        gameOverButtons.add(menuButton);
        gameOverButtons.add(exitButton);

        ArrayList<String> gameOverButtonStrings = new ArrayList<String>();
        gameOverButtonStrings.add("Return to menu");
        gameOverButtonStrings.add("Exit");

        counter = 0;
        for (String gameOverButtonString : gameOverButtonStrings) {
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
     *
     * @param g2d Graphics2D object
     */
    public void drawLighting(Graphics2D g2d) {
        g2d.drawImage(lightingImage, 0, 0, null);
    }

    /**
     * Draw the map on screen
     *
     * @param g2d     Graphics2D object
     * @param mapData MapData to draw
     * @param player  Player object
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
     *
     * @param g2d    Graphics2D object
     * @param player Player object
     * @param z      Zombies DataPacket
     */
    private void drawZombie(Graphics2D g2d, Player player, DataPacket z) {

        // Width and height of the entity sprite
        int w = wildZombieImage.getWidth();
        int h = wildZombieImage.getHeight();

        Point drawPoint = player.relativeDrawPoint(z.getX(), z.getY(), w, h);
        int drawX = drawPoint.x;
        int drawY = drawPoint.y;

        g2d.setColor(Color.GREEN);
        Rectangle healthBarFill = new Rectangle(drawX, drawY + 50, z.getHealth(), 2);
        g2d.fill(healthBarFill);
        g2d.setColor(Color.BLACK);


        AffineTransform at = g2d.getTransform();
        g2d.rotate(z.getFacingAngle(), drawX + w / 2, drawY + h / 2);

        if (z.getState() == DataPacket.State.PLAYER && player.getUsername().equals(z.getUsername())) {
            g2d.drawImage(playerZombieImage, drawX, drawY, null);
        } else if (z.getState() == DataPacket.State.PLAYER) {
            g2d.drawImage(opponentZombieImage, drawX, drawY, null);
        } else {
            g2d.drawImage(wildZombieImage, drawX, drawY, null);
        }
        g2d.setTransform(at);
    }


    /**
     * Draw bullets on the screen
     *
     * @param g2d    Graphics2D object
     * @param player Player object
     * @param b      DataPacket for the bullet
     */
    private void drawBullet(Graphics2D g2d, Player player, DataPacket b) {
        int w = bulletImage.getWidth();
        int h = bulletImage.getHeight();

        Point drawPoint = player.relativeDrawPoint(b.getX(), b.getY(), w, h);
        int drawX = drawPoint.x;
        int drawY = drawPoint.y;

        AffineTransform at = g2d.getTransform();
        g2d.rotate(b.getFacingAngle(), drawX, drawY);
        g2d.drawImage(bulletImage, drawX, drawY, null);
        g2d.setTransform(at);
    }

    private void drawDeadZombie(Graphics2D g2d, Player player, DataPacket d) {
        int w = Renderer.splatter1.getWidth();
        int h = Renderer.splatter1.getHeight();

        Point drawPoint = player.relativeDrawPoint(d.getX(), d.getY(), w, h);
        int drawX = drawPoint.x;
        int drawY = drawPoint.y;

        BufferedImage image = Renderer.splatter1;

        g2d.drawImage(image, drawX, drawY, null);
    }

    private void drawPowerup(Graphics2D g2d, PowerUp p, Player player) {
        int w = Renderer.freezePlayer.getWidth();
        int h = Renderer.freezePlayer.getHeight();

        Point drawPoint = player.relativeDrawPoint(p.getx(), p.gety(), w, h);
        int drawX = drawPoint.x;
        int drawY = drawPoint.y;

        BufferedImage image = Renderer.opponentZombieImage;

        switch (p.getpState()) {
            case FREEZE:
                image = Renderer.freezePlayer;
                break;
            case HEALTH:
                image = Renderer.moreHealth;
                break;
            case INVERSE:
                image = Renderer.invertControls;
                break;
            case SPEED_UP:
                image = Renderer.speedUp;
                break;
            case SLOW_DOWN:
                image = Renderer.speedDown;
                break;
            case COZ:
                image = Renderer.coz;
                break;
            default:
                break;

        }
        g2d.drawImage(image, drawX, drawY, null);
    }

    private void drawWeapon(Graphics2D g2d, Weapon g, Player player) {
        int w = Renderer.moreHealth.getWidth();
        int h = Renderer.moreHealth.getHeight();

        BufferedImage image = Renderer.machineGun;
        switch (g.getwState()) {
            case MAC_GUN:
                image = Renderer.machineGun;
                break;
            case SHOTGUN:
                image = Renderer.shotgun;
                break;
            case CONVERT:
                image = Renderer.converter;
                break;
            case UZI:
                image = Renderer.uzi;
        }

        Point drawPoint = player.relativeDrawPoint(g.getx(), g.gety(), w, h);
        int drawX = drawPoint.x;
        int drawY = drawPoint.y;
        g2d.drawImage(image, drawX, drawY, null);
    }

}