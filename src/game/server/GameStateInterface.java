package game.server;

/**
 * Created by Daniel on 07/02/2017.
 * Used to interface with the game state that is held by the server.
 */
public class GameStateInterface {

    private ServerGameState state;

    public GameStateInterface(ServerGameState state){
        this.state=state;
    }

    public void updateGameState(Object obj){
        //state.update();
    }

    public ServerGameState getPackagedState(){
        ServerGameState copyOf = new ServerGameState(state); // create a copy of the state.
        return copyOf; // return this so that it can be sent.
    }

    public void updateTime(int time){
        state.updateTime(time);
    }

    public void startNewGame(){
        state.startNewGame();
    }

    public boolean inProgress(){
        return state.inProgress();
    }
}
