package game.client;

import game.ResourceLoader;
import game.util.PlayerUpdatePacket;
import game.util.User;
import game.util.Vector;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.ArrayList;

/**
 * This class is the one that the player will run when they want to start the
 * game. When they click "play" in the menu, it will create a new 'Client'
 * thread that will control input and data send/receive.
 */
public class Client extends Canvas implements KeyListener, MouseListener {

    public static final int VIEW_SIZE = 10; // how many tiles can be seen in the
    // game window e.g. 10 => 10x10 view
    public static final int TILE_SIZE = 64;
    static final Dimension GAME_DIMENSION = new Dimension(640, 640);
    static final Point SCREEN_CENTRE = new Point(GAME_DIMENSION.width / 2, GAME_DIMENSION.height / 2);
    // Game settings
    private static final String TITLE = "Outbreak";
    private static final int TARGET_FPS = 60;
    private static final long OPTIMAL_TIME_DIFF = 1000000000L / TARGET_FPS;
    private static String username;
    private static String ipAddress;
    private static int difficulty;
    public Sound soundManager;
    private JFrame container;
    private BufferStrategy bufferStrategy;
    private boolean running;
    private Player player;
    private boolean[] keyArray, mouseButtonArray;
    private boolean mouseInside;
    private boolean nameCheck = true;
    private ClientGameState state;
    private ClientSender sender;
    private ClientReceiver receiver;
    private Renderer renderer;
    private MenuRenderer menu;
    private STATE currentState;
    private MSTATE menuState;
    private User user;

