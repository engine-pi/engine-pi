package de.pirckheimer_gymnasium.demos.classes.graphics.boxes;

import static pi.Resources.colors;

import pi.graphics.boxes.FramedTextBox;
import pi.graphics.boxes.HAlign;
import pi.graphics.boxes.VAlign;

public class DemoBoxes
{
    public static FramedTextBox demo()
    {
        FramedTextBox box = new FramedTextBox("");
        box.background.color(colors.get("blue", 100));
        box.padding.allSides(10);
        box.container.hAlign(HAlign.CENTER);
        box.container.vAlign(VAlign.MIDDLE);
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
        b.container.width(width);
        b.container.height(height);
        return b;
    }
}
