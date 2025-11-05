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
 * Die Malfläche kann zum Beispiel für Turtle-Grafiken verwendet werden oder zur
 * Simulation eines Malprogramms.
 * </p>
 *
 * <p>
 * Alle Malaktionen werden in einem {@link BufferedImage} vollzogen. Diese
 * Klasse übernimmt die Übersetzung des Engine-Pi-Koordinatensystems in das
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
    /**
     * Die Szene, in der die Malfläche als Hintergrund verwendet wird.
     */
    private Scene scene;

    /**
     * Ein Bild, in das gemalt werden kann und das als Hintergrundbild angezeigt
     * wird. Es kann zum Beispiel für Turtle-Grafiken verwendet werden oder zur
     * Simulation eines Malprogramms.
     */
    private BufferedImage image;

    /**
     * Das {@link Graphics2D}-Objekt, das aus dem {@link #image}-Objekt erzeugt
     * wurde.
     */
    private Graphics2D g;

    /**
     * @param scene Die Szene, in der die Malfläche als Hintergrund verwendet
     *     wird.
     */
    public PaintingSurface(Scene scene)
    {
        this.scene = scene;
    }

    public BufferedImage getImage()
    {
        return image;
    }

    /**
     * Die Bilddatei kann nicht im Konstruktur initialisiert werden, da zu
     * diesem Zeitpunkt das Fenster noch keine Abmessungen hat.
     */
    public void initialize()
    {
        if (image == null)
        {
            var size = Game.getWindowSize();
            image = new BufferedImage((int) size.getX(), (int) size.getY(),
                    BufferedImage.TYPE_INT_ARGB);
            g = image.createGraphics();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
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

    /**
     * Füllt die gesamte Zeichenfläche mit der angegebenen Farbe.
     *
     * @param color Die Farbe, mit der die Zeichenfläche gefüllt werden soll.
     */
    public void fill(Color color)
    {
        initialize();
        g.setColor(color);
        g.fillRect(0, 0, image.getWidth(), image.getHeight());
    }

    /**
     * Füllt die Zeichenfläche mit der angegebenen Farbe.
     *
     * @param color Der Name der Farbe als String, die zum Füllen verwendet
     *     werden soll. Die Farbe muss in der Farbzuordnung vorhanden sein.
     */
    public void fill(String color)
    {
        fill(colors.get(color));
    }

    /**
     * Löscht die gesamte Zeichenfläche, indem sie weiß gefüllt wird.
     *
     * Diese Methode setzt die Zeichenfläche auf ihren Ausgangszustand zurück,
     * indem sie mit der Farbe Weiß gefüllt wird.
     */
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

    public void drawLine(Vector point1, Vector point2)
    {
        initialize();
        g.setColor(colors.get("grey"));
        Vector px1 = translateCoordinates(point1);
        Vector px2 = translateCoordinates(point2);
        g.drawLine(px1.getX(1), px1.getY(1), px2.getX(1), px2.getY(1));
    }

}
