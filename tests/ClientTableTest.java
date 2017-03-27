import game.server.ClientTable;
import game.server.ClientTable.playerStatus;
import game.util.User;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ClientTableTest {

    ClientTable c;

    @Before
    public void setUp() throws Exception {
        c = new ClientTable();
    }

    @Test
    public void testClientTable() {
        assertNotNull(c);
    }

    @Test
    public void testAddToTable() {
        User u = new User("Daniel", 1);
        c.addToTable(u);
        assertEquals(c.userExists("Daniel"), true);
    }

    @Test
    public void testRemoveFromTable() {
        User u = new User("Daniel", 1);
        c.removeFromTable(u);
        assertEquals(c.userExists("Daniel"), false);

    }

    @Test
    public void testCheckAvailable() {
        User u = new User("Daniel", 1);
        User u2 = new User("Jon", 1);

        c.addToTable(u);
        c.addToTable(u2);

        c.changePlayerStatus(u, playerStatus.WAITING);
        c.changePlayerStatus(u2, playerStatus.WAITING);

        assertEquals(c.checkAvailable().size(), 2);
    }

}
