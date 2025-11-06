package de.pirckheimer_gymnasium.demos.actor.turtle;

import java.awt.event.KeyEvent;

import de.pirckheimer_gymnasium.engine_pi.actor.Turtle;
import de.pirckheimer_gymnasium.engine_pi.event.KeyStrokeListener;

public class TurtleDemo implements KeyStrokeListener
{
    Turtle turtle;

    public TurtleDemo()
    {
        turtle = new Turtle();
    }

    @Override
    public void onKeyDown(KeyEvent event)
    {
        switch (event.getKeyCode())
        {
        case KeyEvent.VK_1:
            turtle.setSpeed(1);
            break;

        default:
            break;
        }
    }
}
