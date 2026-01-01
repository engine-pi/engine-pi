package pacman.debug;

import pacman.ColorManagement;
import pi.Game;
import pi.Scene;
import pi.debug.ColorContainerVisualizer;

public class ColorContainerVisualizerDemo extends Scene
{
    public ColorContainerVisualizerDemo()
    {
        new ColorContainerVisualizer(this);
    }

    public static void main(String[] args)
    {
        Game.instantMode(false);
        ColorManagement.setColors();
        Game.start(new ColorContainerVisualizerDemo(), 1000, 1000);
    }
}
