package de.pirckheimer_gymnasium.engine_pi_demos.input.keyboard;

import java.awt.Color;
import java.awt.event.KeyEvent;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Scene;
import de.pirckheimer_gymnasium.engine_pi.actor.Rectangle;
import de.pirckheimer_gymnasium.engine_pi.event.KeyStrokeListener;

public class StaticKeyStrokeListenerDemo extends Scene
{
    Rectangle rectangle;

    public StaticKeyStrokeListenerDemo()
    {
        rectangle = new Rectangle(2, 2);
        rectangle.setColor(Color.BLUE);
        add(rectangle);
    }

    public void moveLeft()
    {
        rectangle.moveBy(-1, 0);
    }

    public void moveRight()
    {
        rectangle.moveBy(1, 0);
    }

    public static void main(String[] args)
    {
        StaticKeyStrokeListenerDemo scene = new StaticKeyStrokeListenerDemo();
        Game.start(scene, 600, 400);
        Game.addKeyStrokeListener(new KeyStrokeListener()
        {
            public void onKeyDown(KeyEvent e)
            {
                switch (e.getKeyCode())
                {
                case KeyEvent.VK_LEFT:
                    scene.moveLeft();
                    break;

                case KeyEvent.VK_RIGHT:
                    scene.moveRight();
                    break;
                }
            }
        });
    }
}
