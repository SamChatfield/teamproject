package game.server;

import game.util.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * This class will keep track of the connected clients
 */
public class ClientTable {

	HashMap clientTable;
	ArrayList<User> availablePlayers;

	/**
	 * Constructor creates a hashmap: the key is the username, the value is a bool
	 * False means that the user is not in a game, true means they are.
	 */
	ClientTable() {
		clientTable = new HashMap();
	}

	/**
	 * Add a user to the table
	 * @param username Username to add
	 */
	public enum playerStatus {NOT_PLAYING, WAITING, IN_GAME }

	public void addToTable(User username) {
		clientTable.put(username, playerStatus.NOT_PLAYING);
	}

	public ArrayList<User> checkAvailable() {
		availablePlayers = new ArrayList<>();
		Iterator<?> itr = clientTable.entrySet().iterator();
		while(itr.hasNext()) {
			HashMap.Entry pair = (HashMap.Entry)itr.next();
			User aUser = (User) pair.getKey();
			System.out.println(aUser.getUsername() + ", " + pair.getValue());
			if((playerStatus)pair.getValue() == playerStatus.WAITING) {
				availablePlayers.add(aUser);
			}
		}
		return availablePlayers;
	}

	public void changePlayerStatus(User username, playerStatus status) {
		clientTable.put(username, status);
	}
}