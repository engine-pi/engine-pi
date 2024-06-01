package rocks.friedrich.engine_omega.demos.physics.single_aspects;

import rocks.friedrich.engine_omega.Game;
import rocks.friedrich.engine_omega.Scene;
import rocks.friedrich.engine_omega.actor.Circle;
import rocks.friedrich.engine_omega.actor.Rectangle;

public class RestitutionDemo extends Scene
{
    private final Rectangle ground;

    public RestitutionDemo()
    {
        getCamera().setZoom(5);
        ground = new Rectangle(20, 1);
        ground.setPosition(-10, -8);
        ground.makeStatic();
        setGravity(0, -9.81);
        add(ground);
        double restitution = 0.6;
        for (double x = -9.5; x < 10; x += 2)
        {
            restitution += 0.1;
            createCircle(x, restitution);
        }
    }

    private void createCircle(double x, double restitution)
    {
        Circle circle = new Circle(1);
        add(circle);
        circle.setRestitution(restitution);
        circle.setPosition(x, 5);
        circle.makeDynamic();
    }

    public static void main(String[] args)
    {
        Game.setDebug(true);
        Game.start(new RestitutionDemo());
    }
}
