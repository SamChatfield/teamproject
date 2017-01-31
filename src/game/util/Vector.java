package game.util;

import java.util.Random;

/**
 * A simple 2D float vector
 *
 * Created by Sam on 25/01/2017.
 */
public class Vector {

    private float x, y;

    public Vector(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector normalised() {
        float mag = magnitude();
        float nx = 0.0f;
        float ny = 0.0f;
        if (mag != 0.0f) {
            nx = x / mag;
            ny = y / mag;
        }
        return new Vector(nx, ny);
    }

    public float magnitude() {
        return (float) Math.sqrt(x * x + y * y);
    }

    /**
     * Add another vector to this vector
     * @param a other vector to add
     */
    public void add(Vector a) {
        x += a.x();
        y += a.y();
    }

    /**
     * Return a randomised a vector between with x,y = -1.0 | 1.0
     * Application includes randomising zombie movement
     */
    // TODO zombies only move diagonally because neither of the components of the vector can be 0
    public static Vector randomVector() {
        Random rand = new Random();
        float x = (float) (rand.nextInt(2) * 2 - 1);
        float y = (float) (rand.nextInt(2) * 2 - 1);

        return new Vector(x, y);
    }

    public float x() {
        return x;
    }

    public float y() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vector vector = (Vector) o;

        if (Float.compare(vector.x, x) != 0) return false;
        return Float.compare(vector.y, y) == 0;
    }

    @Override
    public int hashCode() {
        int result = (x != +0.0f ? Float.floatToIntBits(x) : 0);
        result = 31 * result + (y != +0.0f ? Float.floatToIntBits(y) : 0);
        return result;
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }
}
