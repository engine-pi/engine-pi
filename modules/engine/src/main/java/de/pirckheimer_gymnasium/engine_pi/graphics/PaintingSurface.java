package de.pirckheimer_gymnasium.engine_pi.graphics;

import static de.pirckheimer_gymnasium.engine_pi.Resources.colors;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Scene;
import de.pirckheimer_gymnasium.engine_pi.Vector;

/**
 * Eine Malfläche, in die gezeichnet werden kann.
 *
 * <p>
 * Die kann zum Beispiel für Turtle-Grafiken verwendet werden oder zur
 * Simulation eines Malprogramms.
 * </p>
 *
 * <p>
 * Alle Malaktionen werden in einem {@link BufferedImage} vollzogen. Die Klassen
 * übernimmt die Übersetzung des Engine-Pi-Koordinatensystems in das
 * Java-Koordinatensystem. Dazu ist eine Referenz auf eine {@link Scene Szene}
 * nötig.
 * </p>
 *
 * @author Josef Friedrich
 *
 * @since 0.38.0
 */
public class PaintingSurface
{

    private Scene scene;

    /**
     * Ein Bild, in das gemalt werden kann und das als Hintergrundbild angezeigt
     * wird. Es kann zum Beispiel für Turtle-Grafiken verwendet werden oder zur
     * Simulation eines Malprogramms.
     */
    private BufferedImage image;

    private Graphics2D g;

    public PaintingSurface(Scene scene)
    {
        this.scene = scene;
    }

    public BufferedImage getImage()
    {
        return image;
    }

    /**
     * Die Bilddatei kann nicht im Konstruktur initialisiert werden, da zu dem
     * Zeitpunkt das Fenster noch keine Abmessungen hat.
     */
    public void initialize()
    {
        if (image == null)
        {
            var size = Game.getWindowSize();
            image = new BufferedImage((int) size.getX(), (int) size.getY(),
                    BufferedImage.TYPE_INT_ARGB);

            g = image.createGraphics();
            g.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION,
                    RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            g.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING,
                    RenderingHints.VALUE_COLOR_RENDER_QUALITY);
            g.setRenderingHint(RenderingHints.KEY_DITHERING,
                    RenderingHints.VALUE_DITHER_ENABLE);
            g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS,
                    RenderingHints.VALUE_FRACTIONALMETRICS_ON);
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.setRenderingHint(RenderingHints.KEY_RENDERING,
                    RenderingHints.VALUE_RENDER_QUALITY);
            g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,
                    RenderingHints.VALUE_STROKE_PURE);
        }
    }

    /**
     * Übersetzt die Engine-Pi-Koordinaten (Meter) in Java-Koordinaten (Pixel).
     */
    private Vector translateCoordinates(Vector point)
    {
        return scene.getMainLayer()
                .translateWorldPointToFramePxCoordinates(point);
    }

    public void fill(Color color)
    {
        initialize();
        g.setColor(color);
        g.fillRect(0, 0, image.getWidth(), image.getHeight());
    }

    public void fill(String color)
    {
        fill(colors.get(color));
    }

    public void clear()
    {
        fill("white");
    }

    private void drawCenteredCircle(Vector position, int size)
    {
        Vector px = translateCoordinates(position);
        g.fillOval((int) px.getX() - size / 2, (int) px.getY() - size / 2, size,
                size);
    }

    public void drawPoint(Vector position)
    {
        initialize();
        g.setColor(colors.get("grey"));
        drawCenteredCircle(position, 2);
    }

    public void drawCircle(Vector position, int size, Color color)
    {
        initialize();
        g.setColor(color);
        drawCenteredCircle(position, size);
    }

}
