/*
 * Engine Pi ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2025 Josef Friedrich and contributors.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package pi.graphics;

import static pi.Controller.colors;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import pi.Controller;
import pi.Scene;
import pi.graphics.geom.Vector;
import pi.util.Graphics2DUtil;

/**
 * Eine <b>Malfläche</b>, in die gezeichnet werden kann.
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
     * Die <b>Szene</b>, in der die Malfläche als Hintergrund verwendet wird.
     */
    private Scene scene;

    /**
     * Ein <b>Bild</b>, in das gemalt werden kann und das als Hintergrundbild
     * angezeigt wird. Es kann zum Beispiel für Turtle-Grafiken verwendet werden
     * oder zur Simulation eines Malprogramms.
     */
    private BufferedImage image;

    /**
     * Das {@link Graphics2D}-Objekt, das aus dem {@link #image}-Objekt erzeugt
     * wurde.
     */
    private Graphics2D g;

    /**
     * @param scene Die <b>Szene</b>, in der die Malfläche als Hintergrund
     *     verwendet wird.
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
     * Die Bilddatei kann nicht im Konstruktur <b>initialisiert</b> werden, da
     * zu diesem Zeitpunkt das Fenster noch keine Abmessungen hat.
     */
    public void initialize()
    {
        if (image == null)
        {
            setNewImage();
        }
    }

    private void setNewImage()
    {
        var size = Controller.windowSize();
        image = new BufferedImage((int) size.x(), (int) size.y(),
                BufferedImage.TYPE_INT_ARGB);
        g = image.createGraphics();
        Graphics2DUtil.antiAliasing(g, true);
    }

    /**
     * Übersetzt die Engine-Pi-Koordinaten (Meter) in Java-Koordinaten (Pixel).
     */
    private Vector translateCoordinates(Vector point)
    {
        return scene.layer().translateWorldPointToFramePxCoordinates(point);
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
     * <b>Füllt</b> die Zeichenfläche mit der angegebenen Farbe.
     *
     * @param color Der <b>Name der Farbe</b> als Zeichenkette, die zum Füllen
     *     verwendet werden soll. Die Farbe muss in der Farbzuordnung vorhanden
     *     sein ({@link pi.resources.color.ColorContainer siehe Auflistung})
     *     oder eine Farbe in hexadezimaler Codierung (z.B. {@code #ff0000}).
     */
    public void fill(String color)
    {
        fill(colors.get(color));
    }

    /**
     * <b>Löscht</b> die gesamte Zeichenfläche und macht sie transparent.
     *
     * <p>
     * Diese Methode setzt die Zeichenfläche auf ihren Ausgangszustand zurück,
     * indem sie mit der Farbe Weiß in voller Transparenz (Alphakanalwert
     * {@code 0}) ausgefüllt wird.
     * </p>
     */
    public void clear()
    {
        setNewImage();
        // fill(colors.get("white", 0));
    }

    private void drawCenteredCircle(Vector position, int size)
    {
        Vector px = translateCoordinates(position);
        g.fillOval((int) px.x() - size / 2,
            (int) px.y() - size / 2,
            size,
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

    public void drawLine(Vector point1, Vector point2, Color color,
            double lineWidth)
    {
        initialize();
        g.setColor(color);
        g.setStroke(new BasicStroke((float) lineWidth));
        Vector px1 = translateCoordinates(point1);
        Vector px2 = translateCoordinates(point2);
        g.drawLine(px1.x(1), px1.y(1), px2.x(1), px2.y(1));
    }

    public void drawLine(Vector point1, Vector point2, Color color)
    {
        drawLine(point1, point2, color, 1);
    }

    public void drawLine(Vector point1, Vector point2)
    {
        drawLine(point1, point2, colors.get("grey"));
    }
}
