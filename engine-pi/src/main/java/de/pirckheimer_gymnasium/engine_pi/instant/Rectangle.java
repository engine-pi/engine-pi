package de.pirckheimer_gymnasium.engine_pi.instant;

import de.pirckheimer_gymnasium.engine_pi.Scene;
import de.pirckheimer_gymnasium.engine_pi.actor.ActorAdder;

/**
 * @since 0.33.0
 */
public class Rectangle
        extends de.pirckheimer_gymnasium.engine_pi.actor.Rectangle
        implements InstantActor
{

    /**
     * Erzeugt ein <b>Rechteck</b> durch Angabe der <b>Breite</b> und
     * <b>Höhe</b>.
     *
     * @param width Die <b>Breite</b> des Rechtecks in Meter.
     * @param height Die <b>Höhe</b> des Rechtecks in Meter.
     *
     * @see ActorAdder#addRectangle(double, double)
     */
    public Rectangle(double width, double height)
    {
        super(width, height);
        InstantController.addActors(this);
    }

    /**
     * Erzeugt ein <b>Quadrat</b> mit der Seitenlängen von <b>einem Meter</b>.
     *
     * @see ActorAdder#addRectangle(double, double)
     */
    public Rectangle()
    {
        this(1, 1);
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
        new Rectangle();
        new Rectangle().setPosition(2, 2).setColor("blue");
    }

}
