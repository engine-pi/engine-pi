package rocks.friedrich.engine_omega.examples.input.keyboard;

import java.awt.Color;
import java.awt.event.KeyEvent;

import rocks.friedrich.engine_omega.Game;
import rocks.friedrich.engine_omega.Scene;
import rocks.friedrich.engine_omega.actor.Circle;
import rocks.friedrich.engine_omega.event.KeyListener;

public class KeyListenerAsAnonymousClassExample extends Scene
{
    public KeyListenerAsAnonymousClassExample()
    {
        Circle circle = new Circle(2);
        circle.setColor(Color.RED);
        circle.addKeyListener(new KeyListener()
        {
            @Override
            public void onKeyDown(KeyEvent e)
            {
                switch (e.getKeyCode())
                {
                case KeyEvent.VK_UP:
                    circle.moveBy(0, 1);
                    break;

                case KeyEvent.VK_RIGHT:
                    circle.moveBy(1, 0);
                    break;

                case KeyEvent.VK_DOWN:
                    circle.moveBy(0, -1);
                    break;

                case KeyEvent.VK_LEFT:
                    circle.moveBy(-1, 0);
                    break;
                }
            }
        });
        add(circle);
    }

    public static void main(String[] args)
    {
        Game.start(600, 400, new KeyListenerAsAnonymousClassExample());
    }
}