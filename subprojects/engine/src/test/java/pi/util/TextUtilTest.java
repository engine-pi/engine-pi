package pi.util;

import static pi.util.TextAlignment.CENTER;
import static pi.util.TextAlignment.LEFT;
import static pi.util.TextAlignment.RIGHT;
import static pi.util.TextUtil.align;
import static pi.util.TextUtil.getLineCount;
import static pi.util.TextUtil.getLineWidth;
import static pi.util.TextUtil.wrap;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class TextUtilTest
{
    @Test
    void testRoundNumber()
    {
        assertEquals(TextUtil.roundNumber(1.2345), "1.2");
    }

    @Test
    void testRoundNumberWithDecimalPlaces()
    {
        assertEquals(TextUtil.roundNumber(1.2345, 3), "1.234");
    }

    @Test
    void testGetLineWidth()
    {
        assertEquals(getLineWidth("Lorem\nipsum\ndolor sit"), 9);
    }

    @Test
    void testGetLineCount()
    {
        assertEquals(getLineCount("Lorem\nipsum\ndolor sit"), 3);
    }

    @Nested
    class AlignTest
    {
        String text = "Lorem ipsum\ndolor sit\namet.";

        String oneLine = "Lorem ipsum";

        @Test
        void testOneLine()
        {
            assertEquals(align(oneLine, LEFT), "Lorem ipsum");
        }

        @Test
        void testLeft()
        {
            assertEquals(align(text, LEFT),
                    "Lorem ipsum\n" + "dolor sit\n" + "amet.");
        }

        @Test
        void testWidth()
        {
            assertEquals(align(text, 15, LEFT),
                    "Lorem ipsum\n" + "dolor sit\n" + "amet.");
        }

        @Test
        void testWidthOnOneLine()
        {
            assertEquals(align(oneLine, 15, LEFT), "Lorem ipsum");
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
                    "Lorem ipsum\n" + " dolor sit\n" + "   amet.");
        }
    }

    @Nested
    class WrapTest
    {
        String text = "Lorem ipsum dolor sit";

        @Test
        void testLeft()
        {
            assertEquals(wrap(text, 10, LEFT), "Lorem\nipsum\ndolor sit");
        }

        @Test
        void testCenter()
        {
            assertEquals(wrap(text, 10, CENTER),
                    "  Lorem\n" + "  ipsum\n" + "dolor sit");
        }

        @Test
        void testRight()
        {
            assertEquals(wrap(text, 10, RIGHT),
                    "     Lorem\n" + "     ipsum\n" + " dolor sit");
        }

        @Test
        void testWidthNotToSmall()
        {
            assertEquals(wrap(text, 10), "Lorem\nipsum\ndolor sit");
        }

        @Test
        void testWidthToSmall()
        {
            assertThrows(IllegalArgumentException.class, () -> wrap(text, 4));
        }

        @Test
        void testInputWithNewlines()
        {
            assertEquals(wrap("Lorem\nipsum\ndolor\nsit", 10),
                    "Lorem\nipsum\ndolor\nsit");
        }
    }
}
