package game;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Sam on 20/01/2017.
 */
public class Game extends JPanel {

    public Game() {
        setPreferredSize(new Dimension(640, 640));
    }

    public static void main(String[] args) {
        JFrame f = new JFrame("Team Project");
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        Game game = new Game();

        f.add(game);
        f.pack();
        f.setResizable(false);
        f.setVisible(true);
    }

}
