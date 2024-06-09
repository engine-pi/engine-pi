package de.pirckheimer_gymnasium.engine_pi.demos.resources;

import static de.pirckheimer_gymnasium.engine_pi.Resources.colors;

import java.awt.Color;
import java.util.Map.Entry;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Scene;

public class ColorSchemaDemo extends Scene
{
    public ColorSchemaDemo()
    {
        int x = -16;
        int labelY = -2;
        for (Entry<String, Color> entry : colors.getAll().entrySet())
        {
            createCircle(x, 0, entry.getValue());
            createText(entry.getKey(), 0.5, x, labelY);
            System.out.println(entry.getKey());
            x += 2;
            labelY -= 2;
            if (labelY < -4)
            {
                labelY = -2;
            }
        }
    }

    public static void main(String[] args)
    {
        Game.start(1200, 400, new ColorSchemaDemo());
    }
}
