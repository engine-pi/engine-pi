package de.pirckheimer_gymnasium.demos.classes.graphics.boxes;

import java.awt.Font;
import java.awt.Graphics2D;

import de.pirckheimer_gymnasium.demos.graphics2d.Graphics2DComponent;
import de.pirckheimer_gymnasium.engine_pi.graphics.boxes.TextBox;
import de.pirckheimer_gymnasium.engine_pi.graphics.boxes.VerticalContainerBox;
import static de.pirckheimer_gymnasium.engine_pi.Resources.fonts;

public class VerticalContainerBoxDemo extends Graphics2DComponent
{

    Font font = fonts.getDefault().deriveFont(64f);

    public void render(Graphics2D g)
    {
        VerticalContainerBox container = new VerticalContainerBox(
                new TextBox("Text 1", font),
                new TextBox("Text 2", font.deriveFont(32f)),
                new TextBox("Text 3", font));
        container.x(400);
        container.y(100);
        container.render(g);

    }

    public static void main(String[] args)
    {
        new VerticalContainerBoxDemo().show();
    }
}
