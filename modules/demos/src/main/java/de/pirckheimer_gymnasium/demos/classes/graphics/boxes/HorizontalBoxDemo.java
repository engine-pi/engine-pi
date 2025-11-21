package de.pirckheimer_gymnasium.demos.classes.graphics.boxes;

import static de.pirckheimer_gymnasium.engine_pi.Resources.fonts;
import static de.pirckheimer_gymnasium.engine_pi.graphics.boxes.Box.textLine;
import static de.pirckheimer_gymnasium.engine_pi.graphics.boxes.Box.horizontal;

import java.awt.Font;
import java.awt.Graphics2D;

import de.pirckheimer_gymnasium.demos.graphics2d.Graphics2DComponent;

// Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/modules/engine/src/main/java/de/pirckheimer_gymnasium/engine_pi/graphics/boxes/HorizontalBox.java

public class HorizontalBoxDemo extends Graphics2DComponent
{
    Font font = fonts.getDefault().deriveFont(64f);

    public void render(Graphics2D g)
    {
        var box = horizontal(textLine("Text 1", font),
                textLine("Text 2", font.deriveFont(32f)),
                textLine("Text 3", font));
        box.anchor(200, 100);
        box.render(g);
    }

    public static void main(String[] args)
    {
        new HorizontalBoxDemo().show();
    }
}
