/**
 * 
 */
package game.client;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import javax.sound.sampled.Clip;

import org.junit.Before;
import org.junit.Test;

/**
 * @author ryan-
 *
 */
public class SoundTest {
//--
	Sound sound;
	
	@Before
	public void setUp() {
		sound = new Sound();
	}
	

	//@Test
	//public final void testSound() {
	//	assertNull(sound.run());
	//	sound.running = false;
		
		
		
//	/**
//	 * Test method for {@link game.client.Sound#run()}.
//	 */
	//@Test
	//public final void testRun() {
	//	assert(sound.run());
	//	Sound.running = false;
		
		
	//}

	/**
	 * Test method for {@link game.client.Sound#Sound()}.
	 */
	@Test
	public final void testSound() {
		sound = new Sound();
	}

	/**
	 * Test method for {@link game.client.Sound#update()}.
	 */
	@Test
	public final void testUpdate() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link game.client.Sound#createClip(java.lang.String)}.
	 */
	@Test
	public final void testCreateClip() {
		Clip clip = sound.createClip("src/game/sounds/pistol.wav");
		assertNotNull(clip);
		
	}

	/**
	 * Test method for {@link game.client.Sound#bulletSound(boolean)}.
	 */
	@Test
	public final void testBulletSound() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link game.client.Sound#turnDownVolume(javax.sound.sampled.Clip, java.lang.Float)}.
	 */
	@Test
	public final void testTurnDownVolume() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link game.client.Sound#zombieDeath()}.
	 */
	@Test
	public final void testZombieDeath() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link game.client.Sound#playerHurt()}.
	 */
	@Test
	public final void testPlayerHurt() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link game.client.Sound#zombieSounds()}.
	 */
	@Test
	public final void testZombieSounds() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link game.client.Sound#playMusic()}.
	 */
	@Test
	public final void testPlayMusic() {
		fail("Not yet implemented"); // TODO
	}

}
