package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.io.IOException;

/**
 * Created by Sam on 20/01/2017.
 */
public class Game extends Canvas {

    static final String TITLE = "Team Project";
    static final Dimension GAME_DIMENSION = new Dimension(640, 640);
    static final double REFRESH_HZ = 30.0;
    static final double NS_BETWEEN_UPDATES = 1000000000 / REFRESH_HZ;
    static final int MAX_UPDATES_BETWEEN_RENDER = 5;
    static final double TARGET_FPS = 60.0;
    static final double TARGET_NS_BETWEEN_RENDER = 1000000000 / TARGET_FPS;

    private JFrame container;
    private BufferStrategy bufferStrategy;
    private InputHandler inputHandler;
    private boolean running;
    private Entity player;
    private Entity[] zombies;

    public Game() {
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

//        init();
    }

    private void loop() {
        init();
        Renderer renderer = new Renderer(bufferStrategy, player);

        double lastUpdate = System.nanoTime();
        double lastRender = System.nanoTime();

        int lastSecondTime = (int) (lastUpdate / 1000000000);

        while (running) {
            System.out.println("loop de loop");
            double now = System.nanoTime();
            int updateCounter = 0;

            // Do as many updates as needed, maybe catching up
            while( now - lastUpdate > NS_BETWEEN_UPDATES && updateCounter < MAX_UPDATES_BETWEEN_RENDER ) {
                update();
                lastUpdate += NS_BETWEEN_UPDATES;
                updateCounter++;
            }

            // REMOVED SOMETHING FROM EXAMPLE

            // Render
            System.out.println("render thyme");
            float interpolation = Math.min(1.0f, (float) ((now - lastUpdate) / NS_BETWEEN_UPDATES));
            renderer.render(interpolation);
            lastRender = now;

            // Yield thread
            while ( now - lastRender < TARGET_NS_BETWEEN_RENDER && now - lastUpdate < NS_BETWEEN_UPDATES) {
                Thread.yield();

                //This stops the app from consuming all your CPU. It makes this slightly less accurate, but is worth it.
                //You can remove this try block and it will still work (better), your CPU just climbs on certain OSes.
                //FYI on some OS's this can cause pretty bad stuttering. Scroll down and have a look at different peoples' solutions to this.
//                try {
//                    Thread.sleep(1);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                    System.out.println("loop thread interrupted exception");
//                }

                now = System.nanoTime();
            }
        }
    }

    private void loopvariable() {
        init();
        Renderer renderer = new Renderer(bufferStrategy, player);

        long lastLoopTime = System.nanoTime();
        final int targetFPS = 60;
        final long optimalTime = 1000000000L / targetFPS;

        while (running) {
            // Calculate how long since last update
            // Delta is how far things should move this update to compensate
            long now = System.nanoTime();
            long updateLength = now - lastLoopTime;
            lastLoopTime = now;
            double delta = updateLength / ((double) optimalTime);

            // FPS counter stuff if using TODO Add an option for this

            // Update game with the delta
            update(delta);

            // Render
            renderer.render();

            // We want each frame to be the active frame for optimalTime nanoseconds to give 60 FPS
            // So if the difference between now and the start of this loop (now assigned to lastLoopTime ready for the
            // next loop) is less than this optimal time then we need to sleep the thread for the remaining time to fix
            // at 60 FPS
            now = System.nanoTime();
            if (now - lastLoopTime < optimalTime) {
                try {
                    Thread.sleep((lastLoopTime - now + optimalTime) / 1000000);
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
//        player.move(delta);
        // Player movement
        float pdx = 0;
        float pdy = 0;
        float pMoveSpeed = player.getMoveSpeed();
        
        // Change the player movement speed with 1 and 2
        if (inputHandler.isKeyDown(KeyEvent.VK_1)) {
            player.setMoveSpeed(pMoveSpeed -= 0.1f);
        }
        if (inputHandler.isKeyDown(KeyEvent.VK_2)) {
            player.setMoveSpeed(pMoveSpeed += 0.1f);
        }

        if (inputHandler.isKeyDown(KeyEvent.VK_W)) {
            pdy -= pMoveSpeed * delta;
        }
        if (inputHandler.isKeyDown(KeyEvent.VK_A)) {
            pdx -= pMoveSpeed * delta;
        }
        if (inputHandler.isKeyDown(KeyEvent.VK_D)) {
            pdx += pMoveSpeed * delta;
        }
        if (inputHandler.isKeyDown(KeyEvent.VK_S)) {
            pdy += pMoveSpeed * delta;
        }
        player.move(pdx, pdy);
    }

    public static void main(String[] args) {
//        JFrame f = new JFrame("Team Project");
//
//        JPanel panel = (JPanel) f.getContentPane();
//        panel.setPreferredSize(new Dimension(640, 640));
//        panel.setLayout(null);
//
//        Game game = new Game(panel);
//
//        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//        f.add(panel);
//        f.pack();
//        f.setResizable(false);
//        f.setVisible(true);
//        game.init();
        Game game = new Game();
        Thread gameThread = new Thread(() -> game.loopvariable());
        gameThread.start();
    }
}
