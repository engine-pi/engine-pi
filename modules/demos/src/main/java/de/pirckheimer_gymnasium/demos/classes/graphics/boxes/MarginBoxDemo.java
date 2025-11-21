package de.pirckheimer_gymnasium.demos.classes.graphics.boxes;

import static de.pirckheimer_gymnasium.engine_pi.Resources.fonts;
import static de.pirckheimer_gymnasium.engine_pi.graphics.boxes.Box.border;
import static de.pirckheimer_gymnasium.engine_pi.graphics.boxes.Box.margin;
import static de.pirckheimer_gymnasium.engine_pi.graphics.boxes.Box.textLine;
import static de.pirckheimer_gymnasium.engine_pi.graphics.boxes.Box.vertical;

import java.awt.Font;
import java.awt.Graphics2D;

import de.pirckheimer_gymnasium.demos.graphics2d.Graphics2DComponent;

// Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/modules/engine/src/main/java/de/pirckheimer_gymnasium/engine_pi/graphics/boxes/MarginBox.java

public class MarginBoxDemo extends Graphics2DComponent
{
    Font font = fonts.getDefault().deriveFont(32f);

    public void render(Graphics2D g)
    {
        var defaultSettings = border(margin(border(textLine("default", font))));

        var manuel = margin(textLine("default", font)).margin(50);

        vertical(defaultSettings, manuel).anchor(50, 50).render(g);
    }

    public static void main(String[] args)
    {
        new MarginBoxDemo().show();
    }
}
