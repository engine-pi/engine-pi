# `tests` (Unittests)

Als Test-Framework kommt [JUnit](https://junit.org) in der Version 5 zu Einsatz,
als [Mocking](https://de.wikipedia.org/wiki/Mock-Objekt)-Framework
[Mockito](https://site.mockito.org).

Die Testmethoden werden ohne das Präfix `test` formuliert, um kürze
Methodennamen zu erhalten. Dass es sich um einen Testmethode handelt ist durch
die Annoation `@Test` ersichtlich. Die Anordnung der Testmethoden erfolgt in der
gleichen Reihenfolge wie die der zu testenden Methoden, um die
Nachvollziehbarkeit zu erleichtern. Der Name der Testklasse endet stets mit dem
Suffix `Test`, sodass ihre Funktion eindeutig erkennbar ist.

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
    void setUp()
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
