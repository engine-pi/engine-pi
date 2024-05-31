package rocks.friedrich.engine_omega.demos.physics.single_aspects;

import java.awt.event.KeyEvent;

import rocks.friedrich.engine_omega.Game;
import rocks.friedrich.engine_omega.Scene;
import rocks.friedrich.engine_omega.actor.BodyType;
import rocks.friedrich.engine_omega.actor.Rectangle;
import rocks.friedrich.engine_omega.event.KeyListener;

public class GravityDemo extends Scene implements KeyListener
{
    private final Rectangle rectangle;

    public GravityDemo()
    {
        setGravity(0, -9.81);
        createBorder(-5, 4, false);
        createBorder(-5, -5, false);
        createBorder(-5, -5, true);
        createBorder(4, -5, true);
        rectangle = new Rectangle(1, 1);
        rectangle.setBodyType(BodyType.DYNAMIC);
        add(rectangle);
    }

    private Rectangle createBorder(double x, double y, boolean vertical)
    {
        Rectangle rectangle = !vertical ? new Rectangle(10, 1)
                : new Rectangle(1, 10);
        rectangle.setPosition(x, y);
        rectangle.setBodyType(BodyType.STATIC);
        add(rectangle);
        return rectangle;
    }

    @Override
    public void onKeyDown(KeyEvent e)
    {
        switch (e.getKeyCode())
        {
        case KeyEvent.VK_UP -> setGravity(0, 9.81);
        case KeyEvent.VK_DOWN -> setGravity(0, -9.81);
        case KeyEvent.VK_RIGHT -> setGravity(9.81, 0);
        case KeyEvent.VK_LEFT -> setGravity(-9.81, 0);
        }
    }

    public static void main(String[] args)
    {
        Game.setDebug(true);
        Game.start(new GravityDemo());
    }
}
