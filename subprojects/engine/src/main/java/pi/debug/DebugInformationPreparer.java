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
package pi.debug;

import pi.Vector;
import pi.loop.GameLoop;

/**
 * Bereitet die Debuginformationen auf.
 *
 * @author Josef Friedrich
 *
 * @since 0.42.0
 */
class DebugInformationPreparer
{
    private GameLoop loop;

    public DebugInformationPreparer(GameLoop loop)
    {
        this.loop = loop;
    }

    public int actorsCount()
    {
        return loop.getCurrentScene().getWorldHandler().getWorld()
                .getBodyCount();
    }

    /**
     * Bilder pro Sekunde. Frames per Second
     */
    public String fpsFormatted()
    {
        double frameDuration = loop.getFrameDuration();
        if (frameDuration == 0)
        {
            return "∞";
        }
        else
        {
            return String.valueOf(Math.round(1 / frameDuration));
        }
    }

    public Vector gravity()
    {
        return loop.getCurrentScene().getGravity();
    }

    /**
     * @return Eine formattierte Zeichenkette oder {@code null} falls keinen
     *     Schwerkraft gesetzt ist.
     */
    public String gravityFormatted()
    {
        Vector gravity = gravity();
        if (!gravity.isNull())
        {
            return String.format("G(x,y): %.2f,%.2f", gravity.getX(),
                    gravity.getY());
        }
        return null;
    }
}
