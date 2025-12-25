package de.pirckheimer_gymnasium.demos.graphics2d;

import java.awt.Font;
import java.awt.Graphics2D;

/**
 * Demonstierte den Umgang mit verschiedenen Schriftstilen (normal, fett oder
 * kursiv) von <b>Schriftarten</b>.
 */
public class FontStyleDemo extends Graphics2DComponent
{

    @Override
    public void render(Graphics2D g)
    {
        Font font = getFont().deriveFont(28f);

        g.setFont(font);
        g.drawString("Regular", 70, 100);

        g.setFont(font.deriveFont(Font.BOLD));
        g.drawString("Bold", 70, 200);

        g.setFont(font.deriveFont(Font.ITALIC));
        g.drawString("Italic", 70, 300);
    }

    public static void main(String[] args)
    {
        new FontStyleDemo().show();
    }
}
