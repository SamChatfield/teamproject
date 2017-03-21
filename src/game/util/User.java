package game.util;

import game.server.ServerReceiver;
import game.server.ServerSender;

import java.io.Serializable;

/**
 * Created by jonwoodburn on 20/03/17.
 */
public class User implements Serializable{

	private String username;
	private int difficulty;
	public static final int EASY =0;
	public static final int MED =1;
	public static final int HARD =2;

	public ServerSender server_sender;
	public ServerReceiver server_receiver;

	public User(String username, int difficulty) {
		this.username = username;
		this.difficulty = difficulty;

	}

	public int getDifficulty() {
		return difficulty;
	}

	public String getUsername() { return username; }

	public void setUsername(String username) { this.username = username; }

	public void setServerSender(ServerSender sender) {
		server_sender = sender;
	}

	public void setServerReceiver(ServerReceiver receiver) {
		server_receiver = receiver;
	}

	public ServerSender getServerSender() {
		return server_sender;
	}

	public ServerReceiver getServerReceiver() {
		return server_receiver;
	}
}
