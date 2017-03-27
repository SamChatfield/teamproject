package tests;

import game.client.ClientGameState;
import game.client.ClientSender;
import game.util.User;
import org.junit.Before;
import org.junit.Test;

import java.io.ObjectOutputStream;

public class ClientSenderTest {

    ClientSender sender;
    User dave;
    ObjectOutputStream objOut;
    ClientGameState gameState;

    @Before
    public void setUp() throws Exception {
        dave = new User("dave", 1);
        objOut = null;
        gameState = new ClientGameState(dave);
        sender = new ClientSender(dave, objOut, gameState);
    }


    @Test
    public void testAddState() {
        sender.addState(gameState);
    }

}
