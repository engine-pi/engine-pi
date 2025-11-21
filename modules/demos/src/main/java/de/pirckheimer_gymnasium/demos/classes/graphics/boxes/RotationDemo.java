package de.pirckheimer_gymnasium.demos.classes.graphics.boxes;

import static de.pirckheimer_gymnasium.engine_pi.graphics.boxes.Box.border;
import static de.pirckheimer_gymnasium.engine_pi.graphics.boxes.Box.margin;
import static de.pirckheimer_gymnasium.engine_pi.graphics.boxes.Box.textLine;
import static de.pirckheimer_gymnasium.engine_pi.graphics.boxes.Box.vertical;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import de.pirckheimer_gymnasium.demos.graphics2d.Graphics2DComponent;

public class RotationDemo extends Graphics2DComponent
{

    public void render(Graphics2D g)
    {
        var defaultSettings = border(margin(border(textLine("default"))));

        var manuel = margin(textLine("default")).margin(50);

        AffineTransform oldTransform = g.getTransform();

        AffineTransform newTransform = new AffineTransform();
        newTransform.rotate(Math.toRadians(-45));
        g.setTransform(newTransform);

        vertical(defaultSettings, manuel).anchor(-100, 100).render(g);
        g.setTransform(oldTransform);
    }

    public static void main(String[] args)
    {
        new RotationDemo().show();
    }
}
