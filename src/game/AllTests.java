package game;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import game.PowerUp.PuState;
import game.client.ClientGameStateTest;
import game.client.Player;
import game.client.SoundTest;
//import game.map.MapDataTest;
import game.util.GameStateTest;
import game.util.UserTest;

@RunWith(Suite.class)
@SuiteClasses({EntityTest.class, CollisionBoxTest.class, CollisionTest.class, BulletTest.class, GameStateTest.class, 
	SoundTest.class, UserTest.class, ClientGameStateTest.class, WeaponTest.class, PowerUpTest.class})
public class AllTests {

	
}