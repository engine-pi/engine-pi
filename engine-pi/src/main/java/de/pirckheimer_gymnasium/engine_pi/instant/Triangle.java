package de.pirckheimer_gymnasium.engine_pi.instant;

import de.pirckheimer_gymnasium.engine_pi.Scene;
import de.pirckheimer_gymnasium.engine_pi.Vector;
import de.pirckheimer_gymnasium.engine_pi.actor.ActorAdder;
import de.pirckheimer_gymnasium.engine_pi.annotations.API;

/**
 * Beschreibt die <b>Instant-Variante</b> eines <b>Dreiecks</b>.
 *
 * <p>
 * Das Dreieck ist standardmäßig <b>gelb</b> gefärbt. Gelb steht bei
 * <a href="https://www.byk-instruments.com/de/color-determines-shape">Itten</a>
 * für den Geist und das Denken. Gelb zeigt sich kämpferisch und aggressiv,
 * besitzt einen schwerelosen Charakter und diesem Charakter entspricht laut
 * Itten das Dreieck.
 * </p>
 *
 * @see de.pirckheimer_gymnasium.engine_pi.actor.Triangle
 * @see ActorAdder
 *
 * @author Josef Friedrich
 *
 * @since 0.33.0
 */
public class Triangle extends de.pirckheimer_gymnasium.engine_pi.actor.Triangle
        implements InstantActor
{

    /**
     * Erzeugt ein <b>gleichseitiges</b> Dreieck mit einer Seitenlänge von <b>1
     * Meter</b>. Die Spitze zeigt nach oben.
     *
     * @see de.pirckheimer_gymnasium.engine_pi.actor.Triangle#Triangle()
     * @see ActorAdder#addTriangle()
     *
     * @since 0.33.0
     */
    public Triangle()
    {
        this(1);
    }

    /**
     * Erzeugt ein <b>gleichseitiges</b> Dreieck. Die Spitze zeigt nach oben.
     *
     * @param sideLength Die Seitenlänge des gleichseitigen Dreiecks.
     *
     * @see de.pirckheimer_gymnasium.engine_pi.actor.Triangle#Triangle(double)
     * @see ActorAdder#addTriangle(double)
     *
     * @since 0.33.0
     */
    public Triangle(double sideLength)
    {
        this(new Vector(0, 0), new Vector(sideLength, 0),
                new Vector(sideLength / 2.0, Math.sqrt(3) / 2.0 * sideLength));
    }

    /**
     * Erzeugt ein <b>gleichschenkliges</b> Dreieck, dessen Symmetrieachse
     * vertikal ausgerichtet ist. Die Spitze zeigt nach oben.
     *
     * @param width Die Breite des gleichschenkligen Dreiecks - genauer gesagt
     *     die Länge der Basis.
     * @param height Die Höhe der Symmetrieachse.
     *
     * @see de.pirckheimer_gymnasium.engine_pi.actor.Triangle#Triangle(double,
     *     double)
     * @see ActorAdder#addTriangle(double, double)
     *
     * @since 0.33.0
     */
    public Triangle(double width, double height)
    {
        this(new Vector(0, 0), new Vector(width, 0),
                new Vector(width / 2, height));
    }

    /**
     * Erzeugt ein neues Dreieck durch Angabe der <b>x- und y-Koordinaten</b>
     * von <b>drei Punkten</b>.
     *
     * @param x1 Die x-Koordinate des ersten Eckpunkts.
     * @param y1 Die y-Koordinate des ersten Eckpunkts.
     * @param x2 Die x-Koordinate des zweiten Eckpunkts.
     * @param y2 Die y-Koordinate des zweiten Eckpunkts.
     * @param x3 Die x-Koordinate des dritten Eckpunkts.
     * @param y3 Die y-Koordinate des dritten Eckpunkts.
     *
     * @see de.pirckheimer_gymnasium.engine_pi.actor.Triangle#Triangle(double,
     *     double, double, double, double, double)
     * @see ActorAdder#addTriangle(double, double, double, double, double,
     *     double)
     *
     * @since 0.33.0
     */
    @API
    public Triangle(double x1, double y1, double x2, double y2, double x3,
            double y3)
    {
        this(new Vector(x1, y1), new Vector(x2, y2), new Vector(x3, y3));
    }

    /**
     * Erzeugt ein neues Dreieck durch Angabe von <b>drei Punkten</b>.
     *
     * @param point1 Die Koordinate des ersten Eckpunkts.
     * @param point2 Die Koordinate des zweiten Eckpunkts.
     * @param point3 Die Koordinate des dritten Eckpunkts.
     *
     * @see de.pirckheimer_gymnasium.engine_pi.actor.Triangle#Triangle(Vector,
     *     Vector, Vector)
     * @see ActorAdder#addTriangle(Vector, Vector, Vector)
     *
     * @since 0.33.0
     */
    public Triangle(Vector point1, Vector point2, Vector point3)
    {
        super(point1, point2, point3);
        InstantController.addActors(this);
    }

    /**
     * @since 0.33.0
     */
    public Scene getMainScene()
    {
        return InstantController.getMainScene();
    }

    public static void main(String[] args)
    {
        new Triangle();
    }
}
