package de.pirckheimer_gymnasium.pacman.debug;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Scene;
import de.pirckheimer_gymnasium.engine_pi.debug.ColorContainerVisualizer;
import de.pirckheimer_gymnasium.pacman.ColorManagement;

public class ColorContainerVisualizerDemo extends Scene
{
    public ColorContainerVisualizerDemo()
    {
        new ColorContainerVisualizer(this);
    }

    public static void main(String[] args)
    {
        ColorManagement.setColors();
        Game.start(new ColorContainerVisualizerDemo(), 1000, 1000);
    }
}
