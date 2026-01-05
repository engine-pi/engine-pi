/*
 * Engine Pi ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2024 Josef Friedrich and contributors.
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
package pi.debug;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;

import pi.Camera;
import pi.Controller;
import pi.Game;
import pi.Scene;
import pi.Vector;
import pi.annotations.Internal;
import pi.config.CoordinatesystemConfiguration;

/**
 * Zeichnet das <b>Koordinatensystem</b>.
 *
 * <p>
 * Das Koordinatensystem wird in der Einheit Meter gezeichnet. Das
 * Graphics2D-Objekt erwartet jedoch Pixel. Außerdem ist der Ursprung des
 * Graphics2D-Objekt links oben, der Ursprung des Engine-Pi-Koordinatensystem
 * jedoch in der Mitte. Das Koordinatengitter wird etwas größer gezeichnet, als
 * tatsächlich zu sehen ist, d. h. es werden zusätzlichen vertikale und
 * horizontale Linien gezeichnet, die unter Umständen außerhalb des sichtbaren
 * Bereichs liegen.
 * </p>
 */
public final class CoordinateSystemDrawer
{
    private static final int GRID_SIZE_IN_PIXELS = 160;

    private static final int GRID_SIZE_METER_LIMIT = 100000;

    private static final int DEBUG_TEXT_SIZE = 12;

    private static final Font FONT = new Font(Font.MONOSPACED, Font.PLAIN,
            DEBUG_TEXT_SIZE);

    private static final Color COLOR = new Color(255, 255, 255, 150);

    /**
     * Verschiebung der Koordinatensystembeschriftungen in Pixel.
     */
    private static final int LABEL_SHIFT = 5;

    /**
     * Das {@link Graphics2D}-Objekt, in das gezeichnet werden soll.
     */
    private final Graphics2D g;

    AffineTransform pre;

    /**
     * Die Breite des Spielfelds in Pixel.
     */
    private final int width;

    /**
     * Die Höhe des Spielfelds in Pixel.
     */
    private final int height;

    /**
     * Die Kameraposition, also der Mittelpunkt des sichtbaren Spielfelds.
     */
    private final Vector center;

    /**
     * Wie viele Pixel ein Meter misst.
     */
    private final double pixelPerMeter;

    /**
     * Wie viele Meter ein Gitterkästchen groß sein soll.
     */
    private final int gridSizeInMeters;

    /**
     * Wie viele Pixel ein Gitterkästchen groß sein soll. Ist beispielsweise ein
     * Meter 32 Pixel lang und ein Gitterkästchen 5 Meter, so ist eine
     * Gitterkästchen 160x160 Pixel groß. Der Wert dieses Attributs beträgt dann
     * 160.
     */
    private final double gridSizeInPixels;

    /**
     * Der maximale Wert von Höhe oder Breite in Pixel.
     */
    private final int maxWindowSideInPixels;

    /**
     * Der kleinste x-Wert, der durch eine vertikale Linie dargestellt werden
     * soll.
     */
    private int startX;

    /**
     * Der größte x-Wert, der durch eine vertikale Linie dargestellt werden
     * soll.
     */
    private int stopX;

    /**
     * Der kleinste y-Wert, der durch eine horizontale Linie dargestellt werden
     * soll.
     */
    private int startY;

    /**
     * Der größte y-Wert, der durch eine horizontale Linie dargestellt werden
     * soll.
     */
    private int stopY;

    private CoordinatesystemConfiguration config = Controller.config.coordinatesystem;

    /**
     * Zeichnet das <b>Koordinatensystem</b>.
     *
     * @param g Das {@link Graphics2D}-Objekt, in das gezeichnet werden soll.
     * @param scene Die Szene, über die das Koordinatensystem gezeichnet werden
     *     soll.
     * @param width Die Breite des Spielfelds in Pixel.
     * @param height Die Höhe des Spielfelds in Pixel.
     */
    public CoordinateSystemDrawer(Graphics2D g, Scene scene, int width,
            int height)
    {
        this.g = g;
        this.width = width;
        this.height = height;

        pre = g.getTransform();
        Camera camera = scene.camera();
        center = camera.focus();
        double rotation = -camera.rotation();
        g.setClip(0, 0, width, height);
        // Ohne diesen Methodenaufruf würde das Koordinatensystemgitter im
        // linken oberen Bildschirmviertel gezeichnet werden.
        // Damit der Mittelpunkt des Engine-Pi-Koordinatensystems mit dem
        // Mittelpunkt des {@link Graphics2D}-Objekts zusammenfällt.
        g.translate(width / 2, height / 2);
        pixelPerMeter = camera.meter();
        g.rotate(Math.toRadians(rotation), 0, 0);
        g.translate(-center.x() * pixelPerMeter, center.y() * pixelPerMeter);
        if (config.linesNMeter() > 0)
        {
            gridSizeInMeters = config.linesNMeter();
        }
        else
        {
            gridSizeInMeters = (int) Math
                    .round(GRID_SIZE_IN_PIXELS / pixelPerMeter);
        }
        gridSizeInPixels = gridSizeInMeters * pixelPerMeter;
        maxWindowSideInPixels = Math.max(width, height);
    }

    /**
     * Gibt zurück, wie viele Meter der sichtbare Ausschnitt des Spielfelds
     * breit ist.
     *
     * @return Wie viele Meter der sichtbare Ausschnitt des Spielfelds breit
     *     ist.
     */
    private double getWidthInMeter()
    {
        return width / pixelPerMeter;
    }

    private double getWidthInMeter(double factor)
    {
        return getWidthInMeter() * factor;
    }

    /**
     * Gibt zurück, wie viele Meter die Hälfte des sichtbaren Ausschnitts des
     * Spielfelds breit ist.
     *
     * @return Wie viele Meter die Hälfte des sichtbaren Ausschnitts des
     *     Spielfelds breit ist.
     */
    private double getHalfWidthInMeter()
    {
        return getWidthInMeter() / 2;
    }

    /**
     * Wie viele Meter der sichtbare Ausschnitt des Spielfelds hoch ist.
     *
     * @return Wie viele Meter der sichtbare Ausschnitt des Spielfelds hoch ist.
     */
    private double getHeightInMeter()
    {
        return height / pixelPerMeter;
    }

    private double getHeightInMeter(double factor)
    {
        return getHeightInMeter() * factor;
    }

    /**
     * Gibt zurück, wie viele Meter die Hälfte des sichtbaren Ausschnitts des
     * Spielfelds hoch ist.
     *
     * @return Wie viele Meter die Hälfte des sichtbaren Ausschnitts des
     *     Spielfelds hoch ist.
     */
    private double getHalfHeightInMeter()
    {
        return getHeightInMeter() / 2;
    }

    /**
     * @param value Der x- oder y-Wert
     */
    private int getLineThickness(int value)
    {
        return value == 0 ? 3 : 1;
    }

    /**
     * Zeichnet die horizontalen Linien.
     */
    private void drawHorizontalLines()
    {
        for (int y = startY; y <= stopY; y += gridSizeInMeters)
        {
            g.fillRect((int) ((startX - 1) * pixelPerMeter),
                    (int) (y * pixelPerMeter - 1),
                    (int) (maxWindowSideInPixels + 3 * gridSizeInPixels),
                    getLineThickness(y));
        }
    }

    /**
     * Zeichnet die vertikalen Linien.
     */
    private void drawVerticalLines()
    {
        for (int x = startX; x <= stopX; x += gridSizeInMeters)
        {
            g.fillRect((int) (x * pixelPerMeter) - 1,
                    (int) ((startY - 1) * pixelPerMeter), getLineThickness(x),
                    (int) (maxWindowSideInPixels + 3 * gridSizeInPixels));
        }
    }

    /**
     * Berechnet den x- oder y-Wert der ersten Koordinatenlinie, die
     * eingezeichnet werden soll.
     *
     * @param cameraPosition Der x- oder y-Wert der Kameraposition.
     *
     * @return Der x- oder y-Wert der ersten Koordinatenlinie, die eingezeichnet
     *     werden soll.
     */
    private int calculateStartLinePosition(double cameraPosition)
    {
        int start = (int) (cameraPosition
                - maxWindowSideInPixels / 2.0 / pixelPerMeter);
        start -= (start % gridSizeInMeters) + gridSizeInMeters;
        return start;
    }

    /**
     * Berechnet den x- oder y-Wert der letzten Koordinatenlinie, die
     * eingezeichnet werden soll.
     *
     * @param start Der x- oder y-Wert der ersten Koordinatenlinie.
     *
     * @return Der x- oder y-Wert der letzten Koordinatenlinie, die
     *     eingezeichnet werden soll.
     */
    private int calculateStopLinePosition(int start)
    {
        return (int) (start + maxWindowSideInPixels / pixelPerMeter
                + gridSizeInMeters * 2);
    }

