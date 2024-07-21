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

import static de.pirckheimer_gymnasium.engine_pi.Resources.COLORS;

import java.awt.Color;
import java.awt.Graphics2D;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Resources;
import de.pirckheimer_gymnasium.engine_pi.Scene;
import de.pirckheimer_gymnasium.engine_pi.Vector;
import de.pirckheimer_gymnasium.engine_pi.annotations.Internal;
import de.pirckheimer_gymnasium.engine_pi.util.Graphics2DUtil;

/**
 * Zeichnet einige <b>Informationsboxen</b> in das linke obere Eck.
 */
public final class InfoBoxDrawer
{
    private static void drawGravityVector(Graphics2D g, int x, int y,
            Vector gravity, Color color)
    {
        Graphics2DUtil.drawArrowLine(g, x, y, x + (int) gravity.getX() * 2,
                y + (int) gravity.getY() * -2, 5, 5, color);
    }

    /**
     * Zeichnet einige <b>Informationsboxen</b> in das linke obere Eck.
     *
     * @param g Das {@link Graphics2D}-Objekt, in das gezeichnet werden soll.
     */
    @Internal
    public static void draw(Graphics2D g, Scene scene, double frameDuration,
            int actorsCount)
    {
        // Einzelbilder pro Sekunden
        Graphics2DUtil.drawTextBox(g, "FPS: "
                + (frameDuration == 0 ? "∞" : Math.round(1 / frameDuration)),
                10, COLORS.getSafe("blue"));
        // Anzahl an Figuren
        Graphics2DUtil.drawTextBox(g, "Actors: " + actorsCount, 50,
                COLORS.getSafe("green"));
        // Schwerkraft
        Vector gravity = scene.getGravity();
        Color gravityColor = Resources.colorScheme.getBluePurple();
        if (!gravity.isNull())
        {
            Graphics2DUtil.drawTextBox(g, String.format("G(x,y): %.2f,%.2f",
                    gravity.getX(), gravity.getY()), 90, gravityColor);
            drawGravityVector(g, 40, 145, gravity, gravityColor);
        }
    }

    public static void main(String[] args)
    {
        Game.setDebug(true);
        Game.start();
    }
}
