package game.networking;

import java.io.Serializable;

/**
 * Created by Daniel on 02/02/2017.
 * Modified by George on 04/02/2017.
 */
public class SampleObject extends Object implements Serializable {

    private String name;
    private int value;
    
    /**
     * Constructor
     * @param name Name of object
     * @param value Value to set
     */
    public SampleObject(String name, int value){
        this.name = name;
        this.value = value;
    }

    public String toString(){
        return name;
    }
    
    /**
     * Get value
     * @return value
     */
    public int getValue() {
    	return value;
    }
}
