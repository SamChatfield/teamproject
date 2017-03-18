package game.server;
/**
 * This class will keep track of the connected clients
 */
public class ClientTable {

	/**
	 * Add a user to the client table
	 * @param username Username of the user to add
	 */
	public void addToTable(String username) {
		System.out.println("Added user to queue: " + username);
	}
}