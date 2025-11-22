package de.pirckheimer_gymnasium.demos.classes.graphics.boxes;

import static de.pirckheimer_gymnasium.engine_pi.graphics.boxes.Boxes.border;
import static de.pirckheimer_gymnasium.engine_pi.graphics.boxes.Boxes.margin;
import static de.pirckheimer_gymnasium.engine_pi.graphics.boxes.Boxes.textLine;
import static de.pirckheimer_gymnasium.engine_pi.graphics.boxes.Boxes.vertical;

import java.awt.Graphics2D;

import de.pirckheimer_gymnasium.demos.graphics2d.Graphics2DComponent;

// Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/modules/engine/src/main/java/de/pirckheimer_gymnasium/engine_pi/graphics/boxes/MarginBox.java

public class MarginBoxDemo extends Graphics2DComponent
{

    public void render(Graphics2D g)
    {
        var defaultSettings = border(margin(border(textLine("default"))));
        var manuel = margin(textLine(".allSides(50)")).allSides(50);
        var different = border(margin(
                border(textLine(".top(5).right(10).bottom(15).left(20)")))
                .top(5).right(10).bottom(15).left(20));
        vertical(defaultSettings, manuel, different).anchor(50, 50).render(g);
    }

    public static void main(String[] args)
    {
        new MarginBoxDemo().show();
    }
}
