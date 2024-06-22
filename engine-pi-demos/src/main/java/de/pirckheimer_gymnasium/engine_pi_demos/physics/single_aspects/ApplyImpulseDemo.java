package de.pirckheimer_gymnasium.engine_pi_demos.physics.single_aspects;

import java.awt.event.KeyEvent;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Scene;
import de.pirckheimer_gymnasium.engine_pi.actor.Rectangle;
import de.pirckheimer_gymnasium.engine_pi.event.KeyStrokeListener;

/**
 * Demonstriert die Methode
 * {@link de.pirckheimer_gymnasium.engine_pi.actor.Actor#applyImpulse(double,double)}
 */
public class ApplyImpulseDemo extends Scene implements KeyStrokeListener
{
    private final Rectangle rectangle;

    public ApplyImpulseDemo()
    {
        rectangle = new Rectangle(3, 3);
        rectangle.makeDynamic();
        setGravity(0, -1);
        add(rectangle);
    }

    @Override
    public void onKeyDown(KeyEvent e)
    {
        switch (e.getKeyCode())
        {
        case KeyEvent.VK_UP -> rectangle.applyImpulse(0, 100);
        case KeyEvent.VK_DOWN -> rectangle.applyImpulse(0, -100);
        case KeyEvent.VK_RIGHT -> rectangle.applyImpulse(100, 0);
        case KeyEvent.VK_LEFT -> rectangle.applyImpulse(-100, 0);
        }
    }

    public static void main(String[] args)
    {
        Game.start(new ApplyImpulseDemo());
    }
}
