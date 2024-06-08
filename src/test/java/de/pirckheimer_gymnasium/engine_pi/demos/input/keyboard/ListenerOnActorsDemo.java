package de.pirckheimer_gymnasium.engine_pi.demos.input.keyboard;

import java.awt.Color;
import java.awt.event.KeyEvent;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Scene;
import de.pirckheimer_gymnasium.engine_pi.actor.Circle;
import de.pirckheimer_gymnasium.engine_pi.actor.Rectangle;
import de.pirckheimer_gymnasium.engine_pi.event.KeyListener;

public class ListenerOnActorsDemo extends Scene
{
    public ListenerOnActorsDemo()
    {
        Rectangle rectangle = new Rectangle(2, 2);
        rectangle.setColor(Color.BLUE);
        rectangle.setPosition(-3, 0);
        rectangle.addKeyListener(new KeyListener()
        {
            @Override
            public void onKeyDown(KeyEvent e)
            {
                switch (e.getKeyCode())
                {
                case KeyEvent.VK_UP:
                    rectangle.moveBy(0, 1);
                    break;

                case KeyEvent.VK_RIGHT:
                    rectangle.moveBy(1, 0);
                    break;

                case KeyEvent.VK_DOWN:
                    rectangle.moveBy(0, -1);
                    break;

                case KeyEvent.VK_LEFT:
                    rectangle.moveBy(-1, 0);
                    break;
                }
            }
        });
        add(rectangle);
        // Ein zweiter Actor
        Circle circle = new Circle(2);
        circle.setPosition(3, 0);
        circle.setColor(Color.RED);
        // Als Lambda-Ausdruck
        circle.addKeyListener(e -> {
            switch (e.getKeyCode())
            {
            case KeyEvent.VK_W:
                circle.moveBy(0, 1);
                break;

            case KeyEvent.VK_D:
                circle.moveBy(1, 0);
                break;

            case KeyEvent.VK_S:
                circle.moveBy(0, -1);
                break;

            case KeyEvent.VK_A:
                circle.moveBy(-1, 0);
                break;
            }
        });
        add(circle);
    }

    public static void main(String[] args)
    {
        Game.start(600, 400, new ListenerOnActorsDemo());
    }
}
