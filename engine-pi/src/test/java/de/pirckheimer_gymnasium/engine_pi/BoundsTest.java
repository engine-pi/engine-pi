package de.pirckheimer_gymnasium.engine_pi;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class BoundsTest
{
    @Test
    public void testGetCenter()
    {
        Bounds bounds = new Bounds(0, 0, 1, 1);
        Vector vector = bounds.getCenter();
        assertEquals(vector.getX(), 0.5, 0);
        assertEquals(vector.getY(), 0.5, 0);
    }
}
