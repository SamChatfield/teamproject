package game;

/**
 * Created by Sam on 30/01/2017.
 */
public class Map {

    private final float width, height; // width and height of the map in game coords, e.g. for 50x50 tile map w=50.0, h=50.0

    // TODO placeholder constructor, when map parsing is implemented this will creating an instance for a specific map
    public Map(float width, float height) {
        this.width = width;
        this.height = height;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

}
