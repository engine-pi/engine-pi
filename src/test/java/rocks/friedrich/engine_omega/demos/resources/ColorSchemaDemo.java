package rocks.friedrich.engine_omega.demos.resources;

import static rocks.friedrich.engine_omega.resources.Container.colors;

import java.awt.Color;
import java.util.Map.Entry;

import rocks.friedrich.engine_omega.Game;
import rocks.friedrich.engine_omega.Scene;

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
