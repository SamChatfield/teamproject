package game.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * This class will keep track of the connected clients
 */
public class ClientTable {
	HashMap<String, playerStatus> clientTable;
	ArrayList<String> availablePlayers;
	public enum playerStatus {NOT_PLAYING, WAITING, IN_GAME }
	
	/**
	 * Constructor creates a hashmap: the key is the username, the value is a bool
	 * False means that the user is not in a game, true means they are.
	 */
	ClientTable() {
		clientTable = new HashMap<String, playerStatus>();
	}
	
	/**
	 * Add a user to the table
	 * @param username Username to add
	 */
	public void addToTable(String username) {
		clientTable.put(username, playerStatus.NOT_PLAYING);
	}
	
	/**
	 * 
	 * @param username
	 * @return
	 */
	public Boolean userExists(String username) {
		Boolean exists = false;
		Iterator<?> itr = clientTable.entrySet().iterator();
		while(itr.hasNext()) {
			HashMap.Entry pair = (HashMap.Entry)itr.next();
			if(pair.getKey().equals(username)) {
				exists = true;
			}
		}
		return exists;
	}
	
	/**
	 * Check available users
	 * @return ArrayList of available users
	 */
	public ArrayList<String> checkAvailable() {
		availablePlayers = new ArrayList<>();
		Iterator<?> itr = clientTable.entrySet().iterator();
		while(itr.hasNext()) {
			HashMap.Entry pair = (HashMap.Entry)itr.next();
			System.out.println(pair.getKey() + ", " + pair.getValue());
			if((playerStatus)pair.getValue() == playerStatus.WAITING) {
				availablePlayers.add((String)pair.getKey());
			}
		}
		return availablePlayers;
	}
	
	/**
	 * Change player status
	 * @param username Username of player to change
	 * @param status New status
	 */
	public void changePlayerStatus(String username, playerStatus status) {
		clientTable.put(username, status);
	}
}