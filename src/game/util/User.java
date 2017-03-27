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
	public static final int EASY =1;
	public static final int MED =2;
	public static final int HARD =3;

	public ServerSender serverSender;
	public ServerReceiver serverReceiver;

	public User(String username, int difficulty){
		this.username = username;
		this.difficulty = difficulty;

	}

	public int getDifficulty() {
		return difficulty;
	}

	public String getUsername() { return username; }

	public void setUsername(String username) { this.username = username; }

	public void setServerSender(ServerSender sender) {
		serverSender = sender;
	}

	public void setServerReceiver(ServerReceiver receiver) {
		serverReceiver = receiver;
	}

	public ServerSender getServerSender() {
		return serverSender;
	}

	public ServerReceiver getServerReceiver() {
		return serverReceiver;
	}
}
