package de.pirckheimer_gymnasium.demos.classes.graphics.boxes;

import static pi.Resources.colors;
import static pi.graphics.boxes.Boxes.background;
import static pi.graphics.boxes.Boxes.margin;
import static pi.graphics.boxes.Boxes.textLine;
import static pi.graphics.boxes.Boxes.vertical;

import java.awt.Graphics2D;

import de.pirckheimer_gymnasium.demos.graphics2d.Graphics2DComponent;

// Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/modules/engine/src/main/java/pi/graphics/boxes/BackgroundBox.java

public class BackgroundBoxDemo extends Graphics2DComponent
{
    public void render(Graphics2D g)
    {
        var defaultSettings = background(textLine("default"));

        var custom = background(textLine("custom")).color(colors.get("red"));

        var nested = background(margin(
                background(textLine("nested")).color(colors.get("yellow"))))
                .color(colors.get("green"));

        vertical(defaultSettings, custom, nested).anchor(0, 0).render(g)
                .debug();
    }

    public static void main(String[] args)
    {
        new BackgroundBoxDemo().show();
    }
}
