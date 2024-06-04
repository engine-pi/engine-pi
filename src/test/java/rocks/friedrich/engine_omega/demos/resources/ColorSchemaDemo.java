package rocks.friedrich.engine_omega.demos.resources;

import rocks.friedrich.engine_omega.Game;
import rocks.friedrich.engine_omega.Scene;
import rocks.friedrich.engine_omega.actor.Circle;
import rocks.friedrich.engine_omega.actor.Rectangle;
import rocks.friedrich.engine_omega.resources.Container;

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
        Circle circle2 = new Circle();
        circle2.setPosition(1, -2);
        circle2.setColor(Container.colors.getYellowGreen());
        add(circle2);
        Circle circle3 = new Circle();
        circle3.setPosition(1, -4);
        circle3.setColor(Container.colors.getYellow());
        add(circle3);
    }

    public static void main(String[] args)
    {
        Game.start(new ColorSchemaDemo());
    }
}
