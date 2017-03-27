package game;

import game.client.*;
import game.map.TileTest;
import game.map.TileTypeTest;
import game.server.ClientTableTest;
import game.server.MatchmakerTest;
import game.server.ServerGameStateTest;
import game.server.TimerTest;
import game.util.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

//import game.map.MapDataTest;

@RunWith(Suite.class)
@SuiteClasses({EntityTest.class, SendableStateTest.class, CollisionBoxTest.class, CollisionTest.class, BulletTest.class, GameStateTest.class, 
	SoundTest.class, EndStateTest.class, VectorTest.class, UserTest.class, ClientGameStateTest.class, ClientSenderTest.class, WeaponTest.class,
	PowerUpTest.class, MenuRendererTest.class, RendererTest.class, TileTest.class, TileTypeTest.class, ClientTableTest.class, MatchmakerTest.class, 
	ServerGameStateTest.class, TimerTest.class, PlayerUpdatePacketTest.class, SendableStateTest.class})
public class AllTests {

	//this is the testing suite to run all tests from
}