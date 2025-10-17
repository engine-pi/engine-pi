package de.pirckheimer_gymnasium.engine_pi.instant;

import de.pirckheimer_gymnasium.engine_pi.Scene;
import de.pirckheimer_gymnasium.engine_pi.actor.ActorAdder;
import de.pirckheimer_gymnasium.engine_pi.annotations.API;

/**
 * Beschreibt die <b>Instant-Variante</b> eines <b>Rechteck</b>.
 *
 * <p>
 * Das Rechteck ist standardmäßig <b>rot</b> gefärbt. Die Farbe Rot stellt für
 * <a href="https://www.byk-instruments.com/de/color-determines-shape">Itten</a>
 * die körperhafte Materie dar. Sie wirkt statisch und schwer. Er ordnet deshalb
 * der Farbe die statische Form des Quadrates zu.
 * </p>
 *
 * @see de.pirckheimer_gymnasium.engine_pi.actor.Rectangle
 * @see ActorAdder
 *
 * @since 0.33.0
 */
public class Rectangle
        extends de.pirckheimer_gymnasium.engine_pi.actor.Rectangle
        implements InstantActor
{

    /**
     * Erzeugt ein <b>Quadrat</b> mit der Seitenlängen von <b>einem Meter</b>.
     *
     * @see de.pirckheimer_gymnasium.engine_pi.actor.Rectangle#Rectangle()
     * @see ActorAdder#addRectangle()
     *
     * @since 0.34.0
     */
    public Rectangle()
    {
        this(1, 1);
    }

    /**
     * Erzeugt ein <b>Quadrat</b> unter Angabe der <b>Seitenlänge</b>.
     *
     * @param sideLength Die <b>Seitenlänge</b> des Quadrats in Meter.
     *
     * @see de.pirckheimer_gymnasium.engine_pi.actor.Rectangle#Rectangle(double)
     * @see ActorAdder#addRectangle(double)
     *
     * @since 0.34.0
     */
    @API
    public Rectangle(double sideLength)
    {
        this(sideLength, sideLength);
    }

    /**
     * Erzeugt ein <b>Rechteck</b> durch Angabe der <b>Breite</b> und
     * <b>Höhe</b>.
     *
     * @param width Die <b>Breite</b> des Rechtecks in Meter.
     * @param height Die <b>Höhe</b> des Rechtecks in Meter.
     *
     * @see de.pirckheimer_gymnasium.engine_pi.actor.Rectangle#Rectangle(double,
     *     double)
     * @see ActorAdder#addRectangle(double, double)
     */
    public Rectangle(double width, double height)
    {
        super(width, height);
        Controller.addActors(this);
    }

    /**
     * @since 0.33.0
     */
    public Scene getMainScene()
    {
        return Controller.getMainScene();
    }

    public static void main(String[] args)
    {
        new Rectangle();
        new Rectangle().setPosition(2, 2).setColor("blue");
    }

}
