package pi.resources.font;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static pi.Controller.fonts;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIf;

@DisabledIf(value = "java.awt.GraphicsEnvironment#isHeadless", disabledReason = "headless environment")
public class FontContainerTest
{
    FontContainer container = fonts;

    @BeforeEach
    @AfterEach
    void clear()
    {
        container.clear();
    }

    @Test
    public void testLoadFromResources()
    {
        var font = container.get("fonts/Cantarell-Bold.ttf");
        assertEquals(font.getName(), "Cantarell Bold");
    }

    @Test
    public void testLoadSystemFonts()
    {
        var font = container.get("DejaVu Serif");
        assertEquals(font.getName(), "DejaVu Serif");
    }

    @Test
    public void testDefaultFont()
    {
        var font = container.defaultFont(FontStyle.PLAIN);

        assertEquals("Cantarell Regular", font.getName());
        assertEquals(0, font.getStyle());
    }

    @Nested
    class DefaultTest
    {

        private int getStyle(FontStyle style)
        {
            return container.defaultFont(style).getStyle();
        }

        @Test
        public void testPlain()
        {
            assertEquals(0, getStyle(FontStyle.PLAIN));
        }

        @Test
        public void testBold()
        {
            assertEquals(1, getStyle(FontStyle.BOLD));
        }

        @Test
        public void testItalic()
        {
            assertEquals(2, getStyle(FontStyle.ITALIC));
        }

        @Test
        public void testBoldItalic()
        {
            assertEquals(3, getStyle(FontStyle.BOLD_ITALIC));
        }
    }
}
