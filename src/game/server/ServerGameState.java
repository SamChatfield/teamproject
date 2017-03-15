package game.server;

import game.Bullet;
import game.Zombie;
import game.client.Player;
import game.map.MapData;
import game.util.*;

import java.util.ArrayList;
import java.util.Iterator;
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
        this.bullets = new ArrayList<>();
    }

    public Player getPlayer(String username){
        if(username.equals(player1Username)){
            return player1;
        }else if (username.equals(player2Username)){
            return player2;
        }else{
            return null;
        }
    }

    public Player getOtherPlayer(String username){
        if(username.equals(player1Username)){
            return player2;
        }else if (username.equals(player2Username)){
            return player1;
        }else{
            return null;
        }
    }

    // TODO: Stop this from terminating the program
    public void startNewGame(){
        // First we want to generate the map
        mapImage = "prototypemap.png";
        mapData = new MapData(mapImage, "tilesheet.png", "tiledata.csv");

        // Set up two player objects that we can update later.
        this.player1 = new Player(-15,0 ,mapData, player1Username);
        this.player2 = new Player(15 ,0 ,mapData, player2Username);

        int zombieCount = 10;
        ArrayList<Zombie> zombieFactory = new ArrayList<>();
        try{
            for (int i = 0; i < zombieCount; i++) {
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

        GameInstance instance = new GameInstance(this);        // Start up a new game instance
        instance.start();
        this.inProgress = true;

    }

    public ArrayList<DataPacket> getSendableZombies(){
//        long now = System.nanoTime();
        ArrayList<DataPacket> data = new ArrayList<>();
        for(Zombie z:zombies){
            data.add(z.getData());
        }
//        long dt = System.nanoTime() - now;
//        System.out.println("zombie time: " + dt / 1000000f);
        return data;
    }

    public ArrayList<DataPacket> getSendableBullets() {
//        long now = System.nanoTime();
        ArrayList<DataPacket> data = new ArrayList<>();
        for (Iterator<Bullet> it = bullets.iterator(); it.hasNext(); ) {
            data.add(it.next().getData());
        }
//        long dt = System.nanoTime() - now;
//        System.out.println("bullet time: " + dt / 1000000f);
        return data;
    }

    public void updatePlayer(String username, PlayerUpdatePacket packet){
        Player toModify = null;

        // First we need to get the player we want based on their username:
        if(player1Username.equals(username)){  toModify = player1; }else if(player2Username.equals(username)){ toModify = player2; }

        ArrayList<String> moves = packet.getKeyPresses();
        double delta = packet.getDelta();
        Vector pdv = new Vector(0.0f, 0.0f); // Player direction vector for this update

        for(String s:moves) {
            switch(s){
                case "VK_1":
                    toModify.setMoveSpeed(toModify.getMoveSpeed() - 0.01f);
                    break;
                case "VK_2":
                    toModify.setMoveSpeed(toModify.getMoveSpeed() + 0.01f);
                    break;
                case "VK_W":
                    pdv.add(new Vector(0.0f, 1.0f));
                    break;
                case "VK_A":
                    pdv.add(new Vector(-1.0f, 0.0f));
                    break;
                case "VK_D":
                    pdv.add(new Vector(1.0f, 0.0f));
                    break;
                case "VK_S":
                    pdv.add(new Vector(0.0f, -1.0f));
                    break;
                case "VK_Z":
                    toModify.conversionMode = true;
                    break;
                case "VK_X":
                    toModify.conversionMode = false;
                    break;
                case "BUTTON1":
                    // game coord x and y position of the aim
                    double playerAngle = toModify.getFacingAngle();
                    float aimX = (float) Math.cos(playerAngle);
                    float aimY = (float) -Math.sin(playerAngle);
                    Bullet b = toModify.shoot(aimX, aimY);
                    if(b != null){ // this could happen if they have already shot recently.
                        bullets.add(b);
                    }
                    break;
            }
        }
        if(packet.getfX() == -100 || packet.getfY() == -100 ){
            // Don't do anything
        }else{
            toModify.face(packet.getfX(), packet.getfY());
        }

        Vector pnv = pdv.normalised(); // Player normal direction vector for this update
        float pdx = pnv.x() * toModify.getMoveSpeed() * ((float) delta); // Actual change in x this update
        float pdy = pnv.y() * toModify.getMoveSpeed() * ((float) delta); // Actual change in y this update
        toModify.move(pdx, pdy);
    }

    public SendableState getPackagedState(){
        SendableState copyOf = new SendableState(this); // create a copy of the state.
        return copyOf; // return this so that it can be sent.
    }
}
