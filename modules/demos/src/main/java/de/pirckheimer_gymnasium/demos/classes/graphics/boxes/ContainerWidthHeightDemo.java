package de.pirckheimer_gymnasium.demos.classes.graphics.boxes;

import static pi.Resources.colors;
import static pi.graphics.boxes.Boxes.background;
import static pi.graphics.boxes.Boxes.container;
import static pi.graphics.boxes.Boxes.empty;

import java.awt.Graphics2D;

import de.pirckheimer_gymnasium.demos.graphics2d.Graphics2DComponent;

// Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/modules/engine/src/main/java/pi/graphics/boxes/ContainerBox.java

public class ContainerWidthHeightDemo extends Graphics2DComponent
{
    public void render(Graphics2D g)
    {
        background(container(empty()).width(200).height(200))
                .color(colors.get("red")).render(g).debug();
    }

    public static void main(String[] args)
    {
        new ContainerWidthHeightDemo().show();
    }
}
