package de.pirckheimer_gymnasium.demos.classes.graphics.boxes;

import static de.pirckheimer_gymnasium.engine_pi.graphics.boxes.Box.framedText;
import static de.pirckheimer_gymnasium.engine_pi.graphics.boxes.Box.grid;

import java.awt.Graphics2D;

import de.pirckheimer_gymnasium.demos.graphics2d.Graphics2DComponent;
import de.pirckheimer_gymnasium.engine_pi.graphics.boxes.FramedTextBox;

// Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/modules/engine/src/main/java/de/pirckheimer_gymnasium/engine_pi/graphics/boxes/GridBox.java

public class GridBoxDemo extends Graphics2DComponent
{

    private FramedTextBox box(int number)
    {
        var box = framedText(number + "");
        box.padding.allSides(number * 3);
        return box;
    }

    private FramedTextBox[] boxes(int numberOfBoxes)
    {
        FramedTextBox[] boxes = new FramedTextBox[numberOfBoxes];
        for (int i = 0; i < boxes.length; i++)
        {
            boxes[i] = box(i);
        }
        return boxes;
    }

    public void render(Graphics2D g)
    {
        var box = grid(boxes(9));
        box.anchor(200, 100);
        box.render(g);
    }

    public static void main(String[] args)
    {
        new GridBoxDemo().show();
    }
}
