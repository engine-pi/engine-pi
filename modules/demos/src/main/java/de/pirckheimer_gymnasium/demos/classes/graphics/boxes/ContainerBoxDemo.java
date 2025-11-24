package de.pirckheimer_gymnasium.demos.classes.graphics.boxes;

import static de.pirckheimer_gymnasium.engine_pi.Resources.colors;
import static de.pirckheimer_gymnasium.engine_pi.graphics.boxes.Boxes.container;
import static de.pirckheimer_gymnasium.engine_pi.graphics.boxes.Boxes.background;
import static de.pirckheimer_gymnasium.engine_pi.graphics.boxes.Boxes.textLine;
import static de.pirckheimer_gymnasium.engine_pi.graphics.boxes.HAlign.CENTER;
import static de.pirckheimer_gymnasium.engine_pi.graphics.boxes.HAlign.LEFT;
import static de.pirckheimer_gymnasium.engine_pi.graphics.boxes.HAlign.RIGHT;
import static de.pirckheimer_gymnasium.engine_pi.graphics.boxes.VAlign.BOTTOM;
import static de.pirckheimer_gymnasium.engine_pi.graphics.boxes.VAlign.MIDDLE;
import static de.pirckheimer_gymnasium.engine_pi.graphics.boxes.VAlign.TOP;

import java.awt.Graphics2D;

import de.pirckheimer_gymnasium.demos.graphics2d.Graphics2DComponent;
import de.pirckheimer_gymnasium.engine_pi.graphics.boxes.HAlign;
import de.pirckheimer_gymnasium.engine_pi.graphics.boxes.VAlign;

// Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/modules/engine/src/main/java/de/pirckheimer_gymnasium/engine_pi/graphics/boxes/ContainerBox.java

public class ContainerBoxDemo extends Graphics2DComponent
{
    private void makeBox(Graphics2D g, HAlign hAlign, VAlign vAlign)
    {
        int x = 0;
        int y = 0;
        int space = 200;
        switch (hAlign)
        {
        case LEFT:
            break;

        case CENTER:
            x = space;
            break;

        case RIGHT:
            x = 2 * space;
            break;
        }

        switch (vAlign)
        {
        case TOP:
            break;

        case MIDDLE:
            y = space;
            break;

        case BOTTOM:
            y = 2 * space;
            break;
        }

        background(container(textLine(String.format("%s %s", hAlign, vAlign)))
                .hAlign(hAlign).vAlign(vAlign).width(180).height(150))
                .color(colors.get("red")).anchor(x, y).render(g);
    }

    public void render(Graphics2D g)
    {
        makeBox(g, LEFT, TOP);
        makeBox(g, LEFT, MIDDLE);
        makeBox(g, LEFT, BOTTOM);

        makeBox(g, CENTER, TOP);
        makeBox(g, CENTER, MIDDLE);
        makeBox(g, CENTER, BOTTOM);

        makeBox(g, RIGHT, TOP);
        makeBox(g, RIGHT, MIDDLE);
        makeBox(g, RIGHT, BOTTOM);
    }

    public static void main(String[] args)
    {
        new ContainerBoxDemo().show();
    }
}
