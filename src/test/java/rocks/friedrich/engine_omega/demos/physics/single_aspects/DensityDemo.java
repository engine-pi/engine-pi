package rocks.friedrich.engine_omega.demos.physics.single_aspects;

import rocks.friedrich.engine_omega.Game;
import rocks.friedrich.engine_omega.Scene;
import rocks.friedrich.engine_omega.actor.Circle;
import rocks.friedrich.engine_omega.actor.Rectangle;

public class DensityDemo extends Scene
{
    private final Rectangle ground;

    private final Circle circle1;

    private final Circle circle2;

    private final Circle circle3;

    public DensityDemo()
    {
        ground = new Rectangle(20, 1);
        ground.setPosition(-10, -5);
        ground.makeStatic();
        add(ground);
        circle1 = createCircle(-5);
        circle2 = createCircle(0);
        circle3 = createCircle(5);
        delay(1, () -> {
            setGravity(0, -9.81);
            setUpCircle(circle1, 1);
            setUpCircle(circle2, 2);
            setUpCircle(circle3, 100);
        });
    }

    private Circle createCircle(double x)
    {
        Circle circle = new Circle(1);
        circle.setPosition(x, 5);
        circle.makeStatic();
        add(circle);
        return circle;
    }

    private void setUpCircle(Circle circle, double density)
    {
        circle.makeDynamic();
        circle.setDensity(density);
        System.out.println(circle.getDensity());
        System.out.println(circle.getMass());
        System.out.println(circle.getPhysicsHandler());
    }

    public static void main(String[] args)
    {
        Game.setDebug(true);
        Game.start(new DensityDemo());
    }
}
