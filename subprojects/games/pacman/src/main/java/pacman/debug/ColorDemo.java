package pacman.debug;

import pacman.scenes.BaseScene;
import pi.Game;
import pi.Text;

public class ColorDemo extends BaseScene
{
    public ColorDemo()
    {
        add(new Text("Blinky").setColor("teal"));
    }

    public static void main(String[] args)
    {
        Game.instantMode(false);
        Game.start(new ColorDemo(), 1000, 1000);
    }
}
