package game.client;

import game.util.User;

import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Class for sending objects from the client to the server
 */
public class ClientSender extends Thread {

    private User user;
    private ObjectOutputStream objOut;
    private ClientGameState state;
    private boolean running;

    /**
     * Constructor
     *
     * @param user   Object of user
     * @param objOut ObjectOutputStream
     */
    public ClientSender(User user, ObjectOutputStream objOut, ClientGameState state) {
        this.user = user;
        this.objOut = objOut;
        this.state = state;
        this.running = true;
    }

    /**
     * Send an object up the ObjectOutputStream to the Server
     *
     * @param obj Object to send
     */
    public void sendObject(Object obj) {
        try {
            objOut.writeObject(obj);
            objOut.flush();
        } catch (IOException e) {
            System.err.println("Communication Error in ClientSender: " + e.getMessage());
            //e.printStackTrace();
            state.setHasFinished(true);
            //System.exit(1);
        }
    }


    // Main method to run when thread starts
    public void run() {
        System.out.println("Client: ClientSender running");
        sendObject(user);
        // Keep running, sending the player object from the client game state every so often.
        while (running) {
            try {
                Thread.sleep(3000);
            } catch (Exception e) {
                System.err.println("Exception in ClientSender: " + e.getMessage());
                e.printStackTrace();
                // System.exit(1);
            }
        }

        try {
            objOut.close();
        } catch (IOException e) {
            System.out.println("Couldn't close client output stream");
        }
    }

    /**
     * Add a new game state
     *
     * @param state ClientGameState to end to this sender
     */
    public void addState(ClientGameState state) {
        this.state = state;
    }

    public void closeStream() {
        running = false;
    }

}