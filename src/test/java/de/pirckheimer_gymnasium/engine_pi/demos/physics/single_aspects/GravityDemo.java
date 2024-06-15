package de.pirckheimer_gymnasium.engine_pi.demos.physics.single_aspects;

import static de.pirckheimer_gymnasium.engine_pi.Vector.v;

import java.awt.event.KeyEvent;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Scene;
import de.pirckheimer_gymnasium.engine_pi.actor.Circle;
import de.pirckheimer_gymnasium.engine_pi.actor.Polygon;
import de.pirckheimer_gymnasium.engine_pi.actor.Rectangle;
import de.pirckheimer_gymnasium.engine_pi.event.FrameUpdateListener;
import de.pirckheimer_gymnasium.engine_pi.event.KeyStrokeListener;

public class GravityDemo extends Scene
        implements KeyStrokeListener, FrameUpdateListener
{
    private final Circle circle;

    private Polygon arrow;

    public GravityDemo()
    {
        circle = createCircle();
        circle.makeDynamic();
        setGravity(0, -9.81);
        createBorder(-5, 4, false);
        createBorder(-5, -5, false);
        createBorder(-5, -5, true);
        createBorder(4, -5, true);
        createArrow();
    }

    private void createArrow()
    {
        arrow = new Polygon(v(0, 0), v(1, 0), v(0.5, -3));
        arrow.makeStatic();
        arrow.setPosition(3, 3);
        add(arrow);
    }

    private Rectangle createBorder(double x, double y, boolean vertical)
    {
        Rectangle rectangle = !vertical ? new Rectangle(10, 1)
                : new Rectangle(1, 10);
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
    public void onFrameUpdate(double delta)
    {
        // System.out.println(getGravity());
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
