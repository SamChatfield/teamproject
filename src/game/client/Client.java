package game.client;

import game.ResourceLoader;
import game.util.EndState;
import game.util.PlayerUpdatePacket;
import game.util.Vector;

import javax.swing.*;
import java.awt.*;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import game.ResourceLoader;
import game.util.PlayerUpdatePacket;
import game.util.Vector;

/**
 * This class is the one that the player will run when they want to start the game.
 * When they click "play" in the menu, it will create a new 'Client' thread that will control input and data send/receive.
 */
public class Client extends Canvas {

	// Game settings
	private static final String TITLE = "Outbreak";
	static final Dimension GAME_DIMENSION = new Dimension(640, 640);
	static final Point SCREEN_CENTRE = new Point(GAME_DIMENSION.width / 2, GAME_DIMENSION.height / 2);
	public static final int VIEW_SIZE = 10; // how many tiles can be seen in the game window e.g. 10 => 10x10 view
	public static final int TILE_SIZE = 64;
	private static final int TARGET_FPS = 60;
	private static final long OPTIMAL_TIME_DIFF = 1000000000L / TARGET_FPS;

	// Load main iamges into the game
	public static final BufferedImage zombieImage = ResourceLoader.zombieImage();
	public static final BufferedImage playerZombieImage = ResourceLoader.zombiePlayerImage();
	public static final BufferedImage bulletImage = ResourceLoader.bulletImage();

	public Sound soundManager;
	private JFrame container;
	private BufferStrategy bufferStrategy;
	private InputHandler inputHandler;
	private boolean running;
	private Player player;

	private ClientGameState state;
	private ClientSender sender;

	private Renderer renderer;

	// Client state
	private enum STATE {
		START,
		GAME,
		END, 
		EXIT
	};
	// Menu state
	private enum MSTATE {
		MAIN,
		HOPTIONS,
		NONE
	}
	private STATE currentState;
	private MSTATE menuState;

	private static String username;
	private static String ipAddress;

	/**
	 * Create a new Client object
	 * @param state CurrentSlientState object
	 * @param sender ClientSender object
	 */
	private Client(ClientGameState state, ClientSender sender) {
		this.state = state;
		this.sender = sender;
		container = new JFrame(TITLE);
		JPanel panel = (JPanel) container.getContentPane();
		panel.setPreferredSize(GAME_DIMENSION);
		panel.setLayout(null);

		setBounds(0, 0, GAME_DIMENSION.width, GAME_DIMENSION.height);
		panel.add(this);

		setIgnoreRepaint(true);

		container.pack();
		container.setResizable(false);
		container.setVisible(true);
		container.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		// Handle input
		inputHandler = new InputHandler(this);
		addMouseListener(inputHandler);
		addKeyListener(inputHandler);

		// Request focus
		requestFocus();

		// Set up buffer
		createBufferStrategy(2);
		bufferStrategy = getBufferStrategy();

		// Menu is the first thing to be loaded
		running = true;
		currentState = STATE.START;
		menuState = MSTATE.MAIN;

		// Setup sound
		soundManager = new Sound();
		state.addSoundManager(soundManager);
	}

	/**
	 * Main game loop that handles updating and drawing
	 */
	private void loop() {

		// Renders the menu
		MenuRenderer menu = new MenuRenderer(bufferStrategy);
		renderer = new Renderer(bufferStrategy, state);

		// System time
		long lastLoopTime = System.nanoTime();

		// Starts sound playing
		soundManager.start();

		while(running) {

			// Displays the menu or options screen
			while (currentState == STATE.START) {
				if (menuState == MSTATE.MAIN) {
					menu.renderMenu();
				} else if (menuState == MSTATE.HOPTIONS) {
					menu.renderHelpOptions();
				} 
				menuUpdate(menu);
			}

			// Starts the game once play button is clicked
			while (currentState == STATE.GAME) {

				if (!state.isConnected()) {
					sender.sendObject("StartGame"); // Send a message to the server to start the game.
					while (!state.isConnected()) {
						try {
							Thread.sleep(1);
						} catch (Exception e) {
							System.err.println("Error in starting game: " + e.getMessage());
						} // Without this, this loop breaks on some machines.
					}
				}

				// Get current state of player
				this.player = state.getPlayer();

				// Calculate how long since last update
				// Delta is how far things should move this update to compensate
				long now = System.nanoTime();
				long updateLength = now - lastLoopTime;
				lastLoopTime = now;
				double delta = updateLength / ((double) OPTIMAL_TIME_DIFF);

				// Update game with the delta
				update(delta);

				// Render
				renderer.render();

				// We want each frame to be the active frame for OPTIMAL_TIME_DIFF nanoseconds to give 60 FPS
				// So if the difference between now and the start of this loop (now assigned to lastLoopTime ready for the
				// next loop) is less than this optimal time then we need to sleep the thread for the remaining time to fix
				// at 60 FPS
				now = System.nanoTime();
				if (now - lastLoopTime < OPTIMAL_TIME_DIFF) {
					try {
						Thread.sleep((lastLoopTime - now + OPTIMAL_TIME_DIFF) / 1000000);
					} catch (InterruptedException e) {
						e.printStackTrace();
						System.out.println("Client loop interrupted exception");

					}
				}

				// Is the game over?
				if(state.HasFinished()){
					currentState = STATE.END;
				}
			}

			// If the game is over then we can pass the end state into the renderer.
			while (currentState == STATE.END) {
				renderer.renderGameOver(state.getEndState());
				gameOverUpdate(renderer,state.getEndState());
			}
		}
		System.exit(0);
	}

