package demos.tutorials.scenes;

import static pi.Resources.colors;

import java.awt.Color;
import java.awt.Graphics2D;

import pi.Circle;
import pi.Scene;

/**
 * Demonstriert die Methode {@link Scene#render(Graphics2D, int, int)}.
 *
 * <p>
 * Das Demo zeigt zwei grüne Rechtecke, die die Kreisfigur überlagern. Das linke
 * Rechteck ist undurchsichtig und das rechte Rechteck ist transparent. Die
 * Transparenz wird über eine Farbe mit reduziertem Alphakanal erzeugt.
 * </p>
 */
public class OverlayDemo extends Scene
{

    private int margin = 50;

    public OverlayDemo()
    {
        Circle circle = new Circle(15);
        circle.setCenter(0, 0);
    }

    @Override
    public void renderOverlay(Graphics2D g, int width, int height)
    {
        Color old = g.getColor();
        int rectangleWidth = (width - 3 * margin) / 2;
        int rectangleHeight = height - 2 * margin;

        // Undurchsichtiges Rechteck links
        g.setColor(colors.get("green"));
        g.fillRect(margin, margin, rectangleWidth, rectangleHeight);

        // Durchsichtiges Rechteck rechts
        g.setColor(colors.get("green", 100));
        g.fillRect(2 * margin + rectangleWidth, margin, rectangleWidth,
                rectangleHeight);
        g.setColor(old);
    }

    public static void main(String[] args)
    {
        new OverlayDemo();
    }

}
