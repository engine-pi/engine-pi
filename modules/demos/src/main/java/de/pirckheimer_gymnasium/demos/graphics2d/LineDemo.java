package de.pirckheimer_gymnasium.demos.graphics2d;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import static java.awt.BasicStroke.CAP_ROUND;
import static java.awt.BasicStroke.CAP_BUTT;
import static java.awt.BasicStroke.JOIN_ROUND;
import static java.awt.BasicStroke.JOIN_MITER;

/**
 * Demonstierte den Umgang mit <b>Text</b> mit und ohne Unterlängen.
 */
public class LineDemo extends Graphics2DComponent
{
    @Override
    public void render(Graphics2D g)
    {
        // Standard-Linie mit der Standardstärke
        g.drawLine(10, 10, 300, 50);

        // Linie mit der Stärke von 5 Pixeln
        g.setStroke(new BasicStroke(5));
        g.drawLine(10, 70, 300, 110);

        // Gestrichelte Linie
        g.setStroke(
                new BasicStroke(3, CAP_BUTT, JOIN_MITER, 10, new float[]
                { 10, 5 }, 0));
        g.drawLine(10, 130, 300, 170);

        // Gepunktete Linie
        g.setStroke(
                new BasicStroke(2, CAP_ROUND, JOIN_ROUND, 1, new float[]
                { 2, 10 }, 0));
        g.drawLine(10, 190, 300, 230);

        // Dicke Linie mit runden Ecken
        g.setStroke(new BasicStroke(10, CAP_ROUND, JOIN_ROUND));
        g.drawLine(10, 250, 300, 290);
    }

    public static void main(String[] args)
    {
        new LineDemo().show();
    }
}
