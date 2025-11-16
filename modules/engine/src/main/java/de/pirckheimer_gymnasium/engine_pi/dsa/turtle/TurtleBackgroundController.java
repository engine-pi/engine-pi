package de.pirckheimer_gymnasium.engine_pi.dsa.turtle;

import java.awt.Color;

// Go to file: file:///home/jf/repos/school/monorepo/inf/java/engine-pi/modules/demos/src/main/java/de/pirckheimer_gymnasium/demos/classes/dsa/turtle/TurtleBackgroundControllerDemo.java

public class TurtleBackgroundController
{
    private TurtleScene scene;

    public TurtleBackgroundController(TurtleScene scene)
    {
        this.scene = scene;
    }

    public TurtleBackgroundController clear()
    {
        scene.clearBackground();
        return this;
    }

    public TurtleBackgroundController color(Color color)
    {
        scene.setBackgroundColor(color);
        return this;
    }

    public TurtleBackgroundController color(String color)
    {
        scene.setBackgroundColor(color);
        return this;
    }
}
