package game.server;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import game.client.ClientGameState;
import game.util.User;

public class MatchmakerTest {
	
	ClientTable table;
	Matchmaker m;

	@Before
	public void setUp() throws Exception {

		
	}

	@Test
	public void testMatchmaker() {
		table = new ClientTable();
		m = new Matchmaker(table);

		
		assertNotNull(m);
		
		User a = new User("a",1);
		User b = new User("b",1);
		
		ServerReceiver recA = new ServerReceiver(null,a,table);
		a.setServerReceiver(recA);
		
		ServerReceiver recB = new ServerReceiver(null,b,table);
		b.setServerReceiver(recB);
		
		ServerSender sendA = new ServerSender(null);
		a.setServerSender(sendA);
		
		ServerSender sendB = new ServerSender(null);
		b.setServerSender(sendB);
				
		table.addToTable(a);
		table.addToTable(b);
		
		table.changePlayerStatus(a, game.server.ClientTable.playerStatus.WAITING);
		table.changePlayerStatus(b, game.server.ClientTable.playerStatus.WAITING);

		m.start();
		
		// Wait for matchmaking to happen
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(table.availablePlayers.size(),0);		
		

	}

}
