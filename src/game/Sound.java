package game;

import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Random;

import javax.sound.sampled.*;

public class Sound extends Thread{
    private String pistolSound = "src/game/sounds/pistol.wav";
    private String music = "src/game/sounds/music.wav";
    private String zombieDeath = "src/game/sounds/zombieDeath.wav";
    private String zombieSound ="src/game/sounds/zombieSound";
    private boolean running;
    Clip musicClip, gunClip;
    public InputHandler input;
    private static boolean zombieHit;
    Random rn, oneTwoOrThree;

    public Sound(InputHandler input) {
    	running = true;
    	this.input = input;
    	zombieHit = false;
    	rn = new Random();
    	oneTwoOrThree = new Random();
    }
    
    public void run() {
    	playMusic();
    	try {
    		while(running) {
    			if(!Game.musicOn) {
    				stopMusic();
    			}
    			
    			update();
    			
    			Thread.sleep(50);
    		}
    	} catch (InterruptedException e) {
    		System.out.println("Sound loop interrupted exception");
    	}
    }
    
    public void update() {
    	if(zombieHit) {
    		zombieDeath();
    		zombieHit = false;
    	}
    	if(input.isMouseButtonDown(MouseEvent.BUTTON1)){
    		bulletSound();
    	}
    	if(this.rn.nextInt(75) == 25) {
    		zombieSounds();
    	}
    	
    }
//create the clip and load a specific file
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
    	
//play the bullet sound	
    public void bulletSound() {
    	Clip gunClip = this.createClip(pistolSound);
	    gunClip.start();
	    System.out.println(gunClip.isOpen());
	    
    }
    
    public void turnDownVolume(Clip clip, Float fl) {
    	FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
    	gainControl.setValue(fl);
    }
    
    public static void zombieHit() {
    	zombieHit = true;
    }
    
//play the zombie death sound   
    public void zombieDeath() {
    	Clip zombie = this.createClip(zombieDeath);
    	zombie.start();
    }
//play random zombie sounds    
    public void zombieSounds() {
    	int fileNum = oneTwoOrThree.nextInt(3) + 1;
    	System.out.println(fileNum);
    	String zombieS = zombieSound + fileNum + ".wav";
    	Clip zombies = this.createClip(zombieS);
    	zombies.start();
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