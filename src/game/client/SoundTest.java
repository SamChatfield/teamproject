package game.client;

import static org.junit.Assert.*;

import javax.sound.sampled.Clip;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SoundTest {
	
	Sound sound;
	
	@Before
	public void setUp() throws Exception {
		sound = new Sound();
		sound.addPlayer(new Player(0, 0, null, null));

	}

	@Test
	public void testRun() {
		sound.start();
		
	
	}

	@Test
	public void testUpdate() {
		sound.update();
	}

	

	@Test
	public void testBulletSound() {
		sound.bulletSound();
	}

	@Test
	public void testButtonPressed() {
		sound.buttonPressed();
	}

	@Test
	public void testPlayPressed() {
		sound.playPressed();
	}

	@Test
	public void testTurnDownVolume() {
		Clip zombie = sound.createClip("src/game/sounds/zombieDeath.wav");

		sound.turnDownVolume(zombie, 0f);
	}

	@Test
	public void testZombieDeath() {
		sound.zombieDeath();
	}

	@Test
	public void testPlayerHurt() {
		sound.playerHurt();
	}

	@Test
	public void testZombieSounds() {
		sound.zombieSounds();
	}


	@Test
	public void testPlayMusic() {
		sound.playMusic();
	}

	@After
	public void tearDown() throws Exception {
		sound.stop();
	}

}
