package game;

import game.map.MapData;
import game.util.Vector;

import javax.swing.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;


/**
 * Created by Sam on 20/01/2017.
 */
public class Game extends Canvas {

	private static final String TITLE = "Capture the Zom.biz";
	static final Dimension GAME_DIMENSION = new Dimension(640, 640);
	static final Point SCREEN_CENTRE = new Point(GAME_DIMENSION.width / 2, GAME_DIMENSION.height / 2);
	public static final int VIEW_SIZE = 10; // how many tiles can be seen in the game window e.g. 10 => 10x10 view
	public static final int TILE_SIZE = 64;
	private static final int TARGET_FPS = 60;
	private static final long OPTIMAL_TIME_DIFF = 1000000000L / TARGET_FPS;

    
    public Sound soundManager;
	private JFrame container;
	private BufferStrategy bufferStrategy;
	private InputHandler inputHandler;
	private boolean menu, running;
	private MapData mapData;
	private Player player;
	private ArrayList<Zombie> zombies;


	// Game state
	private enum STATE {
		START,
		GAME,
		END
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
	private final int zombieCount = 50;

	private Game() {
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

		running = false;
		menu = true;
		currentState = STATE.START;
		menuState = MSTATE.MAIN;

        soundManager = new Sound();
        soundManager.start();
	}

	private void loop() {

		MenuRenderer menu = new MenuRenderer(bufferStrategy);

		while(currentState == STATE.START) {
			if(menuState == MSTATE.MAIN) {
				menu.renderMenu();
			}
			else if(menuState == MSTATE.HELP) {
				menu.renderHelp();
			}
			else if(menuState == MSTATE.OPTIONS) {
				menu.renderOptions();
			}
			menuUpdate(menu);
		}

		init();
		Renderer renderer = new Renderer(bufferStrategy, mapData, player, zombies);
		long lastLoopTime = System.nanoTime();
		//Timer timer = new Timer();
		//timer.start();
		Timer timer = new Timer(180);
		new Thread(timer).start();


		while (currentState == STATE.GAME) {


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
			renderer.render(timer);

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
					System.out.println("Game loop interrupted exception");
				}
			}

			if(timer.getTimeRemaining() <= 0) {
				currentState = STATE.END;
				break;
			}
		}

