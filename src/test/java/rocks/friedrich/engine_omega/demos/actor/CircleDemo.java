package rocks.friedrich.engine_omega.demos.actor;

import rocks.friedrich.engine_omega.Game;
import rocks.friedrich.engine_omega.actor.Circle;

public class CircleDemo extends ActorBaseScene
{
    public CircleDemo()
    {
        // Konstruktor ohne Parameter
        Circle circle = new Circle();
        circle.setPosition(2, 1);
        circle.makeDynamic();
        add(circle);
        // Konstruktor mit Angabe des Durchmessers
        Circle circle2 = new Circle(2);
        circle2.setPosition(-2, 0);
        circle2.makeStatic();
        add(circle2);
        // Mit Hilfe der create... Methoden erzeugen.
        createCircle(5, 3).makeStatic();
        createCircle(3, -6, -3).makeStatic();
    }

    public static void main(String[] args)
    {
        Game.setDebug(true);
        Game.start(new CircleDemo());
    }
}
