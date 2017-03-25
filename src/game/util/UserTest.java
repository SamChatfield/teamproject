/**
 * 
 */
package game.util;

import static org.junit.Assert.*;

import org.junit.Test;

import game.server.ServerReceiver;
import game.server.ServerSender;

//--
/**
 * @author ryan-
 *
 */
public class UserTest {

	User user = new User("ryan", 1); 
	ServerSender sSend = new ServerSender(null);
	ServerReceiver sRec = new ServerReceiver(null, user, null);

	/**
	 * Test method for {@link game.util.User#getDifficulty()}.
	 */
	@Test
	public final void testGetDifficulty() {
		assertEquals(1, user.getDifficulty(), 0.1);
	}

	/**
	 * Test method for {@link game.util.User#getUsername()}.
	 */
	@Test
	public final void testGetUsername() {
		assertEquals("ryan", user.getUsername());
		user.setUsername("becca");
		assertEquals("becca", user.getUsername());
	}


	/**
	 * Test method for {@link game.util.User#setServerSender(game.server.ServerSender)}.
	 */
	@Test
	public final void testSetServerSender() {
		user.setServerSender(sSend);
		assertNotNull(user.getServerSender());
	}

	/**
	 * Test method for {@link game.util.User#setServerReceiver(game.server.ServerReceiver)}.
	 */
	@Test
	public final void testSetServerReceiver() {
		user.setServerReceiver(sRec);
		assertNotNull(user.getServerReceiver());
	}


}
