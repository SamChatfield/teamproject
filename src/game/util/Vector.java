package game.util;

import java.util.Random;

/**
 * Simple 2D Float Vector
 */
public class Vector {

    private float x, y;

    /**
     * Constructor to create a new Vector
     *
     * @param x X value
     * @param y Y value
     */
    public Vector(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Return a randomised a vector between with x,y = -1.0 | 0.0 | 1.0
     * Application includes randomising zombie movement
     *
     * @return Randomised vector
     */
    public static Vector randomVector() {
        Random rand = new Random();
        float x = (float) (rand.nextInt(3) - 1);
        float y = (float) (rand.nextInt(3) - 1);

        return new Vector(x, y);
    }

    /**
     * Get the normalised version of the vector
     *
     * @return Normalised version of vector
     */
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

    /**
     * Get magnitude of vector
     *
     * @return Magnitude of vector
     */
    public float magnitude() {
        return (float) Math.sqrt(x * x + y * y);
    }

    /**
     * Add another vector to this vector
     *
     * @param Another vector to add
     */
    public void add(Vector a) {
        x += a.x();
        y += a.y();
    }

    /**
     * Get X value of vector
     *
     * @return X value
     */
    public float x() {
        return x;
    }

    /**
     * Get Y value of vector
     *
     * @return Y value
     */
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