	/**
	 * Update the game over screen (what is displayed and if buttons clicked)
	 * @param rend Renderer object
	 * @param state EndState of the game containing details of how game went
	 */
	private void gameOverUpdate(Renderer rend, EndState state) {
		double mx, my;
		try {
			mx = inputHandler.getMousePos().getX();
			my = inputHandler.getMousePos().getY();
		} catch(NullPointerException e) {
			// Defauflt mouse pointer position
			mx = 0;
			my = 0;
		}
		int buttonWidth = (int)rend.menuButton.getWidth();
		int buttonHeight = (int)rend.menuButton.getHeight();

		int menuX = (int)rend.menuButton.getX();
		int menuY = (int) rend.menuButton.getY();
		int exitX = (int)rend.exitButton.getX();
		int exitY = (int)rend.exitButton.getY();

		// If return to menu button clicked
		if(mx >= menuX && mx <= (menuX + buttonWidth)) {
			if(my >= menuY && my <= (menuY + buttonHeight)) {
				if(inputHandler.wasMouseClicked()) {
					currentState = STATE.START;
					menuState = MSTATE.MAIN;
				}
			}
		}

		// If exit button clicked
		if(mx >= exitX && mx <= (exitX + buttonWidth)) {
			if(my >= exitY && my <= (exitY + buttonHeight)) {
				if(inputHandler.wasMouseClicked()) {
					currentState = STATE.EXIT;
					running = false;
				}
			}
		}
	}

	// Update the menu
	/**
	 * Update when the menu is shown on screen (what is displayed and if buttons clicked)
	 * @param menu MenuRenderer object
	 */
	private void menuUpdate(MenuRenderer menu) {
		double mx, my;

		// Get location of mouse pointer
		try {
			mx = inputHandler.getMousePos().getX();
			my = inputHandler.getMousePos().getY();
		}
		catch(NullPointerException e) {
			// Default position for mouse
			mx = 0;
			my = 0;
		}

		// Help menu
		if(menuState == MSTATE.HOPTIONS) {

			// Position of return button
			int returnX = (int)menu.returnButton.getX();
			int returnY = (int)menu.returnButton.getY();

			// Handle clicks on the return button
			if(mx >= returnX && mx <= (returnX + menu.returnButton.getWidth())) {
				if(my >= returnY && my <= (returnY + menu.returnButton.getHeight())) {
					if(inputHandler.wasMouseClicked()) {
						menuState = MSTATE.MAIN;
						inputHandler.setMouseClicked(false);
					}
				}
			}

			// Position of SFX Button
			int sfxX = (int)menu.sfxButton.getX();
			int sfxY = (int)menu.sfxButton.getY();

			if(mx >= sfxX && mx <= (sfxX + menu.sfxButton.getWidth())) {
				if(my >= sfxY && my <= (sfxY + menu.sfxButton.getHeight())) {
					if(inputHandler.wasMouseClicked()) {
						if(Sound.sfxPlayback) {
							Sound.sfxPlayback = false;
						} else {
							Sound.sfxPlayback = true;
						}
						inputHandler.setMouseClicked(false);
					}
				}
			}

			// Position of music button
			int musicX = (int)menu.musicButton.getX();
			int musicY = (int)menu.musicButton.getY();

			if(mx >= musicX && mx <= (musicX + menu.musicButton.getWidth())) {
				if(my >= musicY && my <= (musicY + menu.musicButton.getHeight())) {
					if(inputHandler.wasMouseClicked()) {
						if(Sound.musicPlayback) {
							Sound.musicPlayback = false;
						} else {
							Sound.musicPlayback = true;
						}
						inputHandler.setMouseClicked(false);
					}
				}
			}
		}
		else if(menuState == MSTATE.MAIN) {

			// Buttons on the main menu
			int playX = (int)menu.playButton.getX();
			int playY = (int) menu.playButton.getY();
			int helpOptionsX = (int)menu.helpOptionsButton.getX();
			int helpOptionsY = (int)menu.helpOptionsButton.getY();

			if(mx >= playX && mx <= (playX + menu.playButton.getWidth())) {
				if(my >= playY && my <= (playY + menu.playButton.getHeight())) {
					if(inputHandler.wasMouseClicked()) {
						currentState = STATE.GAME;
						menuState = MSTATE.NONE;
						inputHandler.setMouseClicked(false);
					}
				}
			}
			if(mx >= helpOptionsX && mx <= (helpOptionsX + menu.helpOptionsButton.getWidth())) {
				if(my >= helpOptionsY && my <= (helpOptionsY + menu.helpOptionsButton.getHeight())) {
					if(inputHandler.wasMouseClicked()) {
						menuState = MSTATE.HOPTIONS;
						inputHandler.setMouseClicked(false);
					}
				}
			}
		}
	}

