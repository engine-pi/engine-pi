package de.pirckheimer_gymnasium.demos.graphics2d;

import static de.pirckheimer_gymnasium.engine_pi.Resources.images;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 * Demonstierte wie mit der {@link Graphics2D}-API ein Bild gezeichnet werden
 * kann.
 */
public class DrawImage extends Component
{

    @Override
    public void render(Graphics2D g)
    {
        BufferedImage image = images.get("froggy/Frog.png");
        System.out.println(String.format("Breite: %d Höhe: %d",
                image.getWidth(), image.getHeight()));

        g.drawImage(image, 50, 50, null);

        // Skalieren
        g.drawImage(image, 300, 100, 100, 100, null);

        // 45 Grad im Uhrzeigersinn drehen:
        AffineTransform transform = new AffineTransform();
        transform.translate(100, 100);
        transform.rotate(Math.toRadians(45));
        g.drawImage(image, transform, null);
    }

    public static void main(String[] args)
    {
        new DrawImage().show();
    }
}
