package de.pirckheimer_gymnasium.demos.classes.graphics.boxes;

import static de.pirckheimer_gymnasium.demos.classes.graphics.boxes.DemoBoxes.demo;
import static pi.graphics.boxes.Boxes.border;
import static pi.graphics.boxes.HAlign.CENTER;

import java.awt.Graphics2D;

import de.pirckheimer_gymnasium.demos.graphics2d.Graphics2DComponent;
import pi.graphics.boxes.AlignableVerticalBox;

// Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/modules/engine/src/main/java/de/pirckheimer_gymnasium/engine_pi/graphics/boxes/VerticalBox.java

public class AlignableVerticalBoxDemo extends Graphics2DComponent
{
    public void render(Graphics2D g)
    {
        var box = border(new AlignableVerticalBox(demo("Text 1"),
                demo("Text 2", 100, 70), demo("Text 3")).hAlign(CENTER)
                .padding(10));
        box.anchor(200, 100);
        box.render(g);
    }

    public static void main(String[] args)
    {
        new AlignableVerticalBoxDemo().show();
    }
}
