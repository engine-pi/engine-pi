package de.pirckheimer_gymnasium.demos.classes.graphics.boxes;

import static de.pirckheimer_gymnasium.engine_pi.Resources.colors;
import static de.pirckheimer_gymnasium.engine_pi.graphics.boxes.Box.border;
import static de.pirckheimer_gymnasium.engine_pi.graphics.boxes.Box.text;
import static de.pirckheimer_gymnasium.engine_pi.graphics.boxes.Box.vertical;

import java.awt.Graphics2D;

import de.pirckheimer_gymnasium.demos.graphics2d.Graphics2DComponent;

public class BorderBoxDemo extends Graphics2DComponent
{

    public void render(Graphics2D g)
    {
        var defaultSettings = border(text("default"));

        var lineWidth = border(text("lineWidth")).thickness(5);
        var lineColor = border(text("lineColor")).color(colors.get("blue"));

        vertical(defaultSettings, lineWidth, lineColor).anchor(0, 0).render(g);
    }

    public static void main(String[] args)
    {
        new BorderBoxDemo().show();
    }
}
