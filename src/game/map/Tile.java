package game.map;

/**
 * Created by Sam on 10/02/2017.
 */
public class Tile {

    private float x, y;
    private TileType type;

    public Tile(float x, float y, TileType type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public TileType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ") - " + type;
    }

}
