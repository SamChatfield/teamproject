package game.networking;

import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * @author georgesabourin
 * Class for managing receiving from the client and handling this data
 */
public class ServerReceiver extends Thread {
	
	private ObjectInputStream objIn;
	
	/**
	 * Constructor method
	 * @param objIn The ObjectInputStream
	 */
	public ServerReceiver(ObjectInputStream objIn) {
		this.objIn = objIn;
	}
	
	// Main method to run when thread starts
	public void run() {
		
		while(true) {
			try {
				Object obj = objIn.readObject();
				System.out.println((String)obj);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
}