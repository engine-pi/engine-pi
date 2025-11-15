package de.pirckheimer_gymnasium.engine_pi.dsa.turtle;

import de.pirckheimer_gymnasium.engine_pi.Game;

// Demo: file:///home/jf/repos/school/monorepo/inf/java/engine-pi/modules/demos/src/main/java/de/pirckheimer_gymnasium/demos/classes/dsa/turtle/TurtleDemo.java

public class TurtleAlgorithmLauncher
{
    TurtleScene scene;

    Thread thread;

    public TurtleAlgorithmLauncher()
    {
        scene = new TurtleScene();
        Game.startSafe(scene);
    }

    public void launch(TurtleAlgorithmNg algorithm)
    {
        if (thread != null)
        {
            thread.interrupt();
        }
        algorithm.turtle = scene;
        algorithm.initalState.apply(scene);
        thread = new Thread(algorithm);
        thread.start();
    }
}
