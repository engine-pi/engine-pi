package de.pirckheimer_gymnasium.demos.classes.graphics.boxes;

import static pi.Resources.colors;
import static pi.graphics.boxes.Boxes.border;
import static pi.graphics.boxes.Boxes.textLine;
import static pi.graphics.boxes.Boxes.vertical;

import java.awt.Graphics2D;

import de.pirckheimer_gymnasium.demos.graphics2d.Graphics2DComponent;

// Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/modules/engine/src/main/java/pi/graphics/boxes/BorderBox.java

public class BorderBoxDemo extends Graphics2DComponent
{

    public void render(Graphics2D g)
    {
        var defaultSettings = border(textLine("default"));

        var lineWidth = border(textLine("lineWidth")).thickness(5);
        var lineColor = border(textLine("lineColor")).color(colors.get("blue"));

        vertical(defaultSettings, lineWidth, lineColor).anchor(0, 0).render(g);
    }

    public static void main(String[] args)
    {
        new BorderBoxDemo().show();
    }
}
