import game.*;
import game.client.ClientGameState;
import game.client.Player;
import game.server.ServerGameState;
import game.util.EndState;
import game.util.SendableState;
import game.util.User;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

public class RendererTest extends Canvas {

    game.client.Renderer r;
    ClientGameState cgs;
    // End state stuff
    EndState.EndReason endReason = EndState.EndReason.PLAYER_DIED;
    EndState.EndReason endReason1 = EndState.EndReason.TIME_EXPIRED;
    Player player = new Player(1, 1, null, "ryan");
    Player opponent = new Player(2, 2, null, "becca");
    Bullet bullet = new Bullet(player, 1, 1, 1, 1, null);
    Entity entity = new Entity(1, 1, 0.3f, 50, null, null);
    EndState endState = new EndState(true, "ryan", player, opponent, endReason);
    EndState endState1 = new EndState(true, "ryan", player, opponent, endReason1);
    ServerGameState servState = new ServerGameState("ryan", "becca", 1);
    SendableState sState;
    Player player1 = new Player(1, 1, null, "ryan");
    Player player2 = new Player(1, 1, null, "becca");
    ArrayList<Bullet> b = new ArrayList<>();
    Entity entityPU = new Entity(1, 1, null);
    ArrayList<Zombie> zombies = new ArrayList<>();
    private JFrame container;
    private Graphics2D g2d;

    @Before
    public void setUp() throws Exception {
        servState.startNewGame();

        b.add(bullet);

        Zombie zombie = new Zombie(1, 1, servState.getMapData(), 0);
        Zombie zombie2 = new Zombie(1, 1, servState.getMapData(), 0);

        zombies.add(zombie);
        zombie2.setAlive(false, 1); //for testing "drawDeadZombie"
        zombies.add(zombie2);

        servState.setZombies(zombies);
        servState.setBullets(b);

        ArrayList<Weapon> weapons = new ArrayList<>();
        Weapon w = new Weapon(0, 0, servState.getMapData(), Weapon.randomW(), System.nanoTime());
        weapons.add(w);
        servState.setWeapons(weapons);

        ArrayList<PowerUp> powerup = new ArrayList<>();
        PowerUp p = new PowerUp(0, 0, servState.getMapData(), PowerUp.randomPU(), System.nanoTime());
        powerup.add(p);
        servState.setPowerUp(powerup);


        sState = servState.getPackagedState();


        User u = new User("ryan", 1);
        ClientGameState g = new ClientGameState(u);
        g.updateClientState(sState);

        container = new JFrame("test");
        JPanel panel = (JPanel) container.getContentPane();
        panel.setPreferredSize(new Dimension(640, 640));
        panel.setLayout(null);

        setBounds(0, 0, 640, 640);
        panel.add(this);


        setIgnoreRepaint(true);

        container.pack();
        container.setResizable(false);
        container.setVisible(true);
        container.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        createBufferStrategy(2);
        BufferStrategy bs = getBufferStrategy();

        g2d = (Graphics2D) bs.getDrawGraphics();


        r = new game.client.Renderer(bs, g);
    }


    @Test
    public void testRender() {
        r.render();
    }

    @Test
    public void testRenderWaitingForOpponent() {
        r.renderWaitingForOpponent();
    }

    @Test
    public void testRenderGameOver() {
        r.renderGameOver(endState);
    }

    @Test
    public void testDrawLighting() {
        r.drawLighting(g2d);
    }

    @Test
    public void testDrawMap() {
        r.drawMap(g2d, servState.getMapData(), player);
    }

}
