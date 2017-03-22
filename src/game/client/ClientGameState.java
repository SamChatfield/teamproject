package game.client;

import game.map.MapData;
import game.util.DataPacket;
import game.util.GameState;
import game.util.SendableState;
import game.util.User;

import java.util.ArrayList;

/**
 * The game state of the client at any one time.
 */
public class ClientGameState extends GameState {
	
	private User user;
	private String otherPlayerName;
	private Sound soundManager;

	/**
	 * Create a new ClientGameState
	 * @param user Username of local user
	 */
	public ClientGameState(User user){
		this.user = user;
		this.mapImage = null;
		this.isConnected = false;
		this.bullets = new ArrayList<>();
		this.zombieDataPackets = new ArrayList<>();
        this.bulletDataPackets = new ArrayList<>();
        this.powerups = new ArrayList<>();
	}

	/**
	 * Add sound manager to this state
	 * @param sound Sound object
	 */
	public void addSoundManager(Sound sound){
		this.soundManager = sound;
	}

	/**
	 * Update the state
	 * @param updatedState New state containing information to update
	 */
	public void updateClientState(SendableState updatedState){

		if(!isConnected){
			this.mapImage = updatedState.getMapImage();

			// We need to figure out if the server thinks we are player 1 or 2.
			if(updatedState.getPlayer1().getUsername().equals(user.getUsername())){ // we are player 1 to the server
				otherPlayerName = updatedState.getPlayer2().getUsername();
			}else{
				otherPlayerName = updatedState.getPlayer1().getUsername();
			}

			setUpGame(updatedState.getPlayer(user.getUsername()),updatedState.getPlayer(otherPlayerName));

		}

//		this.bullets = updatedState.getBullets();
		//this.hasFinished = updatedState.HasFinished();

		if(player1.getHealth() > updatedState.getPlayer(user.getUsername()).getHealth()){
			soundManager.playerHurt();
		}

		player1.updateLocalPlayerData(updatedState.getPlayer(user.getUsername()));
		player2.updateData(updatedState.getPlayer(otherPlayerName));

		this.zombieDataPackets = updatedState.getZombies();
        this.bulletDataPackets = updatedState.getBullets();
        this.powerups = updatedState.getPowerups();

		updateTime(updatedState.getTimeRemaining());
	}

	/**
	 * Setup the game
	 * @param p1 DataPacket containing information on Player 1
	 * @param p2 DataPacket containing information on Player 2
	 */
	private void setUpGame(DataPacket p1, DataPacket p2){
		setUpMapData(mapImage);

		// Set up two player objects that we can update later.
		this.player1 = new Player(0,0,mapData,user.getUsername());
		this.player2 = new Player(0,0,mapData,null); // We'll set this later

		// We can reliably update each player locally without knowing which order they were sent in by the server.
		player1.updateData(p1);
		player2.updateData(p2);

		//System.out.println(player1.getX());
		//System.out.println(player1.getY());

		isConnected = true; // we've got our first state send from the server. We are now connected and ready to receive states.
	}

	/**
	 * Set up map data using a string to the map image
	 * @param mapImage String to map image
	 */
	public void setUpMapData(String mapImage){
		this.mapImage = mapImage;
		mapData = new MapData(mapImage, "tilesheet.png", "tiledata.csv");
	}

	/**
	 * Finds the player that this state is local to.
	 * @return The local player object
	 */
	public Player getPlayer() {
		if(player1.getUsername().equals(user.getUsername())){
			return player1;
		}else if(player2.getUsername().equals(user.getUsername())) {
			return player2;
		}
		return null;
	}

	/**
	 * Finds the other player
	 * @return The other player object
	 */
	public Player getOtherPlayer() {
		if(player1.getUsername().equals(user.getUsername())){
			return player2;
		}else if(player2.getUsername().equals(user.getUsername())) {
			return player1;
		}
		return null;
	}
}