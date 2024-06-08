package de.pirckheimer_gymnasium.engine_pi.demos.resources;

import static de.pirckheimer_gymnasium.engine_pi.resources.Container.colors;

import java.awt.Color;
import java.util.Map.Entry;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Scene;

public class ColorSchemaDemo extends Scene
{
    public ColorSchemaDemo()
    {
        int x = -12;
        for (Entry<String, Color> entry : colors.getAll().entrySet())
        {
            createCircle(x, 0, entry.getValue());
            System.out.println(entry.getKey());
            x += 2;
        }
    }

    public static void main(String[] args)
    {
        Game.start(new ColorSchemaDemo());
    }
}
