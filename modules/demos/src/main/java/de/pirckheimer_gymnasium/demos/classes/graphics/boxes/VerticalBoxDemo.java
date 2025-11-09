package de.pirckheimer_gymnasium.demos.classes.graphics.boxes;

import static de.pirckheimer_gymnasium.engine_pi.Resources.fonts;

import java.awt.Font;
import java.awt.Graphics2D;

import de.pirckheimer_gymnasium.demos.graphics2d.Graphics2DComponent;
import de.pirckheimer_gymnasium.engine_pi.graphics.boxes.TextBox;
import de.pirckheimer_gymnasium.engine_pi.graphics.boxes.VerticalBox;

public class VerticalBoxDemo extends Graphics2DComponent
{
    Font font = fonts.getDefault().deriveFont(64f);

    public void render(Graphics2D g)
    {
        VerticalBox container = new VerticalBox(new TextBox("Text 1", font),
                new TextBox("Text 2", font.deriveFont(32f)),
                new TextBox("Text 3", font));
        container.x(200);
        container.y(100);
        container.render(g);
    }

    public static void main(String[] args)
    {
        new VerticalBoxDemo().show();
    }
}
