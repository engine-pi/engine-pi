package de.pirckheimer_gymnasium.engine_pi.demos.input.keyboard;

import java.awt.event.KeyEvent;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Scene;
import de.pirckheimer_gymnasium.engine_pi.actor.Circle;
import de.pirckheimer_gymnasium.engine_pi.event.KeyStrokeListener;

public class KeyStrokeListenerAsNormalClassDemo extends Scene
{
    Circle circle;

    KeyStrokeListener keyStrokeListener;

    class MyKeyStrokeListener implements KeyStrokeListener
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

            case KeyEvent.VK_X:
                circle.removeKeyStrokeListener(keyStrokeListener);
                break;

            case KeyEvent.VK_L:
                circle.addKeyStrokeListener(keyStrokeListener);
                break;
            }
        }
    }

    public KeyStrokeListenerAsNormalClassDemo()
    {
        circle = new Circle(2);
        circle.setColor("red");
        keyStrokeListener = new MyKeyStrokeListener();
        circle.addKeyStrokeListener(keyStrokeListener);
        add(circle);
    }

    public static void main(String[] args)
    {
        Game.start(600, 400, new KeyStrokeListenerAsNormalClassDemo());
    }
}
