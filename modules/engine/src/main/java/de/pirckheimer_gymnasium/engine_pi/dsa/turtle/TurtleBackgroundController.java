package de.pirckheimer_gymnasium.engine_pi.dsa.turtle;

import java.awt.Color;

// Go to file: file:///home/jf/repos/school/monorepo/inf/java/engine-pi/modules/demos/src/main/java/de/pirckheimer_gymnasium/demos/classes/dsa/turtle/TurtleBackgroundControllerDemo.java

/**
 * @author Josef Friedrich
 *
 * @since 0.40.0
 */
public class TurtleBackgroundController
{

    /**
     * @since 0.40.0
     */
    private TurtleScene scene;

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
        scene.setBackgroundColor(color);
        return this;
    }

    /**
     * @since 0.40.0
     */
    public TurtleBackgroundController color(String color)
    {
        scene.setBackgroundColor(color);
        return this;
    }
}
