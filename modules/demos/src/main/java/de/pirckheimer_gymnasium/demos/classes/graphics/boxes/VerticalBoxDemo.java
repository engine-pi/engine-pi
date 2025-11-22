package de.pirckheimer_gymnasium.demos.classes.graphics.boxes;

import static de.pirckheimer_gymnasium.demos.classes.graphics.boxes.DemoBoxes.demo;
import static de.pirckheimer_gymnasium.engine_pi.graphics.boxes.Boxes.vertical;
import static de.pirckheimer_gymnasium.engine_pi.graphics.boxes.Boxes.border;

import java.awt.Graphics2D;

import de.pirckheimer_gymnasium.demos.graphics2d.Graphics2DComponent;

// Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/modules/engine/src/main/java/de/pirckheimer_gymnasium/engine_pi/graphics/boxes/VerticalBox.java

public class VerticalBoxDemo extends Graphics2DComponent
{
    public void render(Graphics2D g)
    {
        var box = border(vertical(demo("Text 1"), demo("Text 2", 100, 70),
                demo("Text 3")).padding(10));
        box.anchor(200, 100);
        box.render(g);
    }

    public static void main(String[] args)
    {
        new VerticalBoxDemo().show();
    }
}
