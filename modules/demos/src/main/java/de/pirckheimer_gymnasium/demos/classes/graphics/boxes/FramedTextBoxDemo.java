package de.pirckheimer_gymnasium.demos.classes.graphics.boxes;

import static de.pirckheimer_gymnasium.engine_pi.Resources.colors;
import static de.pirckheimer_gymnasium.engine_pi.Resources.fonts;
import static de.pirckheimer_gymnasium.engine_pi.graphics.boxes.Box.framedText;
import static de.pirckheimer_gymnasium.engine_pi.graphics.boxes.Box.vertical;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import de.pirckheimer_gymnasium.demos.graphics2d.Graphics2DComponent;

public class FramedTextBoxDemo extends Graphics2DComponent
{
    Font font = fonts.getDefault().deriveFont(32f);

    Color backgroundColor = colors.get("red");

    Color borderColor = colors.get("blue");

    public void render(Graphics2D g)
    {
        var allFeatures = framedText("All features")
                .backgroundColor(backgroundColor).borderColor(borderColor)
                .borderThickness(21).margin(10).padding(40);

        var onlyBackground = framedText("only background")
                .backgroundColor(backgroundColor);

        var onlyBorderColor = framedText("only borderColor")
                .borderColor(borderColor);

        var onlyBorderSize = framedText("only borderSize").borderThickness(13);

        vertical(allFeatures, onlyBackground, onlyBorderColor, onlyBorderSize)
                .anchor(0, 0).render(g);

        framedText("default").x(300).render(g);
    }

    public static void main(String[] args)
    {
        new FramedTextBoxDemo().show();
    }
}
