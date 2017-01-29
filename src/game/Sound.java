package game;

import java.io.File;
import javax.sound.sampled.*;
import java.io.IOException;
import java.net.MalformedURLException;

public class Sound {
    private String pistolSound = "src/game/sounds/pistol.wav";
    private String music = "src/game/sounds/music.wav";
    private String zombieDeath = "src/game/sounds/zombie.wav";
    Clip musicClip;

    public Sound() {
    	
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
}