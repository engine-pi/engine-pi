package de.pirckheimer_gymnasium.demos.classes.graphics.boxes;

import java.awt.Graphics2D;

import de.pirckheimer_gymnasium.demos.graphics2d.Graphics2DComponent;
import de.pirckheimer_gymnasium.engine_pi.graphics.boxes.HAlign;
import de.pirckheimer_gymnasium.engine_pi.graphics.boxes.TextTableBox;

// Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/modules/engine/src/main/java/de/pirckheimer_gymnasium/engine_pi/graphics/boxes/TextTableBox.java

public class TextTableBoxDemo extends Graphics2DComponent
{

    public void render(Graphics2D g)
    {
        new TextTableBox("Cell 1", "Cell 2", "Cell 3").row(0, b -> b.width(400))
                .cell(0, 0, b -> {
                    b.text.color("red");
                    b.text.content("This is cell 0,0");
                }).column(0, b -> b.hAlign(HAlign.RIGHT)).padding(10).render(g);
    }

    public static void main(String[] args)
    {
        new TextTableBoxDemo().show();
    }
}
