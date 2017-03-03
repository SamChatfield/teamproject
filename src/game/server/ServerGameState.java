package game.server;

import game.Entity;
import game.client.Player;
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

    private String player1Username;
    private String player2Username;

    public ServerGameState(String player1Username, String player2Username){
        this.player1Username = player1Username;
        this.player2Username = player2Username;
    }


    // TODO: Stop this from terminating the program
    public void startNewGame(){
        // First we want to generate the map
        mapImage = "prototypemap.png";
        mapData = new MapData(mapImage, "tilesheet.png", "tiledata.csv");

        // Set up two player objects that we can update later.
        this.player1 = new Player(0,0,mapData,player1Username);
        this.player2 = new Player(0,0,mapData,player2Username);

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
        return data;
    }

    public SendableState getPackagedState(){
        SendableState copyOf = new SendableState(this); // create a copy of the state.
        return copyOf; // return this so that it can be sent.
    }

}
