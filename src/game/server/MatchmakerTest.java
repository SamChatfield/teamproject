package game.server;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

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
	}

}
