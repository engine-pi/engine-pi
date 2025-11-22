package de.pirckheimer_gymnasium.demos.classes.graphics.boxes;

import static de.pirckheimer_gymnasium.engine_pi.graphics.boxes.Boxes.vertical;

import java.awt.Graphics2D;

import de.pirckheimer_gymnasium.demos.graphics2d.Graphics2DComponent;
import de.pirckheimer_gymnasium.engine_pi.graphics.boxes.BorderBox;
import de.pirckheimer_gymnasium.engine_pi.graphics.boxes.CombinedChildBox;
import de.pirckheimer_gymnasium.engine_pi.graphics.boxes.MarginBox;
import de.pirckheimer_gymnasium.engine_pi.graphics.boxes.TextLineBox;

// Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/modules/engine/src/main/java/de/pirckheimer_gymnasium/engine_pi/graphics/boxes/MarginBox.java

class MarginTestBox extends CombinedChildBox
{
    BorderBox outerBorder;

    MarginBox margin;

    BorderBox innerBorder;

    TextLineBox textLine;

    public MarginTestBox(String content)
    {
        textLine = new TextLineBox(content);
        innerBorder = new BorderBox(textLine);
        innerBorder.thickness(1).color("gray");
        margin = new MarginBox(innerBorder);
        outerBorder = new BorderBox(margin);
        outerBorder.thickness(2).color("blue");
        addChild(outerBorder);
    }
}

public class MarginBoxDemo extends Graphics2DComponent
{
    private MarginTestBox box(String content)
    {
        return new MarginTestBox(content);
    }

    public void render(Graphics2D g)
    {
        var defaultSettings = box("default");
        var allSides = box(".allSides(50)");
        allSides.margin.allSides(50);
        var different = box(".top(5).right(10).bottom(15).left(20)");
        different.margin.top(5).right(10).bottom(15).left(20);
        vertical(defaultSettings, allSides, different).anchor(50, 50).render(g);
    }

    public static void main(String[] args)
    {
        new MarginBoxDemo().show();
    }
}
