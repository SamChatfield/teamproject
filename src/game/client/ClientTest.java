/**
 * 
 */
package game.client;

import static org.junit.Assert.*;

import java.io.ObjectOutputStream;

import org.junit.Test;

import game.util.User;

/**
 * @author ryan-
 *
 */

//--
public class ClientTest {
User user;
ObjectOutputStream objOut;
ClientGameState state = new ClientGameState(user);
ClientSender sender = new ClientSender(user, objOut, state);

	Client client;// = new Client(state, sender, user);
	/**
	 * Test method for {@link game.client.Client#loginPrompt()}.
	 */
	@Test
	public final void testLoginPrompt() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link game.client.Client#getPlayer()}.
	 */
	@Test
	public final void testGetPlayer() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link game.client.Client#main(java.lang.String[])}.
	 */
	@Test
	public final void testMain() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testUpdate() {
		fail("Not yet implemented"); // TODO
	}

}
