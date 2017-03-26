package game.client;

import static org.junit.Assert.*;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import game.util.User;

public class ClientTest {
	
	User user;
	ObjectOutputStream objOut;
	ObjectInputStream objIn;
	Client c;
	ClientGameState gameState;

	ClientGameState state = new ClientGameState(user);
	ClientSender sender = new ClientSender(user, objOut, state);
	ClientReceiver receiver = new ClientReceiver(user, objIn);

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		User u = new User("ryan",1);
		gameState = new ClientGameState(u);
		//c.loginPrompt();
		

	}

	@Test
	public void testLoginPrompt() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetPlayer() {
		fail("Not yet implemented");
	}

	@Test
	public void testEndClient() {
		fail("Not yet implemented");
	}

	@Test
	public void testMain() {
		fail("Not yet implemented");
	}

	@Test
	public void testKeyTyped() {
		fail("Not yet implemented");
	}

	@Test
	public void testKeyPressed() {
		fail("Not yet implemented");
	}

	@Test
	public void testKeyReleased() {
		fail("Not yet implemented");
	}

	@Test
	public void testMouseClicked() {
		fail("Not yet implemented");
	}

	@Test
	public void testMousePressed() {
		fail("Not yet implemented");
	}

	@Test
	public void testMouseReleased() {
		fail("Not yet implemented");
	}

	@Test
	public void testMouseEntered() {
		fail("Not yet implemented");
	}

	@Test
	public void testMouseExited() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsKeyDown() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsMouseButtonDown() {
		fail("Not yet implemented");
	}

}
