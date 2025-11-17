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

import java.lang.reflect.InvocationTargetException;

import de.pirckheimer_gymnasium.engine_pi.Game;

// Go to file: file:///home/jf/repos/school/monorepo/inf/java/engine-pi/modules/demos/src/main/java/de/pirckheimer_gymnasium/demos/classes/dsa/turtle/TurtleDemo.java

/**
 * @author Josef Friedrich
 *
 * @since 0.40.0
 */
public class TurtleLauncher
{
    private static TurtleScene scene;

    private static Thread thread;

    /**
     * Startet ein neues Fenster.
     *
     * @return Ein Szene, in der sich eine Schildkröte befindet.
     *
     * @since 0.40.0
     */
    public static TurtleScene scene()
    {
        if (scene == null)
        {
            scene = new TurtleScene();
            Game.startSafe(scene);
        }
        return scene;
    }

    /**
     * @since 0.40.0
     */
    public static void launch(TurtleGraphics graphics)
    {

        if (thread != null)
        {
            thread.interrupt();
        }
        graphics.turtle = new TurtleController(scene());
        graphics.initalState.apply(scene());
        thread = new Thread(graphics);
        thread.start();
    }

    /**
     * @since 0.40.0
     */
    public static void launch(Class<? extends TurtleGraphics> graphics)
    {
        TurtleGraphics g;
        try
        {
            g = graphics.getDeclaredConstructor().newInstance();
        }
        catch (InstantiationException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException e)
        {
            throw new RuntimeException(e);
        }
        launch(g);
    }
}
