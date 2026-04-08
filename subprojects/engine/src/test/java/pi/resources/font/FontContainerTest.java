package pi.resources.font;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIf;

import pi.resources.ResourceLoadException;
import pi.resources.Resources;

@DisabledIf(value = "java.awt.GraphicsEnvironment#isHeadless", disabledReason = "headless environment")
class FontContainerTest
{
    // Nicht von Controller importieren, da die Tests auf Github headless
    // laufen.
    FontContainer container = Resources.fonts;

    @BeforeEach
    @AfterEach
    void clear()
    {
        container.clear();
    }

    @Test
    void loadFromResources()
    {
        var font = container.get("fonts/Cantarell-Bold.ttf");
        assertEquals(font.getName(), "Cantarell Bold");
    }

    @Test
    void loadSystemFonts()
    {
        var font = container.get("DejaVu Serif");
        assertEquals(font.getName(), "DejaVu Serif");
    }

    @Test
    void defaultFont()
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
        void plain()
        {
            assertEquals(0, getStyle(FontStyle.PLAIN));
        }

        @Test
        void bold()
        {
            assertEquals(1, getStyle(FontStyle.BOLD));
        }

        @Test
        void italic()
        {
            assertEquals(2, getStyle(FontStyle.ITALIC));
        }

        @Test
        void boldItalic()
        {
            assertEquals(3, getStyle(FontStyle.BOLD_ITALIC));
        }
    }

    @Test
    void throwsException()
    {
        ResourceLoadException exception = assertThrows(
            ResourceLoadException.class,
            () -> container.get("xxx"));
        assertEquals("Die Ressource konnte nicht geladen werden: xxx",
            exception.getMessage());;
    }
}
