package de.pirckheimer_gymnasium.engine_pi.demos.physics.single_aspects;

import java.awt.event.KeyEvent;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Scene;
import de.pirckheimer_gymnasium.engine_pi.actor.Rectangle;
import de.pirckheimer_gymnasium.engine_pi.event.KeyStrokeListener;

public class FrictionDemo extends Scene implements KeyStrokeListener
{
    private final Rectangle rectangle;

    public FrictionDemo()
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
        case KeyEvent.VK_UP -> rectangle.applyForce(0, 5000);
        case KeyEvent.VK_DOWN -> rectangle.applyForce(0, -5000);
        case KeyEvent.VK_RIGHT -> rectangle.applyForce(5000, 0);
        case KeyEvent.VK_LEFT -> rectangle.applyForce(-5000, 0);
        }
    }

    public static void main(String[] args)
    {
        Game.start(new FrictionDemo());
    }
}