	/**
	 * Runs during the game to update the displayed game state.
	 * @param delta
	 */
	private void update(double delta) {

		// Lets store every keypress we see this tick
		ArrayList<String> keyPresses = new ArrayList<>();		
		// Change the player movement speed with 1 and 2
		if (inputHandler.isKeyDown(KeyEvent.VK_1)) {
			keyPresses.add("VK_1");
		}
		if (inputHandler.isKeyDown(KeyEvent.VK_2)) {
			keyPresses.add("VK_2");
		}
		// Handle player keyboard input to move
		if (inputHandler.isKeyDown(KeyEvent.VK_W)) {
			keyPresses.add("VK_W");
		}
		if (inputHandler.isKeyDown(KeyEvent.VK_A)) {
			keyPresses.add("VK_A");
		}
		if (inputHandler.isKeyDown(KeyEvent.VK_D)) {
			keyPresses.add("VK_D");
		}
		if (inputHandler.isKeyDown(KeyEvent.VK_S)) {
			keyPresses.add("VK_S");
		}

		////// DEBUGGING Key bindings
		// Display collision boxes
		if (inputHandler.isKeyDown(KeyEvent.VK_K)) {
			renderer.setShowCollBox(true);
		}
		// Hide collision boxes
		if (inputHandler.isKeyDown(KeyEvent.VK_L)){
			renderer.setShowCollBox(false);
		}
		// Print the player's position
		if (inputHandler.isKeyDown(KeyEvent.VK_P)) {
			System.out.println("Player: (" + player.getX() + ", " + player.getY() + ")");
		}
		// Toggle conversion mode
		if (inputHandler.isKeyDown(KeyEvent.VK_Z)) {
			keyPresses.add("VK_Z");
			System.out.println("Enabled conversion mode!");
		}
		if (inputHandler.isKeyDown(KeyEvent.VK_X)) {
			keyPresses.add("VK_X");
			System.out.println("Disabled conversion mode!");
		}
		// Turn all sound on
		if(inputHandler.isKeyDown(KeyEvent.VK_N)) {
			System.out.println("Sound ON");
			Sound.sfxPlayback = true;
			soundManager.playMusic();
		}
		// Turn all sound off
		if(inputHandler.isKeyDown(KeyEvent.VK_M)) {
			System.out.println("Sound OFF");
			Sound.musicPlayback = false;
			Sound.sfxPlayback = false;
			soundManager.stopMusic();
		}

		// Face the player in the direction of the mouse postate
		Point mousePos = inputHandler.getMousePos();
		Vector fv = null;
		if (inputHandler.isMouseInside() && mousePos != null) {
			fv = new Vector(mousePos.x - 320, 320 - mousePos.y).normalised();

            if (inputHandler.isMouseButtonDown(MouseEvent.BUTTON1)) {
            	keyPresses.add("BUTTON1");
				soundManager.bulletSound(player.canShoot());
			}

			// We need to do this in case fv is null
			float x = -100;
			float y = -100;
			if(fv!=null){
				x = fv.x();
				y = fv.y();
			}
			sender.sendObject(new PlayerUpdatePacket(player.getData(),keyPresses,delta, x,y)); // We send an object to the server every tick.

			updateLocalPlayer(keyPresses,delta,fv);

		}


		/*
		if(player.getHealth() < 0) {
			currentState = STATE.END;
			System.out.println("GAME OVER");
		}
		*/
	}

