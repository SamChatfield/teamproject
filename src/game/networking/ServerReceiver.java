package game.networking;

import java.io.IOException;
import java.io.ObjectInputStream;

import game.Player;
import game.PlayerData;

/**
 * @author georgesabourin
 * Class for managing receiving from the client and handling this data
 */
public class ServerReceiver extends Thread {

	private GameStateInterface state;
	private ObjectInputStream objIn;
	
	
	// This will be improved later
	private String playerName;
	private int playerZombieCount = 0;
	private int playerHealth = 100;
	
	/**
	 * Constructor method
	 * @param objIn The ObjectInputStream
	 */
	public ServerReceiver(ObjectInputStream objIn, GameStateInterface inter, String playerName) {
		this.objIn = objIn;
		this.state = inter;
		this.playerName = playerName;
	}
	
	// Main method to run when thread starts
	public void run() {
		
		PlayerData playerData;
		
		while(true) {
			try {
				//SampleObject obj = (SampleObject) objIn.readObject();
				//state.insert(obj);
				playerData = (PlayerData) objIn.readObject();
				System.out.println(playerData);
				objIn.reset();
				
				System.out.println(playerName + " health: " + playerData.getHealth());
				
				int currentZombies = playerData.getNumberConvertedZombies();
				int currentHealth = playerData.getHealth();
				
				System.out.println(playerName + "health: " + currentHealth);
				
				// Show updated health and converted zombie count
				if(currentZombies > playerZombieCount) {
					System.out.println(playerName + "has converted " + (currentZombies - playerZombieCount) + " new zombies");
				}
				if(currentHealth < playerHealth) {
					System.out.println(playerName + "'s health has decreased by " + (playerHealth - currentHealth));
				}
				playerHealth = currentHealth;
				playerZombieCount = currentZombies;
				
				
			} catch (ClassNotFoundException e) {
				System.err.println("Error! " + e.getMessage());
				System.exit(1);
			} catch (IOException e) {
				System.err.println("Error! " + e.getMessage());
				System.exit(1);
			}
			
		}
	}
}
