package de.pirckheimer_gymnasium.engine_pi.dsa.turtle;

import java.lang.reflect.InvocationTargetException;

import de.pirckheimer_gymnasium.engine_pi.Game;

// Go to file: file:///home/jf/repos/school/monorepo/inf/java/engine-pi/modules/demos/src/main/java/de/pirckheimer_gymnasium/demos/classes/dsa/turtle/TurtleDemo.java

/**
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
