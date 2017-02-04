package game.networking;

import java.io.Serializable;

/**
 * Created by Daniel on 02/02/2017.
 */
public class SampleObject implements Serializable {

    private String name;
    public SampleObject(){
        this.name = "Sample";
    }

    public String toString(){
        return name;
    }
}
