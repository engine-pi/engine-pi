package de.pirckheimer_gymnasium.engine_pi_demos;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Layer;
import de.pirckheimer_gymnasium.engine_pi.Scene;
import de.pirckheimer_gymnasium.engine_pi.actor.Actor;
import de.pirckheimer_gymnasium.engine_pi.actor.Circle;

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
        circle1.setColor("red");
        add(circle1);

        circle2 = new Circle(2);
        circle2.setColor("green");
        layer2.add(circle2);

        circle3 = new Circle(3);
        circle3.setColor("blue");
        layer3.add(circle3);

        for (Actor actor : getAddedActors())
        {
            System.out.println("Zur Ebene hinzugefügt: " + actor);
            System.out.println(actor.getCenter());
        }

        // Die Figuren werden mit Verzögerung zur Ebene bzw. zur Szene
        // hinzugefügt.
        delay(1, () -> {
            for (Actor actor : getActors())
            {
                System.out
                        .println("In der Physics-Engine registriert: " + actor);
                System.out.println(actor.getCenter());
            }
        });
    }

    public static void main(String[] args)
    {
        Game.start(new LayerDemo());
    }
}
