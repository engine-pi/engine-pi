package rocks.friedrich.engine_omega.examples.physics;

import java.awt.event.KeyEvent;

import rocks.friedrich.engine_omega.Game;
import rocks.friedrich.engine_omega.Scene;
import rocks.friedrich.engine_omega.actor.Rectangle;
import rocks.friedrich.engine_omega.event.KeyListener;

public class ApplyTorqueDemo extends Scene implements KeyListener
{
    private final Rectangle rectangle;

    public ApplyTorqueDemo()
    {
        rectangle = new Rectangle(3, 3);
        add(rectangle);
    }

    @Override
    public void onKeyDown(KeyEvent e)
    {
        switch (e.getKeyCode())
        {
        case KeyEvent.VK_RIGHT -> rectangle.applyTorque(-1000);
        case KeyEvent.VK_LEFT -> rectangle.applyTorque(1000);
        }
    }

    public static void main(String[] args)
    {
        Game.start(new ApplyTorqueDemo());
    }
}
