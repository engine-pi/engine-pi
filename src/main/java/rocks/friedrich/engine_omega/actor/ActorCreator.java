package rocks.friedrich.engine_omega.actor;

import java.awt.Color;

import rocks.friedrich.engine_omega.Scene;

/**
 * Schnittstelle um {@link Actor}-Objekte leicher erzeugen zu können.
 *
 * <p>
 * Mit Hilfe dieses Interfaces können die Klassen {@link Scene} and
 * {@link rocks.friedrich.engine_omega.Layer Layer} um einige Hilfsmethoden erweitert werden, ohne sie dabei mit
 * vielen weiteren Methoden zu überfrachten. Die erzeugten
 * {@link Actor}-Objekt werden gleich zur Szene bzw. zur Ebene hinzugefügt.
 * </p>
 *
 * @see Scene
 * @see rocks.friedrich.engine_omega.Layer
 */
public interface ActorCreator
{
    Scene getScene();

    default Rectangle createRectangle(double width, double height, double x,
            double y)
    {
        Rectangle actor = new Rectangle(width, height);
        actor.setPosition(x, y);
        getScene().add(actor);
        return actor;
    }

    default Rectangle createRectangle(double width, double height)
    {
        return createRectangle(width, height, 0, 0);
    }

    default Rectangle createRectangle(int x, int y)
    {
        return createRectangle(1, 1, x, y);
    }

    default Circle createCircle(double diameter, double x, double y)
    {
        Circle actor = new Circle(diameter);
        actor.setPosition(x, y);
        getScene().add(actor);
        return actor;
    }

    default Circle createCircle(double x, double y)
    {
        return createCircle(1, x, y);
    }

    default Circle createCircle(double x, double y, Color color)
    {
        Circle actor = createCircle(1, x, y);
        actor.setColor(color);
        return actor;
    }
}
