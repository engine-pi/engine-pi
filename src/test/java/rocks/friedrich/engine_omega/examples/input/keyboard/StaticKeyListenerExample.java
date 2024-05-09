package rocks.friedrich.engine_omega.examples.input.keyboard;

import java.awt.Color;
import java.awt.event.KeyEvent;

import rocks.friedrich.engine_omega.Game;
import rocks.friedrich.engine_omega.Scene;
import rocks.friedrich.engine_omega.actor.Rectangle;
import rocks.friedrich.engine_omega.event.KeyListener;

public class StaticKeyListenerExample extends Scene
{
    Rectangle rectangle;

    public StaticKeyListenerExample()
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
        StaticKeyListenerExample scene = new StaticKeyListenerExample();
        Game.start(600, 400, scene);
        Game.addKeyListener(new KeyListener()
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
