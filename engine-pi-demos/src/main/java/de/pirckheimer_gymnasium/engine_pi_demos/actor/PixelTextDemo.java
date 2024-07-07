package de.pirckheimer_gymnasium.engine_pi_demos.actor;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Scene;
import de.pirckheimer_gymnasium.engine_pi.actor.PixelText;

public class PixelTextDemo extends Scene
{
    public PixelTextDemo()
    {
        setBackgroundColor("white");
        add(new PixelText("pixel-text", "Hello, World!"));
    }

    public static void main(String[] args)
    {
        Game.start(1020, 520, new PixelTextDemo());
        Game.setTitle("Text Example");
    }
}
