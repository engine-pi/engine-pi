package de.pirckheimer_gymnasium.engine_pi.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TextUtilTest
{
    @Test
    void testRoundNumber()
    {
        assertEquals(TextUtil.roundNumber(1.2345), "1.23");
    }

    @Test
    void testWrap()
    {
        assertEquals(TextUtil.wrap("Lorem ipsum dolor sit", 10),
                "Lorem\nipsum\ndolor sit");
    }
}
