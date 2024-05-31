package rocks.friedrich.engine_omega.demos.physics.single_aspects;

import java.awt.event.KeyEvent;

import rocks.friedrich.engine_omega.Game;
import rocks.friedrich.engine_omega.Scene;
import rocks.friedrich.engine_omega.actor.Rectangle;
import rocks.friedrich.engine_omega.event.KeyListener;

public class ApplyRotationImpulseDemo extends Scene implements KeyListener
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
