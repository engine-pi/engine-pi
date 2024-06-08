package de.pirckheimer_gymnasium.engine_pi.demos.actor;

import java.awt.event.KeyEvent;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Scene;
import de.pirckheimer_gymnasium.engine_pi.actor.Circle;
import de.pirckheimer_gymnasium.engine_pi.actor.Group;
import de.pirckheimer_gymnasium.engine_pi.actor.Rectangle;
import de.pirckheimer_gymnasium.engine_pi.event.KeyListener;

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
