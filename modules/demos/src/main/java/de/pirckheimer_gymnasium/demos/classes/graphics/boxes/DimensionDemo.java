package de.pirckheimer_gymnasium.demos.classes.graphics.boxes;

import static de.pirckheimer_gymnasium.engine_pi.Resources.colors;
import static de.pirckheimer_gymnasium.engine_pi.graphics.boxes.Box.background;
import static de.pirckheimer_gymnasium.engine_pi.graphics.boxes.Box.dimension;
import static de.pirckheimer_gymnasium.engine_pi.graphics.boxes.Box.empty;

import java.awt.Graphics2D;

import de.pirckheimer_gymnasium.demos.graphics2d.Graphics2DComponent;

// Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/modules/engine/src/main/java/de/pirckheimer_gymnasium/engine_pi/graphics/boxes/DimensionBox.java

public class DimensionDemo extends Graphics2DComponent
{
    public void render(Graphics2D g)
    {
        background(dimension(empty()).width(200).height(200))
                .color(colors.get("red")).render(g);
    }

    public static void main(String[] args)
    {
        new DimensionDemo().show();
    }
}
