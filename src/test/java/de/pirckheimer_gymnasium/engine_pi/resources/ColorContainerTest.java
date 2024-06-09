package de.pirckheimer_gymnasium.engine_pi.resources;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.pirckheimer_gymnasium.engine_pi.Resources;

public class ColorContainerTest
{
    ColorContainer container = Resources.colors;

    @BeforeEach
    void clear()
    {
        container.clear();
    }

    @Test
    public void testManipulation()
    {
    }
}
