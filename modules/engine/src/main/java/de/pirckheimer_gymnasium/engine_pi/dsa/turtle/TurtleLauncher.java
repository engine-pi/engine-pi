package de.pirckheimer_gymnasium.engine_pi.dsa.turtle;

import java.lang.reflect.InvocationTargetException;

import de.pirckheimer_gymnasium.engine_pi.Game;

// Demo: file:///home/jf/repos/school/monorepo/inf/java/engine-pi/modules/demos/src/main/java/de/pirckheimer_gymnasium/demos/classes/dsa/turtle/TurtleDemo.java

public class TurtleLauncher
{
    private static TurtleScene scene;

    private static Thread thread;

    public static TurtleScene scene()
    {
        if (scene == null)
        {
            scene = new TurtleScene();
            Game.startSafe(scene);
        }
        return scene;
    }

    public static void launch(TurtleGraphics graphics)
    {

        if (thread != null)
        {
            thread.interrupt();
        }
        graphics.turtle = scene();
        graphics.initalState.apply(scene());
        thread = new Thread(graphics);
        thread.start();
    }

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
