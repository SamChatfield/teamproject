package game;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import game.PowerUp.PuState;
import game.client.ClientGameStateTest;
import game.client.ClientSenderTest;
import game.client.MenuRendererTest;
import game.client.Player;
import game.client.RendererTest;
import game.client.SoundTest;
import game.map.TileTest;
import game.map.TileTypeTest;
import game.server.ClientTableTest;
import game.server.MatchmakerTest;
import game.server.ServerGameState;
import game.server.ServerGameStateTest;
import game.server.TimerTest;
import game.util.EndStateTest;
//import game.map.MapDataTest;
import game.util.GameStateTest;
import game.util.PlayerUpdatePacketTest;
import game.util.SendableStateTest;
import game.util.UserTest;
import game.util.VectorTest;

@RunWith(Suite.class)
@SuiteClasses({EntityTest.class, SendableStateTest.class, CollisionBoxTest.class, CollisionTest.class, BulletTest.class, GameStateTest.class, 
	SoundTest.class, EndStateTest.class, VectorTest.class, UserTest.class, ClientGameStateTest.class, ClientSenderTest.class, WeaponTest.class,
	PowerUpTest.class, MenuRendererTest.class, RendererTest.class, TileTest.class, TileTypeTest.class, ClientTableTest.class, MatchmakerTest.class, 
	ServerGameStateTest.class, TimerTest.class, PlayerUpdatePacketTest.class, SendableStateTest.class})
public class AllTests {

	//this is the testing suite to run all tests from
}