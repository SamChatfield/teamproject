package game.networking;

import java.io.ObjectInputStream;

/**
 * @author georgesabourin
 * Class for receiving objects from the server and then determining what to do with them
 */
public class ClientReceiver extends Thread {

	private String username;
	private ObjectInputStream objIn;

	/**
	 * Constructor
	 * @param username Name of user
	 * @param objIn ObjectInputStream
	 */
	ClientReceiver(String username, ObjectInputStream objIn) {
		this.username = username;
		this.objIn = objIn;
	}

	// Main method to run when thread starts
	public void run() {
		
		System.out.println("DEBUG: ClientReceiver started");
		try {
			while(true) {
//				System.out.println("Receiving object");
				SampleObject obj1 = (SampleObject)objIn.readObject();
				if(obj1 != null) {
					System.out.println("Object = " +obj1.toString() + " | value = " + obj1.getValue());
				}
				else {
					System.out.println("ERROR! Object is null!");
				}	
			}
		} catch(Exception e) {
			System.err.println("Error! " + e.getMessage());
		}
	}
}
