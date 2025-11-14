package de.pirckheimer_gymnasium.demos.classes.graphics.boxes;

import static de.pirckheimer_gymnasium.engine_pi.Resources.fonts;
import static de.pirckheimer_gymnasium.engine_pi.graphics.boxes.Box.background;
import static de.pirckheimer_gymnasium.engine_pi.graphics.boxes.Box.border;
import static de.pirckheimer_gymnasium.engine_pi.graphics.boxes.Box.text;
import static de.pirckheimer_gymnasium.engine_pi.graphics.boxes.Box.vertical;

import java.awt.Font;
import java.awt.Graphics2D;

import de.pirckheimer_gymnasium.demos.graphics2d.Graphics2DComponent;
import de.pirckheimer_gymnasium.engine_pi.graphics.boxes.BorderBox;

public class BackgroundBoxDemo extends Graphics2DComponent
{
    Font font = fonts.getDefault().deriveFont(32f);

    public void render(Graphics2D g)
    {
        BorderBox defaultSettings = border(background(text("default", font)));
        vertical(defaultSettings).anchor(0, 0).render(g);
    }

    public static void main(String[] args)
    {
        new BackgroundBoxDemo().show();
    }
}
