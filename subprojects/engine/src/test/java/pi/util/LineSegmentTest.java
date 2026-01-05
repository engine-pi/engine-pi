package pi.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pi.Vector.v;

import org.junit.jupiter.api.Test;

public class LineSegmentTest
{
    private LineSegment line(double aX, double aY, double bX, double bY)
    {
        return new LineSegment(v(aX, aY), v(bX, bY));
    }

    @Test
    public void aToB()
    {
        assertTrue(line(0, 0, 5, 5).aToB().equals(v(5, 5)));
        assertTrue(line(5, 5, 0, 0).aToB().equals(v(-5, -5)));
    }

    @Test
    public void bToA()
    {
        assertTrue(line(0, 0, 5, 5).bToA().equals(v(-5, -5)));
        assertTrue(line(5, 5, 0, 0).bToA().equals(v(5, 5)));
    }

    @Test
    public void point()
    {
        assertTrue(line(0, 0, 1, 1).point(1).equals(v(1, 1)));
        assertTrue(line(0, 0, 1, 1).point(0).equals(v(0, 0)));
        assertTrue(line(0, 0, 1, 1).point(2).equals(v(2, 2)));
        assertTrue(line(0, 0, 1, 1).point(0.5).equals(v(0.5, 0.5)));
    }

    @Test
    public void length()
    {
        assertEquals(1.4142135623730951, line(0, 0, 1, 1).length());
        assertEquals(1.4142135623730951, line(1, 1, 0, 0).length());
    }

}
