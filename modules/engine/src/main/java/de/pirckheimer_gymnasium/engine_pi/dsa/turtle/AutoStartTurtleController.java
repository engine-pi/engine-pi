package de.pirckheimer_gymnasium.engine_pi.dsa.turtle;

// Go to file: file:///home/jf/repos/school/monorepo/inf/java/engine-pi/modules/demos/src/main/java/de/pirckheimer_gymnasium/demos/classes/dsa/turtle/TurtleDemo.java

/**
 * Eine Schildkröteszene, die automatisch startet, d. h. ein Fenster öffnet.
 *
 * @since 0.40.0
 */
public class AutoStartTurtleController extends TurtleController
{
    /**
     * @since 0.40.0
     */
    public AutoStartTurtleController()
    {
        super(TurtleLauncher.scene());
    }
}
