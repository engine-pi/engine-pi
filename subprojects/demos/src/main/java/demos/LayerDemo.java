package demos;

import pi.Controller;
import pi.Layer;
import pi.Scene;
import pi.actor.Actor;
import pi.Circle;

public class LayerDemo extends Scene
{

    Layer layer2;

    Layer layer3;

    Circle circle1;

    Circle circle2;

    Circle circle3;

    public LayerDemo()
    {

        layer2 = new Layer();
        addLayer(layer2);

        layer3 = new Layer();
        addLayer(layer3);

        circle1 = new Circle(1);
        circle1.color("red");
        add(circle1);

        circle2 = new Circle(2);
        circle2.color("green");
        layer2.add(circle2);

        circle3 = new Circle(3);
        circle3.color("blue");
        layer3.add(circle3);

        for (Actor actor : addedActors())
        {
            System.out.println("Zur Ebene hinzugefügt: " + actor);
            System.out.println(actor.center());
        }

        // Die Figuren werden mit Verzögerung zur Ebene bzw. zur Szene
        // hinzugefügt.
        delay(1, () -> {
            for (Actor actor : actors())
            {
                System.out
                        .println("In der Physics-Engine registriert: " + actor);
                System.out.println(actor.center());
            }
        });
    }

    public static void main(String[] args)
    {
        Controller.start(new LayerDemo());
    }
}
