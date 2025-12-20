package de.pirckheimer_gymnasium.demos.classes.graphics.boxes;

import java.awt.Graphics2D;

import de.pirckheimer_gymnasium.demos.graphics2d.Graphics2DComponent;
import pi.graphics.boxes.HAlign;
import pi.graphics.boxes.TextTableBox;

// Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/modules/engine/src/main/java/de/pirckheimer_gymnasium/engine_pi/graphics/boxes/TextTableBox.java

public class TextTableBoxDemo extends Graphics2DComponent
{
    public void render(Graphics2D g)
    {
        TextTableBox table = new TextTableBox("Cell 1", "Cell 2", "Cell 3",
                "Cell 4");
        table.row(0, b -> b.width(400));
        table.column(0, b -> {
            b.hAlign(HAlign.RIGHT);
            b.text.color("green");
        });
        table.cell(0, 0, b -> {
            b.text.color("red");
            b.text.content("This is cell 0,0");
        });
        table.padding(10);
        table.render(g);
    }

    public static void main(String[] args)
    {
        new TextTableBoxDemo().show();
    }
}
