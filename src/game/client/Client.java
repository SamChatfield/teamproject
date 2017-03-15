package game.client;

import game.ResourceLoader;
import game.util.PlayerUpdatePacket;
import game.util.Vector;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;


/**
 * Created by Sam on 20/01/2017.
 * Modified extensively by Daniel.
 *
 * This class is the one that the player will run when they want to start the game.
 * When they click "play" in the menu, it will create a new 'Client' thread that will control input and data send/receive.
 */
public class Client extends Canvas {

	private static final String TITLE = "Capture the Zom.biz";
	static final Dimension GAME_DIMENSION = new Dimension(640, 640);
	static final Point SCREEN_CENTRE = new Point(GAME_DIMENSION.width / 2, GAME_DIMENSION.height / 2);
	public static final int VIEW_SIZE = 10; // how many tiles can be seen in the game window e.g. 10 => 10x10 view
	public static final int TILE_SIZE = 64;
	private static final int TARGET_FPS = 60;
	private static final long OPTIMAL_TIME_DIFF = 1000000000L / TARGET_FPS;

	public static final BufferedImage zombieImage = ResourceLoader.zombieImage();
	public static final BufferedImage playerZombieImage = ResourceLoader.zombiePlayerImage();
    
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
		HELP,
		OPTIONS,
		NONE
	}
	private STATE currentState;
	private MSTATE menuState;

	// Non final stuff, remove before release
	private final int zombieCount = 70;
	
	private static String username;
	private static String ipAddress;

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

		inputHandler = new InputHandler(this);
		addMouseListener(inputHandler);
		addKeyListener(inputHandler);

		requestFocus();

		createBufferStrategy(2);
		bufferStrategy = getBufferStrategy();

		running = true;
		currentState = STATE.START;
		menuState = MSTATE.MAIN;

        soundManager = new Sound();
        state.addSoundManager(soundManager);
	}


	private void loop() {

		MenuRenderer menu = new MenuRenderer(bufferStrategy);
        renderer = new Renderer(bufferStrategy, state);

        long lastLoopTime = System.nanoTime();

		soundManager.start();
		
		while(running) {

			while (currentState == STATE.START) {
				if (menuState == MSTATE.MAIN) {
					menu.renderMenu();
				} else if (menuState == MSTATE.HELP) {
					menu.renderHelp();
				} else if (menuState == MSTATE.OPTIONS) {
					menu.renderOptions();
				}
				menuUpdate(menu);
			}

			while (currentState == STATE.GAME) {

				if (!state.isConnected()) {
					sender.sendObject("StartGame"); // send a message to the server to start the game.
					while (!state.isConnected()) {
						try {
							Thread.sleep(1);
						} catch (Exception e) {
						} // without this, this loop breaks on some machines.
					}
				}

				this.player = state.getPlayer();

				// Calculate how long since last update
				// Delta is how far things should move this update to compensate
				long now = System.nanoTime();
				long updateLength = now - lastLoopTime;
				lastLoopTime = now;
				double delta = updateLength / ((double) OPTIMAL_TIME_DIFF);

				// FPS counter stuff would go here TODO Add an option for this

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
						System.out.println("Client loop staterupted exception");
					}
				}

				if (state.getTimeRemaining() <= 0) {
					currentState = STATE.END;
					break;
				}
			}

			while (currentState == STATE.END) {
				renderer.renderGameOver();
				gameOverUpdate(renderer);
			}
		}
		System.exit(0);
	}
	
	private void gameOverUpdate(Renderer rend) {
		double mx, my;
		try {
			mx = inputHandler.getMousePos().getX();
			my = inputHandler.getMousePos().getY();
		} catch(NullPointerException e) {
			mx = 0;
			my = 0;
		}
		int buttonWidth = (int)rend.menuButton.getWidth();
		int buttonHeight = (int)rend.menuButton.getHeight();
		
		int playX = (int)rend.menuButton.getX();
		int playY = (int) rend.menuButton.getY();
		int exitX = (int)rend.exitButton.getX();
		int exitY = (int)rend.exitButton.getY();


		if(mx >= playX && mx <= (playX + buttonWidth)) {
			if(my >= playY && my <= (playY + buttonHeight)) {
				if(inputHandler.wasMouseClicked()) {
					System.out.println("MENU BUTTON CLICKED");
					currentState = STATE.START;
					menuState = MSTATE.MAIN;
					
					}
			}
		}

		if(mx >= exitX && mx <= (exitX + buttonWidth)) {
			if(my >= exitY && my <= (exitY + buttonHeight)) {
				if(inputHandler.wasMouseClicked()) {
					System.out.println("EXIT BUTTON CLICKED");
					currentState = STATE.EXIT;
					running = false;
				}
			}
		}
	}

	private void menuUpdate(MenuRenderer menu) {
		double mx, my;
		try {
			mx = inputHandler.getMousePos().getX();
			my = inputHandler.getMousePos().getY();
		}
		catch(NullPointerException e) {
			mx = 0;
			my = 0;
		}

		int buttonWidth = (int)menu.playButton.getWidth();
		int buttonHeight = (int)menu.playButton.getHeight();

		if(menuState == MSTATE.HELP || menuState == MSTATE.OPTIONS) {;
			int returnX = (int)menu.returnButton.getX();
			int returnY = (int)menu.returnButton.getY();

			if(mx >= returnX && mx <= (returnX + buttonWidth)) {
				if(my >= returnY && my <= (returnY + buttonHeight)) {
					if(inputHandler.wasMouseClicked()) {
						menuState = MSTATE.MAIN;
						inputHandler.setMouseClicked(false);
						System.out.println("RETURN BUTTON CLICKED");
					}
				}
			}
		}
		else if(menuState == MSTATE.MAIN) {

			int playX = (int)menu.playButton.getX();
			int playY = (int) menu.playButton.getY();
			int optionsX = (int)menu.optionsButton.getX();
			int optionsY = (int)menu.optionsButton.getY();
			int helpX = (int)menu.helpButton.getX();
			int helpY = (int)menu.helpButton.getY();


			if(mx >= playX && mx <= (playX + buttonWidth)) {
				if(my >= playY && my <= (playY + buttonHeight)) {
					if(inputHandler.wasMouseClicked()) {
						System.out.println("PLAY BUTTON CLICKED");
						currentState = STATE.GAME;
						menuState = MSTATE.NONE;
						//inputHandler.setMouseClicked(false);
						}
				}
			}

			if(mx >= helpX && mx <= (helpX + buttonWidth)) {
				if(my >= helpY && my <= (helpY + buttonHeight)) {
					if(inputHandler.wasMouseClicked()) {
						System.out.println("HELP BUTTON CLICKED");
						menuState = MSTATE.HELP;
						//inputHandler.setMouseClicked(false);
					}
				}
			}

			if(mx >= optionsX && mx <= (optionsX + buttonWidth)) {
				if(my >= optionsY && my <= (optionsY + buttonHeight)) {
					if(inputHandler.wasMouseClicked()) {
						System.out.println("OPTIONS BUTTON CLICKED");
						menuState = MSTATE.OPTIONS;
						//inputHandler.setMouseClicked(false);
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

		ArrayList<String> keyPresses = new ArrayList<>();		// Lets store every keypress we see this tick
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
        // Toggle conversion mode
        if (inputHandler.isKeyDown(KeyEvent.VK_Z)) {
            keyPresses.add("VK_Z");
            System.out.println("Enabled conversion mode!");
        }
        if (inputHandler.isKeyDown(KeyEvent.VK_X)) {
            keyPresses.add("VK_X");
            System.out.println("Disabled conversion mode!");
        }

		// Other debugging key bindings
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
				
		if(inputHandler.isKeyDown(KeyEvent.VK_N)) {
			System.out.println("Sound ON");
			//soundManager.musicPlayback = true;
			soundManager.sfxPlayback = true;
			soundManager.playMusic();
		}
		if(inputHandler.isKeyDown(KeyEvent.VK_M)) {
			System.out.println("Sound OFF");
			soundManager.musicPlayback = false;
			soundManager.sfxPlayback = false;
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

        int newNumConvertedZombies = 0;
		for (int i = 0; i < state.getZombieDataPackets().size(); i++) {
			if (state.getZombieDataPackets().get(i).getUsername().equals(player.getUsername())) {
				newNumConvertedZombies += 1;
			}
		}
        player.setNumConvertedZombies(newNumConvertedZombies);

		if(player.getHealth() < 0) {
			currentState = STATE.END;
			System.out.println("GAME OVER");
		}
	}

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

	public static void loginPrompt() {
		JFrame dialogue = new JFrame(TITLE);

		JTextField usernameEntry = new JTextField();
		JTextField ipaddyEntry = new JTextField();
		Object[] message = {
				"Username:", usernameEntry,
				"Server IP:", ipaddyEntry
		};

		int option = JOptionPane.showConfirmDialog(null, message, "Login to game", JOptionPane.OK_CANCEL_OPTION);
		if (option == JOptionPane.OK_OPTION) {
			System.out.println(usernameEntry.getText());
			username = usernameEntry.getText();
			System.out.println(ipaddyEntry.getText());
			ipAddress = ipaddyEntry.getText();
		} else {
			System.out.println("Game login cancelled");
			System.exit(0);
		}
	}
	
	public Player getPlayer() {
        return player;
    }

    public static void main(String[] args) {
        
        
        loginPrompt();

        // Initialise
        ObjectOutputStream objOut = null;
        ObjectInputStream objIn = null;
        Socket outSocket = null;
        
        try{
            outSocket = new Socket("localhost",4444);
            objOut = new ObjectOutputStream(outSocket.getOutputStream());
            objIn = new ObjectInputStream(outSocket.getInputStream());
        }catch(Exception e){
        	System.out.println("WTF");
			JOptionPane.showMessageDialog(null, "Server not started!", "Server hasn't been started", JOptionPane.ERROR_MESSAGE);
			System.exit(1);
        }

        ClientSender client_sender = new ClientSender(username, objOut,null);
        ClientReceiver client_receiver = new ClientReceiver(username, objIn);

        // Then create a client state for the client
        ClientGameState state = new ClientGameState(username);

        client_receiver.addState(state); //must be called before starting the thread.
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
