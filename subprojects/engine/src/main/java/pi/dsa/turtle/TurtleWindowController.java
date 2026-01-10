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

import pi.Controller;
import pi.graphics.geom.Direction;

// Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/modules/demos/src/main/java/de/pirckheimer_gymnasium/demos/classes/dsa/turtle/TurtleWindowControllerDemo.java

/**
 * Steuert das <b>Fenster</b>, in das die Schildkröte malt.
 *
 * @see TurtleController#window
 *
 * @author Josef Friedrich
 *
 * @since 0.41.0
 */
public class TurtleWindowController
{
    /**
     * @since 0.41.0
     */
    public TurtleWindowController size(int width, int height)
    {
        Controller.windowSize(width, height);
        return this;
    }

    /**
     * @since 0.41.0
     */
    public TurtleWindowController position(int x, int y)
    {
        Controller.windowPosition(x, y);
        return this;
    }

    /**
     * @since 0.41.0
     */
    public TurtleWindowController position(Direction direction)
    {
        Controller.windowPosition(direction);
        return this;
    }
}
