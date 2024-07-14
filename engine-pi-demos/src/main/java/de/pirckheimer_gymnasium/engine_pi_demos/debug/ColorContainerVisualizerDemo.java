package de.pirckheimer_gymnasium.engine_pi_demos.debug;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Resources;
import de.pirckheimer_gymnasium.engine_pi.Scene;
import de.pirckheimer_gymnasium.engine_pi.debug.ColorContainerVisualizer;

public class ColorContainerVisualizerDemo extends Scene
{
    static
    {
        Resources.COLORS.clear();
        Resources.COLORS.add("custom", 1, 200, 3);
    }

    public ColorContainerVisualizerDemo()
    {
        new ColorContainerVisualizer(this);
    }

    public static void main(String[] args)
    {
        Game.start(new ColorContainerVisualizerDemo());
    }
}
