package pi.graphics.geom;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import pi.Vector;

class DirectionTest
{
    @Test
    void toVector()
    {
        Vector vector = Direction.UP.toVector();
        assertEquals(vector.x(), 0);
        assertEquals(vector.y(), 1);
    }
}
