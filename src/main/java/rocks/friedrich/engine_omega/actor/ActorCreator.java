package rocks.friedrich.engine_omega.actor;

import java.awt.Color;

import rocks.friedrich.engine_omega.Scene;
import rocks.friedrich.engine_omega.Vector;
import rocks.friedrich.engine_omega.annotations.API;

/**
 * Schnittstelle um {@link Actor}-Objekte leicher erzeugen zu können.
 *
 * <p>
 * Mit Hilfe dieses Interfaces können die Klassen {@link Scene} and
 * {@link rocks.friedrich.engine_omega.Layer Layer} um einige Hilfsmethoden
 * erweitert werden, ohne sie dabei mit vielen weiteren Methoden zu
 * überfrachten. Die erzeugten {@link Actor}-Objekt werden gleich zur Szene bzw.
 * zur Ebene hinzugefügt.
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

    /**
     * Erzeugt ein neues Dreieck durch Angabe von drei Punkten.
     *
     * @param point1 Die Koordinate des ersten Eckpunkts.
     * @param point2 Die Koordinate des zweiten Eckpunkts.
     * @param point3 Die Koordinate des dritten Eckpunkts.
     *
     * @see Triangle#Triangle(Vector, Vector, Vector)
     */
    default Triangle createTriangle(Vector point1, Vector point2, Vector point3)
    {
        Triangle actor = new Triangle(point1, point2, point3);
        getScene().add(actor);
        return actor;
    }

    /**
     * Erzeugt ein neues Dreieck durch Angabe der x- und y-Koordinate von drei
     * Punkten.
     *
     * @param x1 Die x-Koordinate des ersten Eckpunkts.
     * @param y1 Die y-Koordinate des ersten Eckpunkts.
     * @param x2 Die x-Koordinate des zweiten Eckpunkts.
     * @param y2 Die y-Koordinate des zweiten Eckpunkts.
     * @param x3 Die x-Koordinate des dritten Eckpunkts.
     * @param y3 Die y-Koordinate des dritten Eckpunkts.
     *
     * @see Triangle#Triangle(double, double, double, double, double, double)
     */
    @API
    default Triangle createTriangle(double x1, double y1, double x2, double y2,
            double x3, double y3)
    {
        Triangle actor = new Triangle(x1, y1, x2, y2, x3, y3);
        getScene().add(actor);
        return actor;
    }

    /**
     * Erzeugt ein gleichschenkliges Dreieck, dessen Symmetrieachse vertikal
     * ausgerichtet ist. Die Spitze zeigt nach oben.
     *
     * @param width  Die Breite des gleichschenkligen Dreicks - genauer gesagt
     *               die Länge der Basis.
     * @param height Die Höhe der Symmetrieachse.
     *
     * @see Triangle#Triangle(double, double)
     */
    default Triangle createTriangle(double width, double height)
    {
        Triangle actor = new Triangle(width, height);
        getScene().add(actor);
        return actor;
    }
}
