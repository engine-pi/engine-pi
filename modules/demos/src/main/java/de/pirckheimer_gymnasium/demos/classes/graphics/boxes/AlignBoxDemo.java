package de.pirckheimer_gymnasium.demos.classes.graphics.boxes;

import static de.pirckheimer_gymnasium.engine_pi.Resources.colors;
import static de.pirckheimer_gymnasium.engine_pi.graphics.boxes.Boxes.align;
import static de.pirckheimer_gymnasium.engine_pi.graphics.boxes.Boxes.background;
import static de.pirckheimer_gymnasium.engine_pi.graphics.boxes.Boxes.textLine;
import static de.pirckheimer_gymnasium.engine_pi.graphics.boxes.HAlignment.CENTER;
import static de.pirckheimer_gymnasium.engine_pi.graphics.boxes.HAlignment.LEFT;
import static de.pirckheimer_gymnasium.engine_pi.graphics.boxes.HAlignment.RIGHT;
import static de.pirckheimer_gymnasium.engine_pi.graphics.boxes.VAlignment.BOTTOM;
import static de.pirckheimer_gymnasium.engine_pi.graphics.boxes.VAlignment.MIDDLE;
import static de.pirckheimer_gymnasium.engine_pi.graphics.boxes.VAlignment.TOP;

import java.awt.Graphics2D;

import de.pirckheimer_gymnasium.demos.graphics2d.Graphics2DComponent;
import de.pirckheimer_gymnasium.engine_pi.graphics.boxes.HAlignment;
import de.pirckheimer_gymnasium.engine_pi.graphics.boxes.VAlignment;

// Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/modules/engine/src/main/java/de/pirckheimer_gymnasium/engine_pi/graphics/boxes/AlignBox.java

public class AlignBoxDemo extends Graphics2DComponent
{

    private void makeBox(Graphics2D g, HAlignment hAlign, VAlignment vAlign)
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

        background(align(textLine(String.format("%s %s", hAlign, vAlign)))
                .h(hAlign).v(vAlign).width(180).height(150))
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
        new AlignBoxDemo().show();
    }
}
