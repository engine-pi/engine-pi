package rocks.friedrich.engine_omega.demos.resources;

import rocks.friedrich.engine_omega.Game;
import rocks.friedrich.engine_omega.Scene;
import rocks.friedrich.engine_omega.actor.Circle;
import rocks.friedrich.engine_omega.actor.Rectangle;

public class ColorSchemaDemo extends Scene
{
    public ColorSchemaDemo()
    {
        Rectangle rectangle = new Rectangle();
        rectangle.setPosition(-2, 0);
        add(rectangle);
        Circle circle = new Circle();
        circle.setPosition(2, 0);
        add(circle);
    }

    public static void main(String[] args)
    {
        Game.start(new ColorSchemaDemo());
    }
}
