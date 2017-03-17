package game.util;

import game.client.Player;

import java.io.Serializable;

/**
 * Created by Daniel on 17/03/2017.
 * Class for passing information about why the game has ended to each player
 */
public class EndState implements Serializable {

    private boolean hasFinished;
    private String winnerName;

    public boolean hasFinished() {
        return hasFinished;
    }

    public String getWinnerName() {
        return winnerName;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public EndReason getReason() {
        return reason;
    }

    private Player player1;
    private Player player2;
    private EndReason reason;

    public enum EndReason{
        MORE_ZOMBIES, PLAYER_DIED, TIME_EXPIRED
    }

    public EndState(boolean hasFinished, String winnerName, Player player1, Player player2, EndReason reason) {
        this.hasFinished = hasFinished;
        this.winnerName = winnerName;
        this.player1 = player1;
        this.player2 = player2;
        this.reason  = reason;
    }
}
