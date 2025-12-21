package de.pirckheimer_gymnasium.demos.classes.graphics.boxes;

import java.awt.Graphics2D;

import de.pirckheimer_gymnasium.demos.graphics2d.Graphics2DComponent;
import pi.graphics.boxes.BorderBox;
import pi.graphics.boxes.HAlign;
import pi.graphics.boxes.TextTableBox;

// Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/modules/engine/src/main/java/pi/graphics/boxes/TextTableBox.java

public class TextTableBoxDemo extends Graphics2DComponent
{
    public void render(Graphics2D g)
    {
        TextTableBox table = new TextTableBox("Cell 1", "Cell 2", "Cell 3",
                "Cell 4\nhas\nmultiple lines");
        table.forEachRowBox(0, b -> b.width(300));
        table.forEachColumnBox(0, b -> {
            b.hAlign(HAlign.RIGHT);
            b.text.color("green");
        });
        table.forBox(0, 0, b -> {
            b.text.color("red");
            b.text.content("This is cell 0,0");
        });
        table.padding(10);
        table.forEachColumnBox(1, b -> {
            b.hAlign(HAlign.RIGHT);
            b.text.color("green");
        });
        new BorderBox(table).color("black").render(g).debug();
    }

    public static void main(String[] args)
    {
        new TextTableBoxDemo().show();
    }
}
