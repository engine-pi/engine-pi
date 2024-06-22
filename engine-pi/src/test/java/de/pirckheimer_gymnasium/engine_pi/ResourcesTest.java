package de.pirckheimer_gymnasium.engine_pi;

import static de.pirckheimer_gymnasium.engine_pi.Resources.getColor;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;

public class ResourcesTest
{
    @Nested
    class GetColorTest
    {
        @Test
        public void testPrimaryName()
        {
            assertNotNull(getColor("blue"));
        }

        @Test
        public void testAlias()
        {
            assertNotNull(getColor("blau"));
        }

        @Test
        public void testCaseInsensivity()
        {
            assertNotNull(getColor("BLUE"));
        }

        @Test
        public void testWhiteSpaces()
        {
            assertNotNull(getColor("b l u e"));
        }

        @Test
        public void testException()
        {
            assertThrows(RuntimeException.class, () -> getColor("XXX"));
        }
    }
}
