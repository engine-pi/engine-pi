package de.pirckheimer_gymnasium.engine_pi.resources;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIf;

import de.pirckheimer_gymnasium.engine_pi.Resources;

@DisabledIf(value = "java.awt.GraphicsEnvironment#isHeadless", disabledReason = "headless environment")
public class FontContainerTest
{
    FontContainer container = Resources.FONTS;

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
}