	/**
	 * Update player status locally
	 * @param keyPresses ArrayList of strings that represent key presses
	 * @param delta Interpolation
	 * @param fv Movement for x and y
	 */
	private void updateLocalPlayer(ArrayList<String> keyPresses, double delta, Vector fv) {

		Vector pdv = new Vector(0.0f, 0.0f); // Player direction vector for this update
		for(String s:keyPresses) {
			switch(s){
			case "VK_1":
				player.setMoveSpeed(player.getMoveSpeed() - 0.01f);
				break;
			case "VK_2":
				player.setMoveSpeed(player.getMoveSpeed() + 0.01f);
				break;
			case "VK_W":
				pdv.add(new Vector(0.0f, 1.0f));
				break;
			case "VK_A":
				pdv.add(new Vector(-1.0f, 0.0f));
				break;
			case "VK_D":
				pdv.add(new Vector(1.0f, 0.0f));
				break;
			case "VK_S":
				pdv.add(new Vector(0.0f, -1.0f));
				break;
			case "VK_Z":
				player.conversionMode = true;
				break;
			case "VK_X":
				player.conversionMode = false;
				break;
			case "BUTTON1":
				// this should still happen on the server
				break;
			}
		}
		if(fv != null){
			player.face(fv.x(),fv.y());
		}
		Vector pnv = pdv.normalised(); // Player normal direction vector for this update
		float pdx = pnv.x() * player.getMoveSpeed() * ((float) delta); // Actual change in x this update
		float pdy = pnv.y() * player.getMoveSpeed() * ((float) delta); // Actual change in y this update
		player.move(pdx, pdy);

	}

	/**
	 * Display login prompt allowing to choose a username and the IP address of server
	 */
	public static void loginPrompt() {

		JTextField usernameEntry = new JTextField();
		JTextField ipaddyEntry = new JTextField();
		Object[] message = {
				"Username:", usernameEntry,
				"Server IP Address:", ipaddyEntry
		};

		int option = JOptionPane.showConfirmDialog(null, message, "Outbreak v1.0", JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE);
		if (option == JOptionPane.OK_OPTION) {
			username = usernameEntry.getText();
			if(username.equals("")) {
				// Default value if no username selected
				username = "a";
			};
			ipAddress = ipaddyEntry.getText();
			if(ipAddress.equals("")) {
				// Default value if no server address entered
				ipAddress = "localhost";
			}
		} else {
			System.out.println("Game login cancelled");
			System.exit(0);
		}
	}

	/**
	 * Get the player object
	 * @return Player object
	 */
	public Player getPlayer() {
		return player;
	}

	public static void main(String[] args) {

		// Showlogin prompt
		loginPrompt();

		// Initialise
		ObjectOutputStream objOut = null;
		ObjectInputStream objIn = null;
		Socket outSocket = null;

		try {
			// Hardcoded port
			outSocket = new Socket("localhost",4444);
			objOut = new ObjectOutputStream(outSocket.getOutputStream());
			objIn = new ObjectInputStream(outSocket.getInputStream());
		} catch(Exception e){
			JOptionPane.showMessageDialog(null, "Server offline!", "Server hasn't been started", JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}

		// ClientSender and ClientReceiver objects to handle communication with server
		ClientSender client_sender = new ClientSender(username, objOut,null);
		ClientReceiver client_receiver = new ClientReceiver(username, objIn);

		// Then create a client state for the client
		ClientGameState state = new ClientGameState(username);

		client_receiver.addState(state); // Must be called before starting the thread.
		client_sender.addState(state);
		// If this method didn't exist, stateface would need to be added above, but stateface relies on receiver.

		// Starting threads
		client_sender.start();
		client_receiver.start();

		Client client = new Client(state,client_sender);

		// Create and start the client loop over the loop method of the client object.
		// :: is a method reference since loop is an existing method,
		// semantically the same as () -> client.loop() lambda expression.
		Thread gameThread = new Thread(client::loop);
		gameThread.start();
	}
}