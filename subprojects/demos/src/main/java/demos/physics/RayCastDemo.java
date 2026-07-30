package demos.physics;

import org.jbox2d.collision.RayCastInput;
import org.jbox2d.collision.RayCastOutput;
import org.jbox2d.common.Vec2;
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
            for (Body body = worldHandler().world()
                .getBodyList(); body != null; body = body.getNext())
            {
                System.out.println(body);
                for (Fixture fixture = body
                    .getFixtureList(); fixture != null; fixture = fixture
                        .getNext())
                {
                    System.out.println(fixture);
                    RayCastInput in = new RayCastInput(new Vec2(0, 0), new Vec2(0, 1), 10);
                    RayCastOutput out = new RayCastOutput();
                    System.out.println(fixture.raycast(out, in, 0));
                    System.out.println(out.fraction);
                    System.out.println(out.normal);
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
