package de.pirckheimer_gymnasium.demos.graphics2d;

import java.awt.Color;
import java.awt.Graphics2D;

import de.pirckheimer_gymnasium.engine_pi.graphics.Align;
import de.pirckheimer_gymnasium.engine_pi.graphics.TextRenderer;
import de.pirckheimer_gymnasium.engine_pi.graphics.Valign;

public class TextRendererDemo extends Component
{

    private final String lorem = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. "
            + "Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. "
            + "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.";

    @Override
    public void render(Graphics2D g)
    {
        TextRenderer.render(g, "render(g, lorem, 100, 100, true): " + lorem,
                100, 100, true);

        TextRenderer.renderWithLinebreaks(g,
                "renderWithLinebreaks(g, lorem, 100, 200, 300): " + lorem, 100,
                200, 300);

        TextRenderer.renderWithOutline(g, "renderWithOutline: " + lorem, 100,
                100, 300.0, 600.0, Color.GREEN, 2, Align.CENTER, Valign.MIDDLE,
                true);
    }

    public static void main(String[] args)
    {
        new TextRendererDemo().show();
    }
}
