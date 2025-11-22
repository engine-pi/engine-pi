package de.pirckheimer_gymnasium.demos.classes.graphics.boxes;

import static de.pirckheimer_gymnasium.engine_pi.Resources.colors;

import de.pirckheimer_gymnasium.engine_pi.graphics.boxes.FramedTextBox;
import de.pirckheimer_gymnasium.engine_pi.graphics.boxes.HAlignment;
import de.pirckheimer_gymnasium.engine_pi.graphics.boxes.VAlignment;

public class DemoBoxes
{
    public static FramedTextBox demo()
    {
        FramedTextBox box = new FramedTextBox("");
        box.background.color(colors.get("blue", 100));
        box.padding.allSides(10);
        box.align.h(HAlignment.CENTER);
        box.align.v(VAlignment.MIDDLE);
        return box;
    }

    public static FramedTextBox demo(String content)
    {
        var b = demo();
        b.textLine.content(content);
        return b;
    }

    public static FramedTextBox demo(String content, int width, int height)
    {
        var b = demo(content);
        b.dimension.width(width);
        b.dimension.height(height);
        return b;
    }
}
