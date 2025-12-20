package de.pirckheimer_gymnasium.pacman.debug;

import pi.Game;
import de.pirckheimer_gymnasium.pacman.scenes.BaseScene;

public class ColorDemo extends BaseScene
{
    public ColorDemo()
    {
        addText("Blinky").setColor("teal");
    }

    public static void main(String[] args)
    {
        Game.start(new ColorDemo(), 1000, 1000);
    }
}
