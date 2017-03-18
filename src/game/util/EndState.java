package game.util;

import java.io.Serializable;

import game.client.Player;

/**
 * Class for passing information about why the game has ended to each player
 */
public class EndState implements Serializable {

	private boolean hasFinished;
	private String winnerName;

	private Player player1;
	private Player player2;
	private EndReason reason;

	public enum EndReason{
		MORE_ZOMBIES, PLAYER_DIED, TIME_EXPIRED
	}

	/** Create new EndState
	 * @param hasFinished Whether game finished
	 * @param winnerName Name of winner of game
	 * @param player1 Player1 object
	 * @param player2 Player2 object
	 * @param reason Reason game ended
	 */
	public EndState(boolean hasFinished, String winnerName, Player player1, Player player2, EndReason reason) {
		this.hasFinished = hasFinished;
		this.winnerName = winnerName;
		this.player1 = player1;
		this.player2 = player2;
		this.reason  = reason;
	}

	/**
	 * Whether the game has finished
	 * @return Boolean of whether game finised
	 */
	public boolean hasFinished() {
		return hasFinished;
	}

	/**
	 * Get name of player who won the game
	 * @return Name of winner
	 */
	public String getWinnerName() {
		return winnerName;
	}

	/**
	 * Get object for Player 1
	 * @return Player 1 object
	 */
	public Player getPlayer1() {
		return player1;
	}

	/**
	 * Get object for Player 1
	 * @return Player 1 object
	 */
	public Player getPlayer2() {
		return player2;
	}

	/**
	 * Get reason why game ended
	 * @return Reason game ended
	 */
	public EndReason getReason() {
		return reason;
	}
}