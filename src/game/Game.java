package game;

import game.util.Vector;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.io.IOException;

/**
 * Created by Sam on 20/01/2017.
 */
public class Game extends Canvas {

    private static final String TITLE = "Team Project";
    static final Dimension GAME_DIMENSION = new Dimension(640, 640);
    private static final int TARGET_FPS = 60;
    private static final long OPTIMAL_TIME_DIFF = 1000000000L / TARGET_FPS;

    private JFrame container;
    private BufferStrategy bufferStrategy;
    private InputHandler inputHandler;
    private boolean running;
    private Entity player;
    private Entity[] zombies;

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

        running = true;
    }

    private void loop() {
        init();
        Renderer renderer = new Renderer(bufferStrategy, player);

        long lastLoopTime = System.nanoTime();

        while (running) {
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
                    System.out.println("Game loop interrupted exception");
                }
            }
        }
    }

    private void init() {
        try {
            player = new Player(0.0f, 0.0f, ResourceLoader.playerImage());
        } catch (IOException e) {
            System.out.println("Uh oh. Player image failed to load. RIP");
            System.exit(1);
        }
    }

    private void update() {
        player.move(1.0f, 1.0f);
    }

    private void update(double delta) {
        System.out.println("d: " + delta);
        // Player movement
        float pMoveSpeed = player.getMoveSpeed();

        // Change the player movement speed with 1 and 2
        if (inputHandler.isKeyDown(KeyEvent.VK_1)) {
            player.setMoveSpeed(pMoveSpeed -= 0.1f);
        }
        if (inputHandler.isKeyDown(KeyEvent.VK_2)) {
            player.setMoveSpeed(pMoveSpeed += 0.1f);
        }

        Vector pdv = new Vector(0.0f, 0.0f); // Player direction vector for this update

        // Handle player movement
        if (inputHandler.isKeyDown(KeyEvent.VK_W)) {
            pdv.add(new Vector(0.0f, -1.0f));
        }
        if (inputHandler.isKeyDown(KeyEvent.VK_A)) {
            pdv.add(new Vector(-1.0f, 0.0f));
        }
        if (inputHandler.isKeyDown(KeyEvent.VK_D)) {
            pdv.add(new Vector(1.0f, 0.0f));
        }
        if (inputHandler.isKeyDown(KeyEvent.VK_S)) {
            pdv.add(new Vector(0.0f, 1.0f));
        }

        Vector pnv = pdv.normalise(); // Player normal direction vector for this update
        float pdx = pnv.x() * pMoveSpeed * ((float) delta); // Actual change in x this update
        float pdy = pnv.y() * pMoveSpeed * ((float) delta); // Actual change in y this update
        player.move(pdx, pdy);
    }

    public static void main(String[] args) {
        Game game = new Game();
        Thread gameThread = new Thread(() -> game.loop());
        gameThread.start();
    }
}
