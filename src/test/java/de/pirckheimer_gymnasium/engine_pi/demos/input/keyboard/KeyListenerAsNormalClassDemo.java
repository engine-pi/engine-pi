package de.pirckheimer_gymnasium.engine_pi.demos.input.keyboard;

import java.awt.Color;
import java.awt.event.KeyEvent;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Scene;
import de.pirckheimer_gymnasium.engine_pi.actor.Circle;
import de.pirckheimer_gymnasium.engine_pi.event.KeyListener;

public class KeyListenerAsNormalClassDemo extends Scene
{
    Circle circle;

    KeyListener keyListener;

    class MyKeylistener implements KeyListener
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
                circle.removeKeyListener(keyListener);
                break;

            case KeyEvent.VK_L:
                circle.addKeyListener(keyListener);
                break;
            }
        }
    }

    public KeyListenerAsNormalClassDemo()
    {
        circle = new Circle(2);
        circle.setColor(Color.RED);
        keyListener = new MyKeylistener();
        circle.addKeyListener(keyListener);
        add(circle);
    }

    public static void main(String[] args)
    {
        Game.start(600, 400, new KeyListenerAsNormalClassDemo());
    }
}