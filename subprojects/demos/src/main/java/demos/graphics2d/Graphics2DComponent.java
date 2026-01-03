package demos.graphics2d;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;
import javax.swing.JFrame;

import pi.util.Graphics2DUtil;

/**
 * Übernimmt den Cast von {@link Graphics} zu {@link Graphics2D} und bietet wie
 * die Actor-Klassen eine {@link #render(Graphics2D)}-Methode an. Außerdem eine
 * {@link #show()}-Methode und eine Fenster zum zeichnen.
 */
public abstract class Graphics2DComponent extends JComponent
{
    /**
     * Wird mehrmals ausgeführt.
     */
    public abstract void render(Graphics2D g);

    /**
     * Wird mehrmals ausgeführt.
     */
    @Override
    public void paintComponent(Graphics graphics)
    {
        if (graphics instanceof Graphics2D)
        {
            Graphics2D graphics2D = (Graphics2D) graphics;
            Graphics2DUtil.antiAliasing(graphics2D, true);
            render(graphics2D);
        }
    }

    public void show()
    {
        JFrame frame = new JFrame("Graphics2D Demo");
        frame.getContentPane().add(this);
        frame.setSize(800, 600);
        frame.setVisible(true);
    }
}
