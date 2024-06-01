package rocks.friedrich.engine_omega.demos.physics.single_aspects;

import java.awt.event.KeyEvent;

import rocks.friedrich.engine_omega.Game;
import rocks.friedrich.engine_omega.Scene;
import rocks.friedrich.engine_omega.actor.Circle;
import rocks.friedrich.engine_omega.actor.Rectangle;
import rocks.friedrich.engine_omega.event.KeyListener;

public class DensityDemo extends Scene implements KeyListener
{
    private final Rectangle ground;

    private final Circle circle1;

    private final Circle circle2;

    private final Circle circle3;

    public DensityDemo()
    {
        setGravity(0, -9.81);
        ground = new Rectangle(20, 1);
        ground.setPosition(-10, -5);
        ground.makeStatic();
        add(ground);
        circle1 = createCircle(-5, 10);
        circle2 = createCircle(0, 20);
        circle3 = createCircle(5, 30);
    }

    private Circle createCircle(double x, double density)
    {
        Circle circle = new Circle(1);
        circle.setPosition(x, 5);
        circle.setDensity(density);
        circle.makeDynamic();
        add(circle);
        return circle;
    }

    @Override
    public void onKeyDown(KeyEvent e)
    {
        circle1.applyImpulse(0, 100);
        circle2.applyImpulse(0, 100);
        circle3.applyImpulse(0, 100);
    }

    public static void main(String[] args)
    {
        Game.setDebug(true);
        Game.start(new DensityDemo());
    }
}
