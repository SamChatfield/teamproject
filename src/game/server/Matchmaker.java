package game.server;

import game.util.User;

import java.util.ArrayList;

/**
 * Created by jonwoodburn on 21/03/17.
 * Class to match players that are both waiting and have the same difficulty.
 */
public class Matchmaker extends Thread {

    private ClientTable table;

    public Matchmaker(ClientTable table) {
        this.table = table;
    }

    public void run() {
        while (true) {
            ArrayList<User> players = table.checkAvailable();
            while (players.size() != 0 && players.size() % 2 == 0) {
                players = table.checkAvailable();
                for (int i = 0; i < players.size(); i++) {
                    User player1 = players.get(i);
                    for (int j = i + 1; j < players.size(); j++) {
                        User player2 = players.get(j);
                        System.out.println(player1.getUsername());
                        System.out.println(player2.getUsername());
                        if (player1.getDifficulty() == player2.getDifficulty()) {
                            System.out.println("Matching " + player1.getUsername() + " and " + player2.getUsername());
                            ServerGameState state = new ServerGameState(player1.getUsername(), player2.getUsername(), player1.getDifficulty());
                            state.startNewGame();
                            // Update the state that both players have to a new one
                            player1.getServerReceiver().updateState(state);
                            player1.getServerSender().startNewGame(state);
                            player2.getServerReceiver().updateState(state);
                            player2.getServerSender().startNewGame(state);

                            table.changePlayerStatus(player1, ClientTable.playerStatus.IN_GAME);
                            table.changePlayerStatus(player2, ClientTable.playerStatus.IN_GAME);

                            // Matched these players, continue matching others
                            players.remove(i);
                            players.remove(j - 1);
                        }
                    }
                }

                System.out.println("Wahey, while loop is still going buddy");
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
