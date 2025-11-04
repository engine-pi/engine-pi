package de.pirckheimer_gymnasium.engine_pi.resources;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.awt.Color;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import de.pirckheimer_gymnasium.engine_pi.Resources;

public class ColorContainerTest
{
    ColorContainer container = Resources.colors;

    @BeforeEach
    void clear()
    {
        container.clear();
        container.addScheme(ColorSchemeSelection.GNOME.getScheme());
    }

    @Nested
    class GetColorSafeTest
    {
        @Test
        public void testPrimaryName()
        {
            assertNotNull(container.getSafe("blue"));
        }

        @Test
        public void testAlias()
        {
            assertNotNull(container.getSafe("blau"));
        }

        @Test
        public void testCaseInsensitivity()
        {
            assertNotNull(container.getSafe("BLUE"));
        }

        @Test
        public void testWhiteSpaces()
        {
            assertNotNull(container.getSafe("b l u e"));
        }

        @Test
        public void testHexCode()
        {
            Color actual = container.getSafe("#aabbccdd");
            Color expected = new Color(0xaa, 0xbb, 0xcc, 0xdd);
            assertEquals(actual.getRed(), expected.getRed());
            assertEquals(actual.getGreen(), expected.getGreen());
            assertEquals(actual.getBlue(), expected.getBlue());
            assertEquals(actual.getAlpha(), expected.getAlpha());
        }

        @Test
        public void testNoException()
        {
            assertNotNull(container.getSafe("XXX"));
        }
    }

    @Nested
    class GetColorTest
    {
        @Test
        public void testPrimaryName()
        {
            assertNotNull(container.get("blue"));
        }

        @Test
        public void testAlias()
        {
            assertNotNull(container.get("blau"));
        }

        @Test
        public void testCaseInsensitivity()
        {
            assertNotNull(container.get("BLUE"));
        }

        @Test
        public void testWhiteSpaces()
        {
            assertNotNull(container.get("b l u e"));
        }

        @Test
        public void testHexCode()
        {
            Color actual = container.get("#aabbccdd");
            Color expected = new Color(0xaa, 0xbb, 0xcc, 0xdd);
            assertEquals(actual.getRed(), expected.getRed());
            assertEquals(actual.getGreen(), expected.getGreen());
            assertEquals(actual.getBlue(), expected.getBlue());
            assertEquals(actual.getAlpha(), expected.getAlpha());
        }

        @Test
        public void testException()
        {
            assertThrows(RuntimeException.class, () -> container.get("XXX"));
        }
    }
}
