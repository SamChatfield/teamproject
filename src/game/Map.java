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

    public boolean isInMap(float x, float y) {
        float wBound = width - (width / 2.0f);
        float hBound = height - (height / 2.0f);
        return x >= -wBound && x <= wBound && y >= -hBound && y <= hBound;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

}
