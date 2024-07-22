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

import java.awt.*;
import java.awt.geom.AffineTransform;

import de.pirckheimer_gymnasium.engine_pi.Camera;
import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Resources;
import de.pirckheimer_gymnasium.engine_pi.Scene;
import de.pirckheimer_gymnasium.engine_pi.Vector;
import de.pirckheimer_gymnasium.engine_pi.annotations.Internal;

/**
 * Zeichnet das <b>Koordinatensystem</b>.
 */
public final class CoordinateSystemDrawer
{
    private static final int GRID_SIZE_IN_PIXELS = 160;

    private static final int GRID_SIZE_METER_LIMIT = 100000;

    private static final int DEBUG_TEXT_SIZE = 12;

    /**
     * Zeichnet das <b>Koordinatensystem</b>.
     *
     * @param g      Das {@link Graphics2D}-Objekt, in das gezeichnet werden
     *               soll.
     * @param scene  Die Szene, über die das Koordinatensystem gezeichnet werden
     *               soll.
     * @param width  Die Breite in Pixel.
     * @param height Die Höhe in Pixel.
     */
    @Internal
    public static void draw(Graphics2D g, Scene scene, int width, int height)
    {
        AffineTransform pre = g.getTransform();
        Camera camera = scene.getCamera();
        Vector position = camera.getPosition();
        double rotation = -camera.getRotation();
        g.setClip(0, 0, width, height);
        g.translate(width / 2, height / 2);
        double pixelPerMeter = camera.getMeter();
        g.rotate(Math.toRadians(rotation), 0, 0);
        g.translate(-position.getX() * pixelPerMeter,
                position.getY() * pixelPerMeter);
        // Wie viele Meter ein Kästchen im Gitter groß sein soll.
        int gridSizeInMeters = (int) Math
                .round(GRID_SIZE_IN_PIXELS / pixelPerMeter);
        // Wie viele Pixel ein Kästchen im Gitter groß sein soll.
        double gridSizeInPixels = gridSizeInMeters * pixelPerMeter;
        double gridSizeFactor = gridSizeInPixels / gridSizeInMeters;
        if (gridSizeInMeters > 0 && gridSizeInMeters < GRID_SIZE_METER_LIMIT)
        {
            int windowSizeInPixels = Math.max(width, height);
            int startX = (int) (position.getX()
                    - windowSizeInPixels / 2.0 / pixelPerMeter);
            int startY = (int) ((-1 * position.getY())
                    - windowSizeInPixels / 2.0 / pixelPerMeter);
            startX -= (startX % gridSizeInMeters) + gridSizeInMeters;
            startY -= (startY % gridSizeInMeters) + gridSizeInMeters;
            startX -= gridSizeInMeters;
            int stopX = (int) (startX + windowSizeInPixels / pixelPerMeter
                    + gridSizeInMeters * 2);
            int stopY = (int) (startY + windowSizeInPixels / pixelPerMeter
                    + gridSizeInMeters * 2);
            g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, DEBUG_TEXT_SIZE));
            // Setzen der Gitterfarbe
            g.setColor(new Color(255, 255, 255, 150));
            // Zeichnen der vertikalen Linien
            for (int x = startX; x <= stopX; x += gridSizeInMeters)
            {
                g.fillRect((int) (x * gridSizeFactor) - 1,
                        (int) ((startY - 1) * gridSizeFactor), x == 0 ? 3 : 1,
                        (int) (windowSizeInPixels + 3 * gridSizeInPixels));
            }
            // Zeichnen der horizontalen Linien
            for (int y = startY; y <= stopY; y += gridSizeInMeters)
            {
                g.fillRect((int) ((startX - 1) * gridSizeFactor),
                        (int) (y * gridSizeFactor - 1),
                        (int) (windowSizeInPixels + 3 * gridSizeInPixels),
                        y == 0 ? 3 : 1);
            }
            for (int y = startY; y <= stopY; y += gridSizeInMeters)
            {
                g.drawString(-y + "", (int) (0 * gridSizeFactor + 5),
                        (int) (y * gridSizeFactor - 5));
            }
            for (int x = startX; x <= stopX; x += gridSizeInMeters)
            {
                for (int y = startY; y <= stopY; y += gridSizeInMeters)
                {
                    g.drawString(x + "|" + -y, (int) (x * gridSizeFactor + 5),
                            (int) (y * gridSizeFactor - 5));
                }
            }
        }
        g.setTransform(pre);
    }

    public static void main(String[] args)
    {
        Game.debug();
        Game.start();
    }
}
