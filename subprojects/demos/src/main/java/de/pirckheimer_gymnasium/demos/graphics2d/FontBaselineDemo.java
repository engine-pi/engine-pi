package de.pirckheimer_gymnasium.demos.graphics2d;

import java.awt.Font;
import java.awt.Graphics2D;

/**
 * Demonstierte den Umgang mit <b>Text</b> mit und ohne Unterlängen.
 */
public class FontBaselineDemo extends Graphics2DComponent
{

    @Override
    public void render(Graphics2D g)
    {
        Font font = getFont();
        g.setFont(font.deriveFont(28f));
        // Die Grundlinie bleibt erhalten auch wenn in einem Text keine
        // Buchstaben mit Unterlängen erscheinen.
        g.drawString("Mit Unterlängen", 70, 60);
        g.drawString("ohne", 300, 60);
    }

    public static void main(String[] args)
    {
        new FontBaselineDemo().show();
    }
}
