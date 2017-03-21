package game.server;

import game.util.User;

import java.util.ArrayList;

/**
 * Created by jonwoodburn on 21/03/17.
 */
public class Matchmaker extends Thread{

	private ClientTable table;

	public Matchmaker(ClientTable table) {
		this.table = table;
	}

	public void run() {
		while(true) {
			ArrayList<User> players = table.checkAvailable();
			while(players.size() != 0 && players.size() %2 == 0) {

				for(int i =0; i<players.size(); i++) {
					User player1 = players.get(i);
					for(int j= i+1; j<players.size(); j++) {
						User player2 = players.get(j);
						if(player1.getDifficulty() == player2.getDifficulty()) {

							ServerGameState state = new ServerGameState(player1.getUsername(), player2.getUsername(), player1.getDifficulty());
							player1.getServerReceiver().updateState(state);
							player1.getServerSender().updateState(state);
							player2.getServerReceiver().updateState(state);
							player2.getServerSender().updateState(state);
							state.setReady(true);
							table.changePlayerStatus(player1, ClientTable.playerStatus.IN_GAME);
							table.changePlayerStatus(player2, ClientTable.playerStatus.IN_GAME);
							players.remove(i);
							players.remove(j);
						}
					}
				}

				System.out.println("Wahey, while loop is still going buddy");
			}
			try {
				Thread.sleep(500);
			} catch(InterruptedException e) {
				e.printStackTrace();
			}

		}
	}
}
