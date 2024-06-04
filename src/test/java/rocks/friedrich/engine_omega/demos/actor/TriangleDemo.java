package rocks.friedrich.engine_omega.demos.actor;

import rocks.friedrich.engine_omega.Game;
import rocks.friedrich.engine_omega.Scene;
import rocks.friedrich.engine_omega.actor.Rectangle;
import rocks.friedrich.engine_omega.actor.Triangle;

public class TriangleDemo extends Scene
{
    public TriangleDemo()
    {
        setGravityOfEarth();
        // Kippt beim Aufprall
        Triangle triangle = new Triangle(5, 2);
        triangle.makeDynamic();
        triangle.rotateBy(45);
        add(triangle);
        // Der Anker ist links unten.
        Triangle triangle2 = new Triangle(5, 5);
        triangle2.makeStatic();
        triangle2.setPosition(5, 0);
        add(triangle2);
        Rectangle ground = new Rectangle(20, 1);
        ground.setPosition(-10, -8);
        ground.makeStatic();
        add(ground);
    }

    public static void main(String[] args)
    {
        Game.setDebug(true);
        Game.start(new TriangleDemo());
    }
}
