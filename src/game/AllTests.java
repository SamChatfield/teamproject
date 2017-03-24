package game;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import game.PowerUp.PuState;
import game.client.ClientGameStateTest;
import game.client.Player;
import game.client.SoundTest;
import game.util.EndStateTest;
//import game.map.MapDataTest;
import game.util.GameStateTest;
import game.util.SendableStateTest;
import game.util.UserTest;
import game.util.VectorTest;

@RunWith(Suite.class)
@SuiteClasses({EntityTest.class, SendableStateTest.class, CollisionBoxTest.class, CollisionTest.class, BulletTest.class, GameStateTest.class, 
	SoundTest.class, EndStateTest.class, VectorTest.class, UserTest.class, ClientGameStateTest.class, WeaponTest.class, PowerUpTest.class})
public class AllTests {

	
}