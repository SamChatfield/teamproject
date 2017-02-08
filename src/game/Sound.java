package game;

import java.io.File;
import javax.sound.sampled.*;

import com.sun.glass.events.KeyEvent;

import java.io.IOException;
import java.net.MalformedURLException;

public class Sound extends Thread{
    private String pistolSound = "src/game/sounds/pistol.wav";
    private String music = "src/game/sounds/music.wav";
    private String zombieDeath = "src/game/sounds/zombie.wav";
    private boolean running;
    Clip musicClip;
    public InputHandler input;

    public Sound(InputHandler input) {
    	running = true;
    	this.input = input;
    }
    
    public void run() {
    	try {
    		
    		while(running) {
    			update();
    			
    			Thread.sleep(100);
    		}
    	} catch (InterruptedException e) {
    		System.out.println("Sound loop interrupted exception");
    	}
    }
    
    public void update() {
    	if(input.isKeyDown(KeyEvent.VK_SPACE)){
    		bulletSound();
    	}
    	
    }
//create the clip and load a specific file
    public Clip createClip(String fileName) {
    	Clip sound;
        try {
            File file = new File(fileName);
            if (file.exists()) {
                AudioInputStream audioInp = AudioSystem.getAudioInputStream(file);
                sound = AudioSystem.getClip();
                sound.open(audioInp);
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
            throw new RuntimeException("Sound: Line Unavailable: " + e);
        }
        
        return sound;
    }
    	
//play the bullet sound	
    public void bulletSound() {
	    this.createClip(pistolSound).start();
    }
    
//play the zombie death sound   
    public void zombieDeath() {
    	this.createClip(zombieDeath).start();
    }
    
//play the music   
    public void playMusic() {
    	musicClip = this.createClip(music);
    	musicClip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    
//stop the music! 
    public void stopMusic() {
    	if(musicClip.isActive()) {
    		musicClip.stop();
    	}
    }
    
    public void playZombieSounds() {
    	this.createClip(zombieDeath).start();
    	
    }
}