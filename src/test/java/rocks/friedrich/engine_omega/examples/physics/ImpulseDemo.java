package rocks.friedrich.engine_omega.examples.physics;

import java.awt.Color;
import java.awt.event.KeyEvent;

import rocks.friedrich.engine_omega.Game;
import rocks.friedrich.engine_omega.Scene;
import rocks.friedrich.engine_omega.Vector;
import rocks.friedrich.engine_omega.actor.BodyType;
import rocks.friedrich.engine_omega.actor.Rectangle;
import rocks.friedrich.engine_omega.event.KeyListener;

public class ImpulseDemo extends Scene implements KeyListener
{
    /**
     * Eine einfache Demo
     */
    private final Rectangle rectangle;

    public ImpulseDemo()
    {
        rectangle = new Rectangle(3, 3);
        rectangle.setColor(Color.BLUE);
        rectangle.setBodyType(BodyType.DYNAMIC);
        setGravity(new Vector(0, -1));
        add(rectangle);
    }

    @Override
    public void onKeyDown(KeyEvent e)
    {
        switch (e.getKeyCode())
        {
        case KeyEvent.VK_UP -> rectangle.applyImpulse(0, 300);
        case KeyEvent.VK_DOWN -> rectangle.applyImpulse(0, -100);
        case KeyEvent.VK_RIGHT -> rectangle.applyImpulse(100, 0);
        case KeyEvent.VK_LEFT -> rectangle.applyImpulse(-100, 0);
        }
    }

    public static void main(String[] args)
    {
        Game.setDebug(true);
        Game.start(1020, 520, new ImpulseDemo());
    }
}
