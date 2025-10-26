package de.pirckheimer_gymnasium.engine_pi.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.awt.Font;
import java.awt.geom.Rectangle2D;

import org.junit.jupiter.api.Test;

public class FontUtilTest
{
    Font font = new Font("Arial", Font.PLAIN, 12);

    String content = "Hello, World!";

    @Test
    void testGetDescent()
    {
        int descent = FontUtil.getDescent(font);
        assertEquals(3, descent);
    }

    @Test
    void testGetStringBounds()
    {
        Rectangle2D bounds = FontUtil.getStringBounds(content, font);
        assertNotNull(bounds);
        assertEquals(70.0, bounds.getWidth(), 0.1);
        assertEquals(13.8, bounds.getHeight(), 0.1);
        assertEquals(-10.8, bounds.getY(), 0.1);
        assertEquals(0, bounds.getX(), 0.1);
    }

}
