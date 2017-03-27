import game.client.Player;
import game.client.Sound;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.sound.sampled.Clip;

public class SoundTest {

    Player p;
    Sound sound;

    @Before
    public void setUp() throws Exception {
        sound = new Sound();
        p = new Player(0, 0, null, null);
        p.setLastAttackTime(-100);
        p.setShootDelay(-100);
        sound.addPlayer(p);

    }

    @Test
    public void testRun() {
        sound.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @Test
    public void testUpdate() {
        sound.update();
    }


    @Test
    public void testBulletSound() {
        sound.bulletSound();

        p.setCurrentlyEquipped(game.Weapon.WeaponState.MAC_GUN);
        sound.addPlayer(p);
        sound.bulletSound();

        p.setCurrentlyEquipped(game.Weapon.WeaponState.SHOTGUN);
        sound.addPlayer(p);
        sound.bulletSound();

        p.setCurrentlyEquipped(game.Weapon.WeaponState.UZI);
        sound.addPlayer(p);
        sound.bulletSound();

        p.setCurrentlyEquipped(game.Weapon.WeaponState.CONVERT);
        sound.addPlayer(p);
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
