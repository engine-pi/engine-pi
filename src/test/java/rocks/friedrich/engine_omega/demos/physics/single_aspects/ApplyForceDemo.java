package rocks.friedrich.engine_omega.demos.physics.single_aspects;

import java.awt.event.KeyEvent;

import rocks.friedrich.engine_omega.Game;
import rocks.friedrich.engine_omega.Scene;
import rocks.friedrich.engine_omega.actor.BodyType;
import rocks.friedrich.engine_omega.actor.Rectangle;
import rocks.friedrich.engine_omega.event.KeyListener;

public class ApplyForceDemo extends Scene implements KeyListener
{
    private final Rectangle rectangle;

    public ApplyForceDemo()
    {
        rectangle = new Rectangle(3, 3);
        rectangle.setBodyType(BodyType.DYNAMIC);
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
        Game.start(new ApplyForceDemo());
    }
}
