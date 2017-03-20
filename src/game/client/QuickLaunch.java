package game.client;

import game.server.Server;

/**
* Quick launcher to launch both game and server whilst debugging
 */
public class QuickLaunch {
	
	public static void main(String[] args) throws InterruptedException {
		
		// Create two threads:
		Thread thread1 = new Thread() {
		    public void run() {
				String[] serverArgs = new String[1];
				serverArgs[0] = "4444";
				Server.main(serverArgs); 
		    }
		};

		Thread thread2 = new Thread() {
		    public void run() {
		    	Client.main(args);
		    }
		};

		// Start the downloads.
		thread1.start();
		thread2.start();

		// Wait for them both to finish
		thread1.join();
		thread2.join();

		// Continue the execution...
	}
}