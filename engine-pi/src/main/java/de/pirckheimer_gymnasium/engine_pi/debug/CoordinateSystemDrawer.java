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
package de.pirckheimer_gymnasium.engine_pi.debug;

import de.pirckheimer_gymnasium.engine_pi.Camera;
import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Scene;
import de.pirckheimer_gymnasium.engine_pi.Vector;
import de.pirckheimer_gymnasium.engine_pi.annotations.Internal;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;

/**
 * Zeichnet das <b>Koordinatensystem</b>.
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
     * Verschiebung der Koordinatensystembeschriftungen.
     */
    private static final int LABEL_SHIFT = 5;

    /**
     * Das {@link Graphics2D}-Objekt, in das gezeichnet werden soll.
     */
    private final Graphics2D g;

    AffineTransform pre;

    /**
     * Die Kameraposition.
     */
    private final Vector position;

    /**
     * Wie viele Pixel ein Meter misst.
     */
    private final double pixelPerMeter;

    /**
     * Wie viele Meter ein Gitterkästchen groß sein soll.
     */
    private final int gridSizeInMeters;

    /**
     * Wie viele Pixel ein Gitterkästchen groß sein soll.
     */
    private final double gridSizeInPixels;

    private final double gridSizeFactor;

    /**
     * Der maximale Wert von Höhe oder Breite in Pixeln.
     */
    private final int windowSizeInPixels;

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

    /**
     * Zeichnet das <b>Koordinatensystem</b>.
     *
     * @param g      Das {@link Graphics2D}-Objekt, in das gezeichnet werden
     *               soll.
     * @param scene  Die Szene, über die das Koordinatensystem gezeichnet werden
     *               soll.
     * @param width  Die Breite des Spielfelds in Pixel.
     * @param height Die Höhe des Spielfelds in Pixel.
     */
    public CoordinateSystemDrawer(Graphics2D g, Scene scene, int width,
            int height)
    {
        this.g = g;
        pre = g.getTransform();
        Camera camera = scene.getCamera();
        position = camera.getPosition();
        double rotation = -camera.getRotation();
        g.setClip(0, 0, width, height);
        // Ohne diesen Methodenaufruf würde das Koordinatensystemgitter im
        // linken oberen Bildschirmviertel gezeichnet werden.
        g.translate(width / 2, height / 2);
        pixelPerMeter = camera.getMeter();
        g.rotate(Math.toRadians(rotation), 0, 0);
        g.translate(-position.getX() * pixelPerMeter,
                position.getY() * pixelPerMeter);
        if (DebugConfiguration.coordinateSystemLinesEveryNMeter > 0)
        {
            gridSizeInMeters = DebugConfiguration.coordinateSystemLinesEveryNMeter;
        }
        else
        {
            gridSizeInMeters = (int) Math
                    .round(GRID_SIZE_IN_PIXELS / pixelPerMeter);
        }
        gridSizeInPixels = gridSizeInMeters * pixelPerMeter;
        gridSizeFactor = gridSizeInPixels / gridSizeInMeters;
        windowSizeInPixels = Math.max(width, height);
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
            g.fillRect((int) ((startX - 1) * gridSizeFactor),
                    (int) (y * gridSizeFactor - 1),
                    (int) (windowSizeInPixels + 3 * gridSizeInPixels),
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
            g.fillRect((int) (x * gridSizeFactor) - 1,
                    (int) ((startY - 1) * gridSizeFactor), getLineThickness(x),
                    (int) (windowSizeInPixels + 3 * gridSizeInPixels));
        }
    }

    /**
     * Berechnet den x- oder y-Wert der ersten Koordinatenlinie, die
     * eingezeichnet werden soll.
     *
     * @param cameraPosition Der x- oder y-Wert der Kameraposition.
     *
     * @return Der x- oder y-Wert der ersten Koordinatenlinie, die eingezeichnet
     *         werden soll.
     */
    private int calculateStartLinePosition(double cameraPosition)
    {
        int start = (int) (cameraPosition
                - windowSizeInPixels / 2.0 / pixelPerMeter);
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
     *         eingezeichnet werden soll.
     */
    private int calculateStopLinePosition(int start)
    {
        return (int) (start + windowSizeInPixels / pixelPerMeter
                + gridSizeInMeters * 2);
    }

    private void drawCoordinateLabels()
    {
        for (int x = startX; x <= stopX; x += gridSizeInMeters)
        {
            for (int y = startY; y <= stopY; y += gridSizeInMeters)
            {
                g.drawString(x + "|" + -y, (int) (x * gridSizeFactor + 5),
                        (int) (y * gridSizeFactor - 5));
            }
        }
    }

    private void drawOneLineVerticalCoordinateLabels(int x)
    {
        for (int y = startY; y <= stopY; y += gridSizeInMeters)
        {
            g.drawString(-y + "", (int) (x * gridSizeFactor + LABEL_SHIFT),
                    (int) (y * gridSizeFactor - LABEL_SHIFT));
        }
    }

    private void drawVerticalCoordinateLabels()
    {
        drawOneLineVerticalCoordinateLabels(startX);
        drawOneLineVerticalCoordinateLabels(0);
        drawOneLineVerticalCoordinateLabels(stopX);
    }

    private void drawHorizontalCoordinateLabels()
    {
        for (int x = startX; x <= stopX; x += gridSizeInMeters)
        {
            g.drawString(-x + "", (int) (0 * gridSizeFactor + LABEL_SHIFT),
                    (int) (x * gridSizeFactor - LABEL_SHIFT));
        }
    }

    /**
     * Zeichnet das <b>Koordinatensystem</b>.
     */
    @Internal
    public void draw()
    {
        if (gridSizeInMeters > 0 && gridSizeInMeters < GRID_SIZE_METER_LIMIT)
        {
            startX = calculateStartLinePosition(position.getX());
            startY = calculateStartLinePosition(-1 * position.getY());
            stopX = calculateStopLinePosition(startX);
            stopY = calculateStopLinePosition(startY);
            // Setzen der Schriftart.
            g.setFont(FONT);
            // Setzen der Gitterfarbe.
            g.setColor(COLOR);
            drawVerticalLines();
            drawHorizontalLines();
            // drawCoordinateLabels(startX, stopX, startY, stopY);
            drawVerticalCoordinateLabels();
        }
        g.setTransform(pre);
    }

    public static void main(String[] args)
    {
        Game.debug();
        Scene scene = Game.start();
        scene.addKeyStrokeListener((event) -> {
            switch (event.getKeyCode())
            {
            case KeyEvent.VK_1 ->
                DebugConfiguration.coordinateSystemLinesEveryNMeter = 1;
            case KeyEvent.VK_2 ->
                DebugConfiguration.coordinateSystemLinesEveryNMeter = 2;
            case KeyEvent.VK_3 ->
                DebugConfiguration.coordinateSystemLinesEveryNMeter = 3;
            case KeyEvent.VK_4 ->
                DebugConfiguration.coordinateSystemLinesEveryNMeter = 4;
            case KeyEvent.VK_5 ->
                DebugConfiguration.coordinateSystemLinesEveryNMeter = 5;
            }
        });
    }
}
