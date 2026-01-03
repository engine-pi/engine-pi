package demos.classes.actor;

import pi.Game;
import pi.Scene;
import pi.actor.Image;

public class ActorSetFixtureFromStringDemo extends Scene
{
    public ActorSetFixtureFromStringDemo()
    {
        meter(50);
        Image rectangle = new Image("dude/box/obj_box001.png", 1, 1);
        rectangle.fixtures("R 0.25, 0.25, 0.5, 0.5");
        add(rectangle);
        Image circle = new Image("dude/moon.png", 1, 1);
        circle.fixtures("C 0.5, 0.5, 0.3");
        circle.position(1, 1);
        add(circle);
    }

    public static void main(String[] args)
    {
        Game.debug();
        Game.start(new ActorSetFixtureFromStringDemo());
    }
}
