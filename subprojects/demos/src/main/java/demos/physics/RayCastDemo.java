package demos.physics;

import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.Fixture;

import pi.Controller;
import pi.Scene;
import pi.actor.Circle;
import pi.actor.Rectangle;

public class RayCastDemo extends Scene
{
    public RayCastDemo()
    {
        Circle circle = new Circle(4);
        Rectangle rectangle = new Rectangle(2, 4);
        add(circle, rectangle);

        delay(0.1, () -> {
            for (Body b = worldHandler().world().getBodyList(); b != null; b = b
                .getNext())
            {
                System.out.println(b);
                for (Fixture f = b.getFixtureList(); f != null; f = f.getNext())
                {
                    System.out.println(f);
                }
            }
        });
    }

    public static void main(String[] args)
    {
        Controller.instantMode(false);
        Controller.start(new RayCastDemo());
    }
}
