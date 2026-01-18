# `tests` (Unittests)

[JUnit](https://junit.org/)

[Mockito](https://site.mockito.org/)

- Testmethoden ohne `test`-Präfix
- Testmethoden in der Reihenfolge der zu testenden Methoden.
- Test-Klassenname endet mit `Test`

```java
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.awt.Color;
import java.awt.Graphics2D;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import pi.debug.ToStringFormatter;

/**
 * @author Josef Friedrich
 *
 * @since 0.42.0
 */
public class EllipseBoxTest
{
    EllipseBox ellipse;

    @BeforeEach
    void beforeEach()
    {
        ellipse = new EllipseBox(100, 50).color(Color.RED);
    }

    @Test
    void width()
    {
        assertSame(ellipse, ellipse.width(150));
        ellipse.calculateDimension();
        assertEquals(150, ellipse.width());
    }

    @Test
    void height()
    {
        assertSame(ellipse, ellipse.height(75));
        ellipse.calculateDimension();
        assertEquals(75, ellipse.height());
    }

    @Test
    void color()
    {
        EllipseBox result = ellipse.color(Color.BLUE);
        assertSame(ellipse, result);
        assertEquals(Color.BLUE, ellipse.color());
    }

    @Test
    void draw()
    {
        Graphics2D g = mock(Graphics2D.class);
        ellipse.draw(g);
        verify(g).fillOval(0, 0, 100, 50);
    }

    @Test
    void calculateDimension()
    {
        assertEquals(0, ellipse.width());
        assertEquals(0, ellipse.height());
        ellipse.calculateDimension();
        assertEquals(100, ellipse.width());
        assertEquals(50, ellipse.height());
    }

    @Test
    void toStringFormatter()
    {
        String result = ellipse.toString();
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(
            "EllipseBox [color=Color[r=255,g=0,b=0], dWidth=100, dHeight=50]",
            ToStringFormatter.clean(result));
    }
}
```
