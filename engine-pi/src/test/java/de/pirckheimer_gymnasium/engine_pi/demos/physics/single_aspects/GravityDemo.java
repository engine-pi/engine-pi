package de.pirckheimer_gymnasium.engine_pi.demos.physics.single_aspects;

import java.awt.event.KeyEvent;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Scene;
import de.pirckheimer_gymnasium.engine_pi.actor.Circle;
import de.pirckheimer_gymnasium.engine_pi.actor.Rectangle;
import de.pirckheimer_gymnasium.engine_pi.event.KeyStrokeListener;

public class GravityDemo extends Scene implements KeyStrokeListener
{
    private final Circle circle;

    public GravityDemo()
    {
        getCamera().setMeter(45);
        circle = createCircle();
        circle.makeDynamic();
        setGravity(0, -9.81);
        // oben
        createBorder(-5, 4.5, false);
        // unten
        createBorder(-5, -5, false);
        // links
        createBorder(-5, -5, true);
        // rechts
        createBorder(4.5, -5, true);
    }

    private Rectangle createBorder(double x, double y, boolean vertical)
    {
        Rectangle rectangle = !vertical ? new Rectangle(10, 0.5)
                : new Rectangle(0.5, 10);
        rectangle.setPosition(x, y);
        rectangle.makeStatic();
        add(rectangle);
        return rectangle;
    }

    @Override
    public void onKeyDown(KeyEvent e)
    {
        switch (e.getKeyCode())
        {
        case KeyEvent.VK_UP -> setGravity(0, 9.81);
        case KeyEvent.VK_DOWN -> setGravity(0, -9.81);
        case KeyEvent.VK_RIGHT -> setGravity(9.81, 0);
        case KeyEvent.VK_LEFT -> setGravity(-9.81, 0);
        case KeyEvent.VK_SPACE -> circle.sleep();
        }
    }

    @Override
    public void setGravity(double x, double y)
    {
        // Die Figur muss aufgeweckt werden, falls sie zur Ruhe gekommen ist.
        // Sonst wirkt eine Änderung der Gravitation nicht.
        circle.awake();
        super.setGravity(x, y);
    }

    public static void main(String[] args)
    {
        Game.setDebug(true);
        Game.start(new GravityDemo());
    }
}