    /**
     * Create a new Client object
     *
     * @param state  CurrentSlientState object
     * @param sender ClientSender object
     */
    private Client(ClientGameState state, ClientSender sender, ClientReceiver receiver, User user) {
        this.user = user;
        this.state = state;
        this.sender = sender;
        this.receiver = receiver;
        container = new JFrame(TITLE + " - " + user.getUsername());
        JPanel panel = (JPanel) container.getContentPane();
        panel.setPreferredSize(GAME_DIMENSION);
        panel.setLayout(null);

        setBounds(0, 0, GAME_DIMENSION.width, GAME_DIMENSION.height);
        panel.add(this);

        // Set icon on macOS -- code from GitHub gist by bchapuis
        try {
            Class util = Class.forName("com.apple.eawt.Application");
            Method getApplication = util.getMethod("getApplication", new Class[0]);
            Object application = getApplication.invoke(util);
            Class params[] = new Class[1];
            params[0] = Image.class;
            Method setDockIconImage = util.getMethod("setDockIconImage", params);
            setDockIconImage.invoke(application, ResourceLoader.iconImage());
        } catch (Exception e) {
            // log exception
        }

        // Set icon on Windows
        container.setIconImage(ResourceLoader.iconImage());
        setIgnoreRepaint(true);

        container.pack();
        container.setResizable(false);
        container.setVisible(true);
        container.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        container.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                endClient();
            }
        });

        // Handle input
        addMouseListener(this);
        addKeyListener(this);

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

        // Set up input
        keyArray = new boolean[256];
        mouseButtonArray = new boolean[MouseInfo.getNumberOfButtons()];
        mouseInside = false;
        // keyPresses = new ArrayList<>();
    }

    /**
     * Display login prompt allowing to choose a username and the IP address of
     * server
     */
    public static void loginPrompt() {

        JTextField usernameEntry = new JTextField("a");
        JTextField ipaddyEntry = new JTextField("127.0.0.1");
        String[] difficultyStrings = {"Easy", "Medium", "Hard"};
        JComboBox difficultySelection = new JComboBox(difficultyStrings);
        difficultySelection.setSelectedIndex(0);

        difficultySelection.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox cb = (JComboBox) e.getSource();
            }
        });

        Object[] message = {
                "Username (max 10 chars): ", usernameEntry,
                "Server IP Address: ", ipaddyEntry,
                "Level Difficulty: ", difficultySelection
        };

        String regex = "([0-9]+)[.]([0-9])+[.]([0-9])+[.][0-9]+";
        while (username == null || username.length() > 10 || !ipAddress.matches(regex)) {

            int option = JOptionPane.showConfirmDialog(null, message, "Outbreak v1.0", JOptionPane.PLAIN_MESSAGE,
                    JOptionPane.PLAIN_MESSAGE);
            if (option == JOptionPane.OK_OPTION) {
                username = usernameEntry.getText();
                String choice = (String) difficultySelection.getSelectedItem();
                switch (choice) {
                    case "Easy":
                        difficulty = User.EASY;
                        break;
                    case "Medium":
                        difficulty = User.MED;
                        break;
                    case "Hard":
                        difficulty = User.HARD;
                        break;
                }

                if (username.equals("")) {
                    // Default value if no username selected
                    username = "a";
                }
                ;
                ipAddress = ipaddyEntry.getText();

                // Pattern mattern correct format for IP address
                /// XXX.XXX.XXX.XXX
                if (!ipAddress.matches(regex)) {
                    JOptionPane.showMessageDialog(null, "Please format your server IP address as XXX.XXX.XXX.XXX", "Incorrect IP Format", JOptionPane.WARNING_MESSAGE);
                } else if (username.length() > 10) {
                    JOptionPane.showMessageDialog(null, "Username must be no longer than 10 characters", "Username too long", JOptionPane.WARNING_MESSAGE);
                }
                if (ipAddress.equals("")) {
                    // Default value if no server address entered
                    ipAddress = "localhost";
                }
            } else {
                System.out.println("Game login cancelled");
                System.exit(0);
            }
        }

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
            outSocket = new Socket(ipAddress, 4444);
            objOut = new ObjectOutputStream(outSocket.getOutputStream());
            objIn = new ObjectInputStream(outSocket.getInputStream());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Server offline!", "Server hasn't been started",
                    JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
        User newUser = new User(username, difficulty);
        // ClientSender and ClientReceiver objects to handle communication with
        // server
        ClientSender clientSender = new ClientSender(newUser, objOut, null);
        ClientReceiver clientReceiver = new ClientReceiver(newUser, objIn);

        // Then create a client state for the client
        ClientGameState state = new ClientGameState(newUser);

        clientReceiver.addState(state); // Must be called before starting the
        // thread.
        clientSender.addState(state);
        // If this method didn't exist, stateface would need to be added above,
        // but stateface relies on receiver.

        // Starting threads
        clientSender.start();
        clientReceiver.start();

        Client client = new Client(state, clientSender, clientReceiver, newUser);

        // Create and start the client loop over the loop method of the client
        // object.
        // :: is a method reference since loop is an existing method,
        // semantically the same as () -> client.loop() lambda expression.
        Thread gameThread = new Thread(client::loop);
        gameThread.start();
    }

    /**
     * Main game loop that handles updating and drawing
     */
    private void loop() {

        // Renders the menu
        menu = new MenuRenderer(bufferStrategy);
        renderer = new Renderer(bufferStrategy, state);

        // System time
        long lastLoopTime = System.nanoTime();

        // Starts sound playing
        soundManager.start();

        while (running) {


            // Displays the menu or options screen
            while (currentState == STATE.START) {
                if (menuState == MSTATE.MAIN) {
                    menu.renderMenu();
                } else if (menuState == MSTATE.HOPTIONS) {
                    menu.renderHelpOptions();
                }
                // menuUpdate(menu);
            }

            // Starts the game once play button is clicked
            while (currentState == STATE.GAME) {


                if (!state.isConnected()) {
                    while (!state.isConnected()) {
                        // System.out.println("Client state: " +
                        // state.isConnected());
                        renderer.renderWaitingForOpponent();
                        try {
                            Thread.sleep(200);
                        } catch (Exception e) {

                        }
                    }
                }

                // Get current state of player
                this.player = state.getPlayer();

                if (nameCheck) {
                    try {
                        System.out.println(player.getUsername());
                        container.setTitle("Outbreak - " + player.getUsername());
                        nameCheck = false;
                    } catch (NullPointerException e) {
                    }
                }


                soundManager.addPlayer(this.player);

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

                // We want each frame to be the active frame for
                // OPTIMAL_TIME_DIFF nanoseconds to give 60 FPS
                // So if the difference between now and the start of this loop
                // (now assigned to lastLoopTime ready for the
                // next loop) is less than this optimal time then we need to
                // sleep the thread for the remaining time to fix
                // at 60 FPS
                now = System.nanoTime();
                if (now - lastLoopTime < OPTIMAL_TIME_DIFF) {
                    try {
                        Thread.sleep((lastLoopTime - now + OPTIMAL_TIME_DIFF) / 1000000);
                    } catch (InterruptedException e) {
                        System.err.println("Client loop state interupted exception: " + e.getMessage());
                        e.printStackTrace();

                    }
                }

                // Is the game over?
                if (state.HasFinished()) {
                    currentState = STATE.END;

                }
            }

            // If the game is over then we can pass the end state into the
            // renderer.
            while (currentState == STATE.END) {
                renderer.renderGameOver(state.getEndState());
                // gameOverUpdate();
            }
        }
        System.exit(0);
    }

    /**
     * Runs during the game to update the displayed game state.
     *
     * @param delta
     */
    private void update(double delta) {


        // Lets store every keypress we see this tick
        ArrayList<String> keyPresses = new ArrayList<>();
        // Handle player keyboard input to move
        if (isKeyDown(KeyEvent.VK_W)) {
            keyPresses.add("VK_W");
        }
        if (isKeyDown(KeyEvent.VK_A)) {
            keyPresses.add("VK_A");
        }
        if (isKeyDown(KeyEvent.VK_D)) {
            keyPresses.add("VK_D");
        }
        if (isKeyDown(KeyEvent.VK_S)) {
            keyPresses.add("VK_S");
        }


        //Toggle Weapons
        if (isKeyDown(KeyEvent.VK_1)) {
            keyPresses.add("VK_1");
        }
        if (isKeyDown(KeyEvent.VK_2)) {
            keyPresses.add("VK_2");
        }
        if (isKeyDown(KeyEvent.VK_3)) {
            keyPresses.add("VK_3");
        }
        if (isKeyDown(KeyEvent.VK_4)) {
            keyPresses.add("VK_4");
        }
        if (isKeyDown(KeyEvent.VK_5)) {
            keyPresses.add("VK_5");
        }


        // Toggle conversion mode
        if (isKeyDown(KeyEvent.VK_Z)) {
            keyPresses.add("VK_Z");
            System.out.println("Enabled conversion mode!");
        }
        if (isKeyDown(KeyEvent.VK_X)) {
            keyPresses.add("VK_X");
            System.out.println("Disabled conversion mode!");
        }

        // Face the player in the direction of the mouse postate
        Point mousePos = this.getMousePosition();
        Vector fv = null;
        if (mouseInside && mousePos != null) {
            fv = new Vector(mousePos.x - 320, 320 - mousePos.y).normalised();

            if (isMouseButtonDown(MouseEvent.BUTTON1)) {
                keyPresses.add("BUTTON1");
                soundManager.bulletSound();
            }
        }
        // We need to do this in case fv is null
        float x = -100;
        float y = -100;
        if (fv != null) {
            x = fv.x();
            y = fv.y();
        }

        sender.sendObject(new PlayerUpdatePacket(player.getData(), keyPresses, delta, x, y)); // We
        // send
        // an
        // object
        // to
        // the
        // server
        // every
        // tick.

        updateLocalPlayer(keyPresses, delta, fv);
    }

    /**
     * Update player status locally
     *
     * @param keyPresses ArrayList of strings that represent key presses
     * @param delta      Interpolation
     * @param fv         Movement for x and y
     */
    private void updateLocalPlayer(ArrayList<String> keyPresses, double delta, Vector fv) {

        Vector pdv = new Vector(0.0f, 0.0f); // Player direction vector for this
        // update
        for (String s : keyPresses) {
            switch (s) {
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
                case "BUTTON1":
                    // this should still happen on the server
                    break;
            }
        }
        if (fv != null) {
            player.face(fv.x(), fv.y());
        }
        Vector pnv = pdv.normalised(); // Player normal direction vector for
        // this update
        float pdx = pnv.x() * player.getMoveSpeed() * ((float) delta); // Actual
        // change
        // in x
        // this
        // update
        float pdy = pnv.y() * player.getMoveSpeed() * ((float) delta); // Actual
        // change
        // in y
        // this
        // update

        player.move(pdx, pdy, state.getOtherPlayer());

    }

    /**
     * Get the player object
     *
     * @return Player object
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * End the client by sending a message to the server and closing all streams.
     */

    public void endClient() {
        sender.sendObject("Bye");
        sender.closeStream();
        receiver.closeStream();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keyArray[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {


        // Toggle sound
        if (e.getKeyCode() == KeyEvent.VK_M) {
            Sound.sfxPlayback = !Sound.sfxPlayback;
            Sound.musicPlayback = !Sound.musicPlayback;
            System.out.println("sfx: " + Sound.sfxPlayback);
        }
        // Print player's position
        else if (e.getKeyCode() == KeyEvent.VK_P) {
            System.out.println("Player: (" + player.getX() + ", " + player.getY() + ")");
        }
        keyArray[e.getKeyCode()] = false;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        double mx = this.getMousePosition().getX();
        double my = this.getMousePosition().getY();

        // If we're on the menu
        if (currentState == STATE.START) {
            // If we're on the main menu
            if (menuState == MSTATE.MAIN) {
                // If play button clicked
                if (menu.playButton.contains(mx, my)) {
                    soundManager.playPressed();
                    currentState = STATE.GAME;
                    menuState = MSTATE.NONE;
                    sender.sendObject("Waiting");
                }
                // If help button clicked
                else if (menu.helpOptionsButton.contains(mx, my)) {
                    soundManager.buttonPressed();
                    menuState = MSTATE.HOPTIONS;
                }
            }
            // If we're on the help/options menu
            else if (menuState == MSTATE.HOPTIONS) {
                // If return button clicked
                if (menu.returnButton.contains(mx, my)) {
                    soundManager.buttonPressed();
                    menuState = MSTATE.MAIN;
                }
                // If sfx button clicked
                else if (menu.sfxButton.contains(mx, my)) {
                    soundManager.buttonPressed();
                    Sound.sfxPlayback = !Sound.sfxPlayback;
                }
                // If music button clicked
                else if (menu.musicButton.contains(mx, my)) {
                    soundManager.buttonPressed();
                    Sound.musicPlayback = !Sound.musicPlayback;
                }
            }
        }
        // If we're on the game over screen
        else if (currentState == STATE.END) {
            // If menu button was clicked
            if (renderer.menuButton.contains(mx, my)) {
                currentState = STATE.START;
                menuState = MSTATE.MAIN;
                soundManager.playPressed();
                // Resets the state, and sets HasFinished and Connected to
                // false. This allows a new initial state
                // for a new game
                state.resetState(user);
                state.setHasFinished(false);
                state.setConnected(false);
            }
            // If exit button was clicked
            else if (renderer.exitButton.contains(mx, my)) {
                endClient();
                soundManager.buttonPressed();
                currentState = STATE.EXIT;
                running = false;
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        mouseButtonArray[e.getButton()] = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mouseButtonArray[e.getButton()] = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        mouseInside = true;
    }

    @Override
    public void mouseExited(MouseEvent e) {
        mouseInside = false;
    }

    /**
     * Get if a current key is down/pressed on the keyboard
     *
     * @param keyCode Key to check
     * @return Whether key is pressed on keyboard
     */
    public boolean isKeyDown(int keyCode) {
        return keyArray[keyCode];
    }

    /**
     * Check if mouse button is down
     *
     * @param button Button to check
     * @return Whether mouse button is currently down/clicked
     */
    public boolean isMouseButtonDown(int button) {
        return mouseButtonArray[button];
    }

    // Client state
    private enum STATE {
        START, GAME, END, EXIT;
    }

    // Menu state
    private enum MSTATE {
        MAIN, HOPTIONS, NONE;
    }

}
