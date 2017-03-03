package game.server;

import game.Entity;
import game.map.MapData;
import game.map.MapParser;
import game.util.DataPacket;
import game.util.GameState;
import game.Zombie;
import game.client.EntityData;
import game.util.SendableState;
import jdk.nashorn.internal.runtime.regexp.joni.constants.EncloseType;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Daniel on 16/02/2017.
 *
 * The game state of the server at any one time.
 */
public class ServerGameState extends GameState {

    public ServerGameState(){
    }

    /**
     * Secondary constructor used for copying this class for sending.
     * The main game state is passed into here as a parameter, and then this object is sendable.
     *
     * @param state State to create a copy of.
     */
    public ServerGameState(ServerGameState state){
        this.zombies = state.getZombies();
        this.players = state.getPlayers();
        this.mapImage = state.getMapImage();
        this.timeRemaining = state.getTimeRemaining();
    }

    // TODO: Stop this from terminating the program
    public void startNewGame(){
        // First we want to generate the map
        mapImage = "prototypemap.png";
        mapData = new MapData(mapImage, "tilesheet.png", "tiledata.csv");
        int zombieCount = 100;
        ArrayList<Zombie> zombieFactory = new ArrayList<>();
        try{
            for (int i = 0; i < zombieCount; i++) {
              //  System.out.println("Zombie");

                // Daniel does some random stuff here... (like speaking in the third person)
                Random rand = new Random();
                float x = (float) (0.5-rand.nextFloat())*mapData.getWidth();
                float y = (float) (0.5-rand.nextFloat())*mapData.getHeight();

                while(mapData.tileTypeAt(x,y).isObstacle()){
                    x = (float) (0.5-rand.nextFloat())*mapData.getWidth();
                    y = (float) (0.5-rand.nextFloat())*mapData.getHeight();
                }
                zombieFactory.add(new Zombie(x,y,mapData));
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
            System.exit(0);
        }
        this.zombies = zombieFactory;
        // Start up a new game instance
        GameInstance instance = new GameInstance(this);
        instance.start();
        this.inProgress = true;

    }

    public ArrayList<DataPacket> getSendableZombies(){
        ArrayList<DataPacket> data = new ArrayList<DataPacket>();
        for(Zombie z:zombies){
            data.add(z.getData());
        }
        System.out.println("Zom1"+data.get(1).getX());
        return data;
    }

    public SendableState getPackagedState(){
        SendableState copyOf = new SendableState(this); // create a copy of the state.
        return copyOf; // return this so that it can be sent.
    }

}
