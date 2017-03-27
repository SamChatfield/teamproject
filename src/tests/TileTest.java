package tests;

import game.map.Tile;
import game.map.TileType;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.assertEquals;

public class TileTest {

    Tile tile;
    Color color;
    TileType tileType;

    @Before
    public void setUp() {
        color = new Color(0, 150, 150);
        tileType = new TileType("cake", color, null, false);
        tile = new Tile(1, 1, tileType);
    }

    @Test
    public void testGetX() {
        float X = tile.getX();
        assertEquals(X, (float) 1, 1);
    }

    @Test
    public void testGetY() {
        float Y = tile.getY();
        assertEquals(Y, (float) 1, 1);
    }

    @Test
    public void testGetType() {
        TileType type = tile.getType();
        assertEquals(type, tileType);
    }

    @Test
    public void testToString() {
        String toString = tile.toString();
        assertEquals(toString, "(1.0, 1.0) - cake");
    }


}
