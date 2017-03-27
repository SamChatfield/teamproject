package tests; /**
 *
 */

import game.util.Vector;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


//--

/**
 * @author ryan-
 */
public class VectorTest {

    Vector vector = new Vector(0, 0);
    Vector vector1 = new Vector(1, 1);
    Vector vector2 = new Vector(2, 2);
    Vector vectorNorm = new Vector(0.70710677f, 0.70710677f);

    /**
     * Test method for {@link game.util.Vector#hashCode()}.
     */
    @Test
    public final void testHashCode() {
        assertEquals(0, vector.hashCode(), 0.1);
        assertNotNull(vector1.hashCode());
    }


    /**
     * Test method for {@link game.util.Vector#normalised()}.
     */
    @Test
    public final void testNormalised() {
        assertEquals(vector, vector.normalised());
        assertEquals(vectorNorm, vector1.normalised());

    }

    /**
     * Test method for {@link game.util.Vector#add(game.util.Vector)}.
     */
    @Test
    public final void testAdd() {
        vector.add(vector1);
        assertEquals(vector1, vector);

    }

    /**
     * Test method for {@link game.util.Vector#randomVector()}.
     */
    @Test
    public final void testRandomVector() {
        assertNotNull(Vector.randomVector());

    }

    /**
     * Test method for {@link game.util.Vector#x()}.
     */
    @Test
    public final void testX() {
        assertNotNull(vector.x());
    }

    /**
     * Test method for {@link game.util.Vector#y()}.
     */
    @Test
    public final void testY() {
        assertNotNull(vector.y());
    }

    /**
     * Test method for {@link game.util.Vector#toString()}.
     */
    @Test
    public final void testToString() {
        assertEquals("(0.0,0.0)", vector.toString());
    }

}
