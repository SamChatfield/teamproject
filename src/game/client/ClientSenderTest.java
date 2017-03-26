package game.client;

import static org.junit.Assert.fail;

import java.io.ObjectOutputStream;

import org.junit.Before;
import org.junit.Test;

import game.util.User;

public class ClientSenderTest {
	
	ClientSender sender;
	User dave;
	ObjectOutputStream objOut;
	ClientGameState gameState;

	@Before
	public void setUp() throws Exception {
		dave = new User("dave", 1);
		objOut = null;
		gameState = new ClientGameState(dave);
		sender = new ClientSender(dave, objOut, gameState);
	}
	
	

	@Test
	public void testAddState() {
		sender.addState(gameState);
	}

}
