package pi.graphics.geom;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static pi.graphics.geom.Vector.v;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class DirectedLineSegmentTest
{
    private DirectedLineSegment line(double aX, double aY, double bX, double bY)
    {
        return new DirectedLineSegment(v(aX, aY), v(bX, bY));
    }

    @Test
    public void difference()
    {
        assertEquals(v(5, 5), line(0, 0, 5, 5).difference());
        assertEquals(v(-5, -5), line(5, 5, 0, 0).difference());
    }

    @Test
    public void proportionalPoint()
    {
        DirectedLineSegment l = line(0, 0, 1, 1);
        assertEquals(v(1, 1), l.proportionalPoint(1));
        assertEquals(v(0, 0), l.proportionalPoint(0));
        assertEquals(v(2, 2), l.proportionalPoint(2));
        assertEquals(v(0.5, 0.5), l.proportionalPoint(0.5));
    }

    @Test
    public void distancePoint()
    {
        DirectedLineSegment l = line(0, 0, 1, 1);
        assertEquals(v(0, 0), l.distancePoint(0));
        assertEquals(v(1, 1).normalize(), l.distancePoint(1));
        assertEquals(v(1, 1).normalize().multiply(2), l.distancePoint(2));

        DirectedLineSegment l2 = line(0, 0, 3, 4);
        assertEquals(v(0, 0), l2.distancePoint(0));
        assertEquals(v(0.6, 0.8), l2.distancePoint(1));
        assertEquals(v(1.2, 1.6), l2.distancePoint(2));
    }

    @Test
    public void length()
    {
        assertEquals(1.4142135623730951, line(0, 0, 1, 1).length());
        assertEquals(1.4142135623730951, line(1, 1, 0, 0).length());
    }

    @Nested
    class AngleTest
    {
        @Test
        public void cardinal()
        {
            assertEquals(0.0, line(0, 0, 1, 0).angle(), 1e-12);
            assertEquals(90.0, line(0, 0, 0, 1).angle(), 1e-12);
            assertEquals(180.0, line(0, 0, -1, 0).angle(), 1e-12);
            assertEquals(-90.0, line(0, 0, 0, -1).angle(), 1e-12);
        }

        @Test
        public void diagonals()
        {
            assertEquals(45.0, line(0, 0, 1, 1).angle(), 1e-12);
            assertEquals(135.0, line(0, 0, -1, 1).angle(), 1e-12);
            assertEquals(-135.0, line(0, 0, -1, -1).angle(), 1e-12);
            assertEquals(-45.0, line(0, 0, 1, -1).angle(), 1e-12);
        }

        @Test
        public void arbitrary()
        {
            assertEquals(53.13010235415598, line(0, 0, 3, 4).angle(), 1e-12);
            assertEquals(-126.86989764584402, line(0, 0, -3, -4).angle(),
                    1e-12);
            assertEquals(0.0, line(5, 5, 10, 5).angle(), 1e-12);
            assertEquals(180.0, line(10, 5, 5, 5).angle(), 1e-12);
        }
    }

}
