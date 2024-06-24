package de.pirckheimer_gymnasium.engine_pi.resources;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.pirckheimer_gymnasium.engine_pi.Resources;

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
    public void testLoad()
    {
        var font = container.get("fonts/Cantarell-Bold.ttf");
        assertEquals(font.getName(), "Cantarell Bold");
    }
}
