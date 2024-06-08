package de.pirckheimer_gymnasium.engine_pi.demos;

import java.awt.Color;
import java.awt.event.KeyEvent;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Scene;
import de.pirckheimer_gymnasium.engine_pi.Vector;
import de.pirckheimer_gymnasium.engine_pi.actor.Polygon;
import de.pirckheimer_gymnasium.engine_pi.event.KeyListener;

public class RotationDemo extends Scene implements KeyListener
{
    Polygon triangle;

    public RotationDemo()
    {
        triangle = new Polygon(new Vector(0, 0), new Vector(1, 0),
                new Vector(.5, 3));
        triangle.setColor(Color.BLUE);
        add(triangle);
    }

    @Override
    public void onKeyDown(KeyEvent keyEvent)
    {
        switch (keyEvent.getKeyCode())
        {
        case KeyEvent.VK_LEFT:
            // Gegen den Uhrzeigersinn
            triangle.rotateBy(90);
            break;

        case KeyEvent.VK_RIGHT:
            // Im Uhrzeigersinn
            triangle.rotateBy(-90);
            break;

        case KeyEvent.VK_1:
            triangle.setRotation(0);
            break;

        case KeyEvent.VK_2:
            triangle.setRotation(90);
            break;

        case KeyEvent.VK_3:
            triangle.setRotation(180);
            break;

        case KeyEvent.VK_4:
            triangle.setRotation(270);
            break;
        }
    }

    public static void main(String[] args)
    {
        Game.start(600, 400, new RotationDemo());
    }
}
