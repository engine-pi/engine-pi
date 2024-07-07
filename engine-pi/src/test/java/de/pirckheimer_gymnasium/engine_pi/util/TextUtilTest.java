package de.pirckheimer_gymnasium.engine_pi.util;

import static de.pirckheimer_gymnasium.engine_pi.util.TextAlignment.CENTER;
import static de.pirckheimer_gymnasium.engine_pi.util.TextAlignment.RIGHT;
import static de.pirckheimer_gymnasium.engine_pi.util.TextUtil.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Nested;
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
        assertEquals(wrap("Lorem ipsum dolor sit", 10),
                "Lorem\nipsum\ndolor sit");
    }

    @Test
    void testGetLineWidth()
    {
        assertEquals(getLineWidth("Lorem\nipsum\ndolor sit"), 9);
    }

    @Nested
    class AlignTest
    {
        String text = "Lorem ipsum\ndolor sit\namet.";

        String oneLine = "Lorem ipsum";

        @Test
        void testOneLine()
        {
            assertEquals(align(oneLine), "Lorem ipsum");
        }

        @Test
        void testLeft()
        {
            assertEquals(align(text),
                    "Lorem ipsum\n" + "dolor sit  \n" + "amet.      ");
        }

        @Test
        void testWidth()
        {
            assertEquals(align(text, 15), "Lorem ipsum    \n"
                    + "dolor sit      \n" + "amet.          ");
        }

        @Test
        void testWidthOnOneLine()
        {
            assertEquals(align(oneLine, 15), "Lorem ipsum    ");
        }

        @Test
        void testRight()
        {
            assertEquals(align(text, RIGHT),
                    "Lorem ipsum\n" + "  dolor sit\n" + "      amet.");
        }

        @Test
        void testCenter()
        {
            assertEquals(align(text, CENTER),
                    "Lorem ipsum\n" + " dolor sit \n" + "   amet.   ");
        }
    }
}
