package game.client;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Random;

/**
 * Class that manages sound, mainly music and the game's sound effects.
 */
public class Sound extends Thread{
	private String pistolSound = "src/game/sounds/pistol.wav";
	private String shotgunSound = "src/game/sounds/shotgun.wav";
	private String music = "src/game/sounds/music.wav";
	private String zombieDeath = "src/game/sounds/zombieDeath.wav";
	private String zombieSound ="src/game/sounds/zombieSound";
	private String hurt = "src/game/sounds/hit.wav";
	private String buttonPressed = "src/game/sounds/buttonPressed.wav";
	private String playPressed = "src/game/sounds/playPressed.wav";
	private boolean running;
	private Clip musicClip;
	private static final float ZOMBIE_SOUND_PROBABILITY = 0.01f;
	Random rn, oneTwoOrThree;
	public static boolean musicPlayback = true;
	public static boolean sfxPlayback = true;
	public boolean initial = true;
	public boolean isActive = true;

	/**
	 * Initialise sound object
	 */
	public Sound() {
		running = true;
		rn = new Random();
		oneTwoOrThree = new Random();
	}

	/**
	 * The run method of the Sound thread. 
	 * Used for the music and the random zombie sound effects. Updates every half a second.
	 */ 
	public void run() {
		try {
			while(running) {
				playMusic();
				update();
				Thread.sleep(500);
			}
		} catch (InterruptedException e) {
			System.err.println("Sound loop interrupted exception: " + e.getMessage());
		}
	}

	/**
	 * The update method called inside the run method. 
	 * Plays a background zombie sound based on ZOMBIE_SOUND_PROBABILITY
	 */
	public void update() {
		if(this.rn.nextFloat() < ZOMBIE_SOUND_PROBABILITY) {
			zombieSounds();
		}
	}

	/**
	 * Creates an AudioInpuStream from a given file and then creates a Clip.
	 * @param fileName The sound file that will be played
	 * @return Clip used to play the sound
	 */
	public Clip createClip(String fileName) {
		Clip sound;
		try {
			File file = new File(fileName);
			if (file.exists()) {
				sound = AudioSystem.getClip();
				AudioInputStream audioInp = AudioSystem.getAudioInputStream(file);
				sound.open(audioInp);

				sound.addLineListener(new LineListener() {
					public void update(LineEvent event) {
						if (event.getType() == LineEvent.Type.STOP) {
							event.getLine().close();
						}
					}
				});
			}
			else {
				throw new RuntimeException("Sound Exception - File not found: " + fileName);
			}
		}
		catch (MalformedURLException e) {
			throw new RuntimeException("Sound Exception - Malformed URL: " + e);
		}
		catch (UnsupportedAudioFileException e) {
			throw new RuntimeException("Sound Exception - Unsupported Audio File: " + e);
		}
		catch (IOException e) {
			throw new RuntimeException("Sound Exception - Input/Output Error: " + e);
		}
		catch (LineUnavailableException e) {
			throw new RuntimeException("Sound: Line Unavailable: " + e.getMessage());
		} 
		return sound;
	}

	/**
	 * Method to play the gun sound. 
	 * @param playerShot Boolean of wherever the player has been allowed to shoot (fire rate)
	 */
	public void bulletSound(boolean playerShot) {
		if (playerShot && sfxPlayback) {
			Clip gunClip = this.createClip(pistolSound);
			turnDownVolume(gunClip, -10.0f);
			gunClip.start();
		}  
	}

	public void buttonPressed() {
		if(sfxPlayback) {
			Clip newClip = this.createClip(buttonPressed);
			newClip.start();
		}
	}

	public void playPressed() {
		if(sfxPlayback) {
			Clip newClip = this.createClip(playPressed);
			newClip.start();
		}
	}

	/**
	 * Method to alter the volume of a clip.
	 * @param clip The Clip which needs needs altering	
	 * @param fl Amount of decibels the volume needs changing by
	 */
	public void turnDownVolume(Clip clip, Float fl) {
		FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		gainControl.setValue(fl);
	}

	/**
	 * Method to play the sound of a zombie dying
	 */
	public void zombieDeath() {
		if(sfxPlayback) {
			Clip zombie = this.createClip(zombieDeath);
			zombie.start();
		}
	}

	/**
	 * Method to play the sound of player being hit by a zombie
	 */
	public void playerHurt() {
		if(sfxPlayback) {
			Clip playerHurt = this.createClip(hurt);
			playerHurt.start();
		}
	}

	/**
	 * Method used to played random background zombie sounds.
	 */
	public void zombieSounds() {
		if(sfxPlayback) {
			int fileNum = oneTwoOrThree.nextInt(3) + 1;
			String zombieS = zombieSound + fileNum + ".wav";
			Clip zombies = this.createClip(zombieS);
			zombies.start();
			// System.out.println("Zombie noise played");
		}
	}

	/**
	 * Method used to play and stop the music. The music is looped continuously
	 */
	public void playMusic() {
		if(initial) {
			//System.out.println("Music started, we good");
			musicClip = this.createClip(music);
			musicClip.loop(Clip.LOOP_CONTINUOUSLY);
			isActive = true;
			initial = false;
		}else if(musicPlayback && !isActive) {
			//System.out.println("Music started again");
			musicClip = this.createClip(music);
			musicClip.loop(Clip.LOOP_CONTINUOUSLY);
			isActive = true;
		} else if(!musicPlayback && isActive) {
			//System.out.println("Music stopped");
			musicClip.close();
			isActive = false;

		}
	}
}