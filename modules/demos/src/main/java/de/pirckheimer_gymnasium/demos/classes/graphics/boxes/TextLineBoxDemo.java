package de.pirckheimer_gymnasium.demos.classes.graphics.boxes;

import static de.pirckheimer_gymnasium.engine_pi.Resources.fonts;
import static de.pirckheimer_gymnasium.engine_pi.graphics.boxes.Box.textLine;
import static de.pirckheimer_gymnasium.engine_pi.graphics.boxes.Box.vertical;

import java.awt.Font;
import java.awt.Graphics2D;

import de.pirckheimer_gymnasium.demos.graphics2d.Graphics2DComponent;

// Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/modules/engine/src/main/java/de/pirckheimer_gymnasium/engine_pi/graphics/boxes/TextLineBox.java

public class TextLineBoxDemo extends Graphics2DComponent
{
    Font font = fonts.getDefault().deriveFont(64f);

    public void render(Graphics2D g)
    {
        textLine("as standalone box").fontSize(32).anchor(500, 400).render(g);

        var box = vertical(textLine("default"),
                textLine("different fontSize").fontSize(42),
                textLine("custom Font", font),
                textLine("custom color").color("orange"),
                textLine("custom content").content("updated content"));
        box.anchor(200, 100);
        box.render(g);
    }

    public static void main(String[] args)
    {
        new TextLineBoxDemo().show();
    }
}
