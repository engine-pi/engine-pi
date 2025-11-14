package de.pirckheimer_gymnasium.demos.classes.graphics.boxes;

import static de.pirckheimer_gymnasium.engine_pi.Resources.colors;
import static de.pirckheimer_gymnasium.engine_pi.graphics.boxes.Box.background;
import static de.pirckheimer_gymnasium.engine_pi.graphics.boxes.Box.margin;
import static de.pirckheimer_gymnasium.engine_pi.graphics.boxes.Box.text;
import static de.pirckheimer_gymnasium.engine_pi.graphics.boxes.Box.vertical;

import java.awt.Graphics2D;

import de.pirckheimer_gymnasium.demos.graphics2d.Graphics2DComponent;

public class BackgroundBoxDemo extends Graphics2DComponent
{
    public void render(Graphics2D g)
    {
        var defaultSettings = background(text("default"));

        var custom = background(text("custom")).color(colors.get("red"));

        var nested = background(
                margin(background(text("nested")).color(colors.get("yellow"))))
                .color(colors.get("green"));

        vertical(defaultSettings, custom, nested).anchor(0, 0).render(g);
    }

    public static void main(String[] args)
    {
        new BackgroundBoxDemo().show();
    }
}
