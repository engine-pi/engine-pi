package de.pirckheimer_gymnasium.demos.classes.graphics.boxes;

import static de.pirckheimer_gymnasium.engine_pi.Resources.fonts;
import static de.pirckheimer_gymnasium.engine_pi.graphics.boxes.Box.text;
import static de.pirckheimer_gymnasium.engine_pi.graphics.boxes.Box.vertical;

import java.awt.Font;
import java.awt.Graphics2D;

import de.pirckheimer_gymnasium.demos.graphics2d.Graphics2DComponent;

public class TextBoxDemo extends Graphics2DComponent
{
    Font font = fonts.getDefault().deriveFont(64f);

    public void render(Graphics2D g)
    {
        text("as standalone box").fontSize(32).anchor(500, 400).render(g);

        var box = vertical(text("default"),
                text("different fontSize").fontSize(42),
                text("custom Font", font), text("custom color").color("orange"),
                text("custom content").content("updated content"));
        box.anchor(200, 100);
        box.render(g);
    }

    public static void main(String[] args)
    {
        new TextBoxDemo().show();
    }
}
