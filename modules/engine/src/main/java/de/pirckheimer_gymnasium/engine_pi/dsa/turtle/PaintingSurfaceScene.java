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
package de.pirckheimer_gymnasium.engine_pi.dsa.turtle;

import java.awt.Graphics2D;

import de.pirckheimer_gymnasium.engine_pi.Scene;
import de.pirckheimer_gymnasium.engine_pi.annotations.Internal;
import de.pirckheimer_gymnasium.engine_pi.graphics.PaintingSurface;

/**
 * Eine <b>Szenen</b> mit einer <b>Malfläche</b> als Hintergrund.
 *
 * @author Josef Friedrich
 *
 * @since 0.40.0
 */
public class PaintingSurfaceScene extends Scene
{
    /**
     * Ein <b>Malfläche</b>, in die gemalt werden kann und dir als
     * Hintergrundbild angezeigt wird. Es kann zum Beispiel für Turtle-Grafiken
     * verwendet werden oder zur Simulation eines Malprogramms.
     *
     * @since 0.40.0
     */
    protected PaintingSurface paintingSurface;

    /**
     * @since 0.40.0
     */
    public PaintingSurface getPaintingSurface()
    {
        if (paintingSurface == null)
        {
            paintingSurface = new PaintingSurface(this);
        }
        return paintingSurface;
    }

    /**
     * @since 0.40.0
     */
    @Internal
    public final void render(Graphics2D g, int width, int height)
    {
        // Die Malfläche muss zuerst eingezeichnet werden, damit sie als
        // Hintergrund erscheint.
        if (paintingSurface != null)
        {
            g.drawImage(paintingSurface.getImage(), 0, 0, null);
        }
        super.render(g, width, height);
    }
}
