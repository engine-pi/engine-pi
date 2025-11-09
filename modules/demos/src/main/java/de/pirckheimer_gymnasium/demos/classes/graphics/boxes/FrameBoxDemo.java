package de.pirckheimer_gymnasium.demos.classes.graphics.boxes;

import static de.pirckheimer_gymnasium.engine_pi.Resources.colors;
import static de.pirckheimer_gymnasium.engine_pi.Resources.fonts;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import de.pirckheimer_gymnasium.demos.graphics2d.Graphics2DComponent;
import de.pirckheimer_gymnasium.engine_pi.graphics.boxes.TextBox;
import de.pirckheimer_gymnasium.engine_pi.graphics.boxes.VerticalBox;
import de.pirckheimer_gymnasium.engine_pi.graphics.boxes.FrameBox;

/**
 *
 */
public class FrameBoxDemo extends Graphics2DComponent
{
    Font font = fonts.getDefault().deriveFont(32f);

    Color backgroundColor = colors.get("red");

    Color borderColor = colors.get("blue");

    public void render(Graphics2D g)
    {
        FrameBox allFeatures = new FrameBox(new TextBox("All features", font))
                .backgroundColor(backgroundColor).borderColor(borderColor)
                .borderSize(21).margin(10).padding(40);

        FrameBox onlyBackground = new FrameBox(
                new TextBox("only background", font))
                .backgroundColor(backgroundColor);

        FrameBox onlyBorderColor = new FrameBox(
                new TextBox("only borderColor", font)).borderColor(borderColor);

        FrameBox onlyBorderSize = new FrameBox(
                new TextBox("only borderSize", font)).borderSize(13);

        new VerticalBox(allFeatures, onlyBackground, onlyBorderColor,
                onlyBorderSize).anchor(0, 0).render(g);
    }

    public static void main(String[] args)
    {
        new FrameBoxDemo().show();
    }
}
