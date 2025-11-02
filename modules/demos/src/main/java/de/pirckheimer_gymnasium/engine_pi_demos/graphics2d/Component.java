package de.pirckheimer_gymnasium.engine_pi_demos.graphics2d;

import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JComponent;
import javax.swing.JFrame;

/**
 * Übernimmt den Cast von Graphics zu Graphics2D und bietet wie die
 * Actor-Klassen eine render-Methode an. Außerdem eine show-Methode und eine
 * Fenster zum zeichnen.
 */
abstract class Component extends JComponent
{
    abstract void render(Graphics2D g);

    @Override
    public void paintComponent(Graphics graphics)
    {
        if (graphics instanceof Graphics2D)
        {
            Graphics2D graphics2D = (Graphics2D) graphics;
            graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            render(graphics2D);
        }
    }

    public void show()
    {
        JFrame frame = new JFrame("Graphics2D Demo");
        Container cp = frame.getContentPane();
        cp.add(this);
        frame.setSize(800, 600);
        frame.setVisible(true);
    }
}
