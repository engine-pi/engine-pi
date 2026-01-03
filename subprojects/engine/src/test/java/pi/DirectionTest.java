package pi;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
