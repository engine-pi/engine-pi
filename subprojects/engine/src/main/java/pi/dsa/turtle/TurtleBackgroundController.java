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
package pi.dsa.turtle;

import java.awt.Color;

// Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/modules/demos/src/main/java/de/pirckheimer_gymnasium/demos/classes/dsa/turtle/TurtleBackgroundControllerDemo.java

/**
 * Steuert die Malfläche, also den <b>Hintergrund</b>, auf dem die Schildkröte
 * malt.
 *
 * <p>
 * Die Klasse kann die Hintergrundfarbe setzen und die Malspuren löschen.
 * </p>
 *
 * @see TurtleController#background
 *
 * @author Josef Friedrich
 *
 * @since 0.40.0
 */
public class TurtleBackgroundController
{
    /**
     * @since 0.40.0
     */
    private final TurtleScene scene;

    /**
     * @since 0.40.0
     */
    public TurtleBackgroundController(TurtleScene scene)
    {
        this.scene = scene;
    }

    /**
     * @since 0.40.0
     */
    public TurtleBackgroundController clear()
    {
        scene.clearBackground();
        return this;
    }

    /**
     * @since 0.40.0
     */
    public TurtleBackgroundController color(Color color)
    {
        scene.backgroundColor(color);
        return this;
    }

    /**
     * @since 0.40.0
     */
    public TurtleBackgroundController color(String color)
    {
        scene.backgroundColor(color);
        return this;
    }
}
