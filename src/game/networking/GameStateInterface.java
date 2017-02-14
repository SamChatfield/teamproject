package game.networking;

import java.util.ArrayList;

/**
 * Created by Daniel on 07/02/2017.
 */
public class GameStateInterface {

    private ArrayList<SampleObject> a;

    public GameStateInterface(){
        this.a = new ArrayList<>();
    }

    public void insert(SampleObject obj){
       a.add(obj);
        System.out.println("Adding "+obj.toString());
    }

    public String toString(){
        String st = "";
        for(SampleObject b:a){
            st += b.toString();
        }
        return st;
    }

}
