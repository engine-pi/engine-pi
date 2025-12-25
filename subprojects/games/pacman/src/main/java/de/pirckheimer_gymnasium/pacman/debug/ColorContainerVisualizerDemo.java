package de.pirckheimer_gymnasium.pacman.debug;

import pi.Game;
import pi.Scene;
import pi.debug.ColorContainerVisualizer;
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
