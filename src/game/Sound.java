package game;

import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Random;

import javax.sound.sampled.*;

/**
 * 
 * @author jonwoodburn
 *Class that manages sound, mainly music and the game's sound effects.
 */

public class Sound extends Thread{
    private String pistolSound = "src/game/sounds/pistol.wav";
    private String music = "src/game/sounds/music.wav";
    private String zombieDeath = "src/game/sounds/zombieDeath.wav";
    private String zombieSound ="src/game/sounds/zombieSound";
    private String hurt = "src/game/sounds/hurt.wav";
    private boolean running;
    private Clip musicClip, gunClip;
    private static final float ZOMBIE_SOUND_PROBABILITY = 0.01f;
    Random rn, oneTwoOrThree;
    public static boolean musicOn = true;
    public static boolean SFXOn = true;
    
    /**
     * initialises basic variables
     */

    public Sound() {
    	running = true;
    	rn = new Random();
    	oneTwoOrThree = new Random();
    }
    
    /**
     * The run method of the Sound thread. Used for the music and the
     * random zombie sound effects. Updates every half a second.
     */
    
    public void run() {
    	playMusic();
    	try {
    		while(running) {
    			if(!musicOn) {
    				stopMusic();
    			}
    			
    			update();
    			
    			Thread.sleep(500);
    		}
    	} catch (InterruptedException e) {
    		System.out.println("Sound loop interrupted exception");
    	}
    }
    
    /**
     * The update method called inside the run method. Plays a background
     * zombie sound based on ZOMBIE_SOUND_PROBABILITY
     */
    
    public void update() {
    	if(this.rn.nextFloat() < ZOMBIE_SOUND_PROBABILITY) {
    		zombieSounds();
    	}
    	
    }
    
    /**
     * Creates an AudioInpuStream from a given file and then creates a Clip.
     * @param fileName the sound file that will be played
     * @return the Clip used to play the sound
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
                throw new RuntimeException("Sound: file not found: " + fileName);
            }
        }
        catch (MalformedURLException e) {
            throw new RuntimeException("Sound: Malformed URL: " + e);
        }
        catch (UnsupportedAudioFileException e) {
            throw new RuntimeException("Sound: Unsupported Audio File: " + e);
        }
        catch (IOException e) {
            throw new RuntimeException("Sound: Input/Output Error: " + e);
        }
        catch (LineUnavailableException e) {
            throw new RuntimeException("Sound: Line Unavailable: " + e.getMessage());
        }
        
        return sound;
    }
    
    /**
     * Method to play the gun sound. 
     * @param playerShot boolean of wherever the player has been allowed to shoot (fire rate)
     */
    public void bulletSound(boolean playerShot) {
    	if (playerShot) {
        	Clip gunClip = this.createClip(pistolSound);
        	turnDownVolume(gunClip, -10.0f);
    	    gunClip.start();
    	}  
    }

    /**
     * Method to alter the volume of a clip.
     * @param clip the Clip which needs needs altering	
     * @param fl the amount of decibels the volume needs changing by
     */
    public void turnDownVolume(Clip clip, Float fl) {
    	FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
    	gainControl.setValue(fl);
    }
    
    /**
     * Method to play the sound of a zombie dying
     */
    
    public void zombieDeath() {
    	Clip zombie = this.createClip(zombieDeath);
    	zombie.start();
    }
    
    /**
     * Method to play the sound of player being hit by a zombie.
     */
    
    public void playerHurt() {
    	Clip playerHurt = this.createClip(hurt);
    	playerHurt.start();
    }
    
    /**
     * Method used to played random background zombie sounds.
     */
    
    public void zombieSounds() {
    	int fileNum = oneTwoOrThree.nextInt(3) + 1;
    	String zombieS = zombieSound + fileNum + ".wav";
    	Clip zombies = this.createClip(zombieS);
    	zombies.start();
    }
    
    /**
     * Method used to play the music. The music is looped continuously
     */
    
    public void playMusic() {
    	musicClip = this.createClip(music);
    	musicClip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    
    /**
     * Method used to stop the music
     */
    public void stopMusic() {
    	if(musicClip.isActive()) {
    		musicClip.stop();
    	}
    }
}