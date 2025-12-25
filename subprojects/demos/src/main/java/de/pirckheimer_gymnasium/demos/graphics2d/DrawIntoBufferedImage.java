package de.pirckheimer_gymnasium.demos.graphics2d;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Demonstierte wie mit der {@link Graphics2D}-API in ein Bild gezeichnet werden
 * kann. Diese Technik ist nützlich für Turtle-Grafiken.
 */
public class DrawIntoBufferedImage extends Graphics2DComponent
{

    @Override
    public void render(Graphics2D g)
    {
        BufferedImage image = new BufferedImage(200, 200,
                BufferedImage.TYPE_INT_ARGB);

        Graphics2D imageG = image.createGraphics();
        imageG.setColor(Color.RED);
        // Rechteck, das größer als das Bild ist
        imageG.fillRect(0, 0, image.getWidth() + 1, image.getHeight() + 1);

        imageG.setColor(Color.BLUE);
        // Die Anfangs- und Endpunkte der Linie ragen über das Bild hinaus
        imageG.drawLine(-100, -100, 300, 300);

        g.drawImage(image, 100, 100, null);
    }

    public static void main(String[] args)
    {
        new DrawIntoBufferedImage().show();
    }

}
