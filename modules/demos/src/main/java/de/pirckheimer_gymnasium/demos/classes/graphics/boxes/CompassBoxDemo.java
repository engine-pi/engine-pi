package de.pirckheimer_gymnasium.demos.classes.graphics.boxes;

import java.awt.Graphics2D;

import de.pirckheimer_gymnasium.demos.graphics2d.Graphics2DComponent;
import pi.graphics.boxes.CompassBox;
import pi.graphics.boxes.GridBox;

// Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/modules/engine/src/main/java/pi/graphics/boxes/CompassBox.java

public class CompassBoxDemo extends Graphics2DComponent
{
    private CompassBox compass(double direction)
    {
        return new CompassBox(100).direction(direction);
    }

    public void render(Graphics2D g)
    {
        GridBox<CompassBox> grid = new GridBox<>(compass(0), compass(90),
                compass(180), compass(270));
        grid.padding(20).x(200).y(200).render(g).debug();

        new CompassBox(200).direction(90).x(10).y(10).render(g).debug();

    }

    public static void main(String[] args)
    {
        new CompassBoxDemo().show();
    }
}
