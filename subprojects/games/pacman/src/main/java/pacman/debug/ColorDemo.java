package pacman.debug;

import pacman.scenes.BaseScene;
import pi.Game;

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
