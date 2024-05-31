package rocks.friedrich.engine_omega.examples.actor;

import java.awt.event.KeyEvent;

import rocks.friedrich.engine_omega.Game;
import rocks.friedrich.engine_omega.Scene;
import rocks.friedrich.engine_omega.actor.Circle;
import rocks.friedrich.engine_omega.actor.Group;
import rocks.friedrich.engine_omega.actor.Rectangle;
import rocks.friedrich.engine_omega.event.KeyListener;

public class GroupDemo extends Scene implements KeyListener
{
    private final Group group;

    public GroupDemo()
    {
        Circle circle = new Circle(3);
        circle.setPosition(3, 3);
        Rectangle rectangle = new Rectangle(5, 1);
        group = new Group(this);
        group.add(circle, rectangle);
    }

    @Override
    public void onKeyDown(KeyEvent e)
    {
        switch (e.getKeyCode())
        {
        case KeyEvent.VK_UP -> group.moveBy(0, 1);
        case KeyEvent.VK_DOWN -> group.moveBy(0, -1);
        case KeyEvent.VK_RIGHT -> group.moveBy(1, 0);
        case KeyEvent.VK_LEFT -> group.moveBy(-1, 0);
        }
    }

    public static void main(String[] args)
    {
        Game.start(new GroupDemo());
    }
}