		while(currentState == STATE.END) {
			renderer.renderGameOver();

			try {
				Thread.sleep(3000);
				System.exit(0);
			}
			catch(Exception e) {
				System.out.println("Thread Exception: " + e.getMessage());
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
			};

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

	private void update(double delta) {
		// Player movement
		float pMoveSpeed = player.getMoveSpeed();

		// Change the player movement speed with 1 and 2
		if (inputHandler.isKeyDown(KeyEvent.VK_1)) {
			player.setMoveSpeed(pMoveSpeed -= 0.01f);
		}
		if (inputHandler.isKeyDown(KeyEvent.VK_2)) {
			player.setMoveSpeed(pMoveSpeed += 0.01f);
		}

		Vector pdv = new Vector(0.0f, 0.0f); // Player direction vector for this update

		// Handle player keyboard input to move
		if (inputHandler.isKeyDown(KeyEvent.VK_W)) {
			pdv.add(new Vector(0.0f, 1.0f));
		}
		if (inputHandler.isKeyDown(KeyEvent.VK_A)) {
			pdv.add(new Vector(-1.0f, 0.0f));
		}
		if (inputHandler.isKeyDown(KeyEvent.VK_D)) {
			pdv.add(new Vector(1.0f, 0.0f));
		}
		if (inputHandler.isKeyDown(KeyEvent.VK_S)) {
			pdv.add(new Vector(0.0f, -1.0f));
		}

		// Other debugging key bindings
		// Display collision boxes
		if (inputHandler.isKeyDown(KeyEvent.VK_K)) {
			player.setShowCollBox(true);
			for (Zombie z : zombies) {
				z.setShowCollBox(true);
			}
		}
		// Hide collision boxes
		if (inputHandler.isKeyDown(KeyEvent.VK_L)){
			player.setShowCollBox(false);
			for (Zombie z : zombies) {
				z.setShowCollBox(false);
			}
		}
		// Print the player's position
		if (inputHandler.isKeyDown(KeyEvent.VK_P)) {
			System.out.println("Player: (" + player.x() + ", " + player.y() + ")");
		}
		if (inputHandler.isKeyDown(KeyEvent.VK_Z)) {
			if(player.conversionMode) {
				player.conversionMode = false;
				System.out.println("Disabled conversion mode!");
				}
			else {
				player.conversionMode = true;
				System.out.println("Enabled conversion mode!");
			}
		}

		// Move the player by the correct amount accounting for movement speed, delta, and normalisation of the vector
		Vector pnv = pdv.normalised(); // Player normal direction vector for this update
		float pdx = pnv.x() * pMoveSpeed * ((float) delta); // Actual change in x this update
		float pdy = pnv.y() * pMoveSpeed * ((float) delta); // Actual change in y this update
		player.move(pdx, pdy);

		// Face the player in the direction of the mouse pointer
		Point mousePos = inputHandler.getMousePos();
		if (inputHandler.isMouseInside() && mousePos != null) {
//			player.face(mousePos.x - 320, mousePos.y - 320);
			Vector fv = new Vector(mousePos.x - 320, 320 - mousePos.y).normalised();
			player.face(fv.x(), fv.y());
            // Player shooting
            if (inputHandler.isMouseButtonDown(MouseEvent.BUTTON1)) {
                // game coord x and y position of the aim
                double playerAngle = player.getFacingAngle();
                float aimX = (float) Math.cos(playerAngle);
                float aimY = (float) -Math.sin(playerAngle);
                boolean playerShot = player.shoot(aimX, aimY);
                soundManager.bulletSound(playerShot);
            }
        }

        // Move the zombies around randomly
        Random rand = new Random();
        for (Zombie zombie : zombies) {
            // Change the zombie's direction with given probability
            if (rand.nextFloat() < Zombie.DIRECTION_CHANGE_PROBABILITY) {
                zombie.newMovingDir();
            }
            zombie.move(delta);
            Collision.checkCollision(zombie, player, soundManager); // check if this zombie has collided with the player
        }

        // Bullet movement
        for (int i = 0; i < player.getBullets().size(); i++) {
            Bullet b = player.getBullets().get(i);
            if ((!mapData.isEntityMoveValid(b.x(), b.y(), b)) || !b.active) {
                player.getBullets().remove(i);
                continue;
            }
            Collision.checkBulletCollision(i, player.getBullets(), zombies, player, soundManager);
            b.move(delta);
            // System.out.println("bullet " + i + " at " + b.getX() + ", " + b.getY());
        }

        int newNumConvertedZombies = 0;
        for (int i = 0; i < zombies.size(); i++) {
            if (zombies.get(i).health <= 0) {
                zombies.remove(i);
                i--;
            }
            if(zombies.get(i).getState() == Zombie.State.PLAYER) {;
            	newNumConvertedZombies += 1;
            }
        }
        player.setNumConvertedZombies(newNumConvertedZombies);

		if(player.health <= -100) {
			currentState = STATE.END;
			System.out.println("GAME OVER");
		}
	}

    private void init() {
        // Create the map and parse it
        mapData = new MapData("prototypemap.png", "tilesheet.png", "tiledata.csv");

        // Initialise the entities
        zombies = new ArrayList<>(zombieCount);
        try {
            player = new Player(0.0f, 0.0f, ResourceLoader.playerImage(), mapData);

            // Create zombieCount zombies and place them all at 50, 50 on the mapData TODO change this
            for (int i = 0; i < zombieCount; i++) {

            	// Daniel does some random stuff here... (like speaking in the third person)

				Random rand = new Random();
				float x = (float) (0.5-rand.nextFloat())*mapData.getWidth();
				float y = (float) (0.5-rand.nextFloat())*mapData.getHeight();

				while(mapData.tileTypeAt(x,y).isObstacle()){
					x = (float) (0.5-rand.nextFloat())*mapData.getWidth();
					y = (float) (0.5-rand.nextFloat())*mapData.getHeight();
				}

                zombies.add(new Zombie(x,y, ResourceLoader.zombieImage(), ResourceLoader.zombiePlayerImage(), mapData));
                zombies.get(i).newMovingDir();
            }
        } catch (IOException e) {
            System.out.println("Uh oh. Player image failed to load. RIP");
            System.exit(1);
        }
    }

    public Player getPlayer() {
        return player;
    }

    public static void main(String[] args) {
        Game game = new Game();

        // Create and start the game loop over the loop method of the game object.
        // :: is a method reference since loop is an existing method,
        // semantically the same as () -> game.loop() lambda expression.
        Thread gameThread = new Thread(game::loop);
        gameThread.start();
    }

}
