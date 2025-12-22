package de.pirckheimer_gymnasium.demos.classes.graphics.boxes;

import static pi.Resources.colors;
import static pi.Resources.fonts;
import static pi.graphics.boxes.Boxes.framedText;
import static pi.graphics.boxes.Boxes.vertical;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import de.pirckheimer_gymnasium.demos.graphics2d.Graphics2DComponent;

// Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/modules/engine/src/main/java/pi/graphics/boxes/FramedTextBox.java

public class FramedTextBoxDemo extends Graphics2DComponent
{
    Font font = fonts.getDefault().deriveFont(32f);

    Color backgroundColor = colors.get("red");

    Color borderColor = colors.get("blue");

    public void render(Graphics2D g)
    {
        var allFeatures = framedText("All features");
        allFeatures.background.color(backgroundColor);
        allFeatures.border.color(borderColor);
        allFeatures.border.thickness(21);
        allFeatures.margin.allSides(10);
        allFeatures.padding.allSides(40);

        var onlyBackground = framedText("only background");
        onlyBackground.background.color(backgroundColor);

        var onlyBorderColor = framedText("only borderColor");
        onlyBorderColor.border.color(borderColor);

        var onlyBorderSize = framedText("only borderSize");
        onlyBorderSize.border.thickness(13);
        vertical(allFeatures, onlyBackground, onlyBorderColor, onlyBorderSize)
                .anchor(0, 0).render(g).debug();

        framedText("default").x(300).render(g).debug();
    }

    public static void main(String[] args)
    {
        new FramedTextBoxDemo().show();
    }
}
