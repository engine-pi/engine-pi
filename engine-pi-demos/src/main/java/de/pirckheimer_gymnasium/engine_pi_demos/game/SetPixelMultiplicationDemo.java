package de.pirckheimer_gymnasium.engine_pi_demos.game;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Scene;
import de.pirckheimer_gymnasium.engine_pi.Vector;
import de.pirckheimer_gymnasium.engine_pi.actor.Image;
import de.pirckheimer_gymnasium.engine_pi.actor.Text;
import de.pirckheimer_gymnasium.engine_pi.event.FrameUpdateListener;
import de.pirckheimer_gymnasium.engine_pi.util.TextUtil;

public class SetPixelMultiplicationDemo extends Scene
{
    static
    {
        Game.setPixelMultiplication(4);
    }

    public SetPixelMultiplicationDemo()
    {
        Text test = addText("Text", 0, 0);
        addImage("dude/box/obj_box001.png", 1, 1).setPosition(3, 0);
        getCamera().setFocus(test);
        setBackgroundColor("white");
    }

    public static void main(String[] args)
    {
        Game.start(new SetPixelMultiplicationDemo());
    }
}
