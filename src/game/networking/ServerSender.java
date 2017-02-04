package game.networking;

import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * @author georgesabourin
 * Class for managing sending from the server to the specified client
 */
public class ServerSender extends Thread {
	
	private ObjectOutputStream objOut;
	
	/**
	 * Constructor method
	 * @param objOut The ObjectOutputStream
	 */
	public ServerSender(ObjectOutputStream objOut) {
		this.objOut = objOut;
	}
	
	/**
	 * Send an object down the ObjectOutputStream to the client
	 * @param obj The object to send
	 */
	private void sendObject(Object obj) {
		try {
			objOut.writeObject(obj);
			objOut.flush();
		} catch (IOException e) {
			System.err.println("Communication Error! " + e.getMessage());
			System.exit(1);
		}
	}
	
	// Main method to run when thread starts
	public void run() {
		while(true) {
            try {
				Thread.sleep(1000);
				sendObject(new SampleObject("IanKenny", 9));
				Thread.sleep(500);
				sendObject(new SampleObject("PaulLevvy", 10));
				System.out.println("Sending objects ...");
			} catch (InterruptedException e) {
				e.printStackTrace();
			} 
		}
	}
}
