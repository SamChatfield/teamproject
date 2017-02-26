package game.server;

import game.Entity;
import game.map.MapData;
import game.map.MapParser;
import game.util.DataPacket;
import game.util.GameState;
import game.Zombie;
import game.client.EntityData;
import jdk.nashorn.internal.runtime.regexp.joni.constants.EncloseType;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Daniel on 16/02/2017.
 *
 * The game state of the server at any one time.
 */
public class ServerGameState extends GameState {

    protected ArrayList<Zombie> zombies;

    public ServerGameState(){
    }

    public ArrayList<DataPacket> getZombieData(){
        ArrayList<DataPacket> packagedZombies = new ArrayList<>();
        for(Zombie z:zombies){
            packagedZombies.add(z.getData());
        }
        return packagedZombies;
    }

    public ArrayList<Zombie> getZombies() {
        return zombies;
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
                float x = (float) (0.5-rand.nextFloat())*50;
                float y = (float) (0.5-rand.nextFloat())*50;
                //System.out.println(x+" "+y);
                zombieFactory.add(new Zombie(x,y,mapData));
                //zombies.get(i).newMovingDir();
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

}