    /**
     * Zeichne Koordinatenbeschriftungen bei jeder Überschneidung der
     * Gitterlinien ein.
     */
    private void drawCoordinateLabels()
    {
        for (int x = startX; x <= stopX; x += gridSizeInMeters)
        {
            for (int y = startY; y <= stopY; y += gridSizeInMeters)
            {
                g.drawString(x + "|" + -y, (int) (x * pixelPerMeter + 5),
                        (int) (y * pixelPerMeter - 5));
            }
        }
    }

    /**
     * @param x Der x-Wert in Meter.
     */
    private void drawOneLineVerticalCoordinateLabels(double x)
    {
        for (int y = startY; y <= stopY; y += gridSizeInMeters)
        {
            g.drawString(-y + "", (int) (x * pixelPerMeter + LABEL_SHIFT),
                    (int) (y * pixelPerMeter - LABEL_SHIFT));
        }
    }

    /**
     * Zeichnet die Beschriftungen für die vertikalen Linien - also für die
     * y-Achsen - ein.
     */
    private void drawVerticalCoordinateLabels()
    {
        // y-Werte am linken Rand
        drawOneLineVerticalCoordinateLabels(center.x() - getHalfWidthInMeter());
        // y-Werte an der y-Achse
        drawOneLineVerticalCoordinateLabels(0);
        // y-Werte am rechten Rand
        drawOneLineVerticalCoordinateLabels(
                center.x() + getHalfWidthInMeter() - getWidthInMeter(0.04));
    }

    /**
     * @param y Der y-Wert in Meter.
     */
    private void drawOneLineHorizontalCoordinateLabels(double y)
    {
        for (int x = startX; x <= stopX; x += gridSizeInMeters)
        {
            g.drawString(x + "", (int) (x * pixelPerMeter + LABEL_SHIFT),
                    (int) (-1 * y * pixelPerMeter - LABEL_SHIFT));
        }
    }

    /**
     * Zeichnet die Beschriftungen für die horizontalen Linien - also für die
     * x-Achsen - ein.
     */
    private void drawHorizontalCoordinateLabels()
    {
        // x-Werte am unteren Rand
        drawOneLineHorizontalCoordinateLabels(
                center.y() - getHalfHeightInMeter() + getHeightInMeter(0.02));
        // x-Werte an der x-Achse
        drawOneLineHorizontalCoordinateLabels(0);
        // x-Werte am oberen Rand
        drawOneLineHorizontalCoordinateLabels((center.y()
                + (getHalfHeightInMeter() - getHeightInMeter(0.04))));
    }

    /**
     * Zeichnet das <b>Koordinatensystem</b>.
     *
     * @hidden
     */
    @Internal
    public void draw()
    {
        if (gridSizeInMeters > 0 && gridSizeInMeters < GRID_SIZE_METER_LIMIT)
        {
            startX = calculateStartLinePosition(center.x());
            startY = calculateStartLinePosition(-1 * center.y());
            stopX = calculateStopLinePosition(startX);
            stopY = calculateStopLinePosition(startY);
            // Setzen der Schriftart.
            g.setFont(FONT);
            // Setzen der Gitterfarbe.
            g.setColor(COLOR);
            drawVerticalLines();
            drawHorizontalLines();
            drawHorizontalCoordinateLabels();
            drawVerticalCoordinateLabels();
            if (config.labelsOnIntersections())
            {
                drawCoordinateLabels();
            }
        }
        g.setTransform(pre);
    }

    public static void main(String[] args)
    {
        Game.debug();
        Scene scene = Game.start();
        scene.addKeyStrokeListener((event) -> {
            CoordinatesystemConfiguration config = Controller.config.coordinatesystem;
            switch (event.getKeyCode())
            {
            case KeyEvent.VK_1 -> config.linesNMeter(1);
            case KeyEvent.VK_2 -> config.linesNMeter(2);
            case KeyEvent.VK_3 -> config.linesNMeter(3);
            case KeyEvent.VK_4 -> config.linesNMeter(4);
            case KeyEvent.VK_5 -> config.linesNMeter(5);
            }
        });
    }
}
