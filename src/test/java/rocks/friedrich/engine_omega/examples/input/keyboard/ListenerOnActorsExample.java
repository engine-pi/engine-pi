package rocks.friedrich.engine_omega.examples.input.keyboard;

import java.awt.Color;
import java.awt.event.KeyEvent;

import rocks.friedrich.engine_omega.Game;
import rocks.friedrich.engine_omega.Scene;
import rocks.friedrich.engine_omega.actor.Circle;
import rocks.friedrich.engine_omega.actor.Rectangle;
import rocks.friedrich.engine_omega.event.KeyListener;

public class ListenerOnActorsExample extends Scene
{
    public ListenerOnActorsExample()
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
        Game.start(600, 400, new ListenerOnActorsExample());
    }
}
