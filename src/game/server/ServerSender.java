package game.server;

import game.util.SendableState;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ConcurrentModificationException;

/**
 * Class for managing sending from the server to the specified client
 */
public class ServerSender extends Thread {

    private ServerGameState state;
    private ObjectOutputStream objOut;
    private boolean initial, playersReadyInitial, gameInProgress, running;


    /**
     * Constructor method
     *
     * @param objOut The ObjectOutputStream
     */
    public ServerSender(ObjectOutputStream objOut) {
        this.objOut = objOut;
        this.initial = true;
        this.playersReadyInitial = true;
        this.gameInProgress = false;
        //This is a dummy state that won't be used, it is waiting for a real state to be created
        this.state = new ServerGameState("a", "b", 0);
        this.running = true;
    }

    /**
     * Send an object down the ObjectOutputStream to the client
     */
    public void sendGameState() {
        try {
            SendableState newState = state.getPackagedState();
            objOut.writeObject(newState);
            objOut.flush();
            objOut.reset();
        } catch (IOException e) {
            System.err.println("Can't send game state! " + e.getMessage());
            //System.exit(1);
        } catch (ConcurrentModificationException c) {
            System.out.println("Concurrent mod exception in sendGameState()");
        }
    }


    /**
     * Send the end state of the game
     */
    public void sendEndState() {

        try {
            objOut.writeObject(state.getEndState());
            objOut.flush();
            objOut.reset();
        } catch (IOException e) {
            System.err.println("Can't send end game state! " + e.getMessage());
            //System.exit(1);
        }
    }

    /**
     * Send an object up the ObjectOutputStream to the Client
     *
     * @param obj Object to send
     */
    public void sendObject(Object obj) {
        try {
            objOut.writeObject(obj);
            objOut.flush();
        } catch (IOException e) {
            System.err.println("Can't send object! " + e.getMessage());
            //System.exit(1);
        }
        System.out.println("Server: Object successfully sent");
    }

    // Main method to run when thread starts
    public void run() {
        boolean finalStateSent = false;
        while (running) {
            try {
                Thread.sleep(1000 / 60);
                if (gameInProgress) { // if there is a game in progress
                    if (initial) {
                        sendObject("StartingGame");
                        initial = false;
                        finalStateSent = false;
                    }
                    if (state.HasFinished() && !finalStateSent) {
                        System.out.println("hasFinished is now true");
                        finalStateSent = true;

                        sendGameState(); // Send a final update (so players don't finish with 50% health because they didn't get the final update.
                        sendEndState(); // Send end state of the game

                        gameInProgress = false; // locally stop receiving updates.
                        initial = true;
                        System.out.println("Final state sent, end state sent");
                    } else {
                        sendGameState(); // Send the game state
                    }
                } else {
                    //System.out.println("Game not ready yet");
                }

            } catch (InterruptedException e) {
                System.err.println("ServerSender Interrupted Exception: " + e.getMessage());
            }
        }

        try {
            System.out.println("Closing OutputStream");
            objOut.close();
        } catch (IOException e) {
            System.out.println("Closing the object output stream didn't work");
            //System.exit(1);
        }
    }

    public void startNewGame(ServerGameState server) {
        this.state = server;
        this.gameInProgress = true;
        this.playersReadyInitial = true;
    }

    public void closeStream() {
        running = false;
    }
}
