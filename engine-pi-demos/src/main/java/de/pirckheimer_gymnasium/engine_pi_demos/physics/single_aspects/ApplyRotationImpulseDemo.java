package de.pirckheimer_gymnasium.engine_pi_demos.physics.single_aspects;

import java.awt.event.KeyEvent;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Scene;
import de.pirckheimer_gymnasium.engine_pi.actor.Rectangle;
import de.pirckheimer_gymnasium.engine_pi.event.KeyStrokeListener;

public class ApplyRotationImpulseDemo extends Scene implements KeyStrokeListener
{
    private final Rectangle rectangle;

    public ApplyRotationImpulseDemo()
    {
        rectangle = new Rectangle(3, 3);
        add(rectangle);
    }

    @Override
    public void onKeyDown(KeyEvent e)
    {
        switch (e.getKeyCode())
        {
        case KeyEvent.VK_RIGHT -> rectangle.applyRotationImpulse(-100);
        case KeyEvent.VK_LEFT -> rectangle.applyRotationImpulse(100);
        }
    }

    public static void main(String[] args)
    {
        Game.start(new ApplyRotationImpulseDemo());
    }
}
