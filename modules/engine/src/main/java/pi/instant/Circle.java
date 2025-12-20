package pi.instant;

import pi.Scene;
import pi.actor.ActorAdder;

/**
 * Beschreibt die <b>Instant-Variante</b> eines <b>Kreises</b>.
 *
 * <p>
 * Das Kreis ist standardmäßig <b>blau</b> gefärbt. Die Farbe Blau wirkt für
 * <a href="https://www.byk-instruments.com/de/color-determines-shape">Itten</a>
 * rund, erweckt ein Gefühl der Entspanntheit und Bewegung und steht für den „in
 * sich bewegten Geist“, wie er sich ausdrückt. Der Kreis entspricht der Farbe
 * Blau, da er ein Symbol der „stetigen Bewegung“ darstelle.
 * </p>
 *
 * @see pi.actor.Circle
 * @see ActorAdder
 *
 * @author Josef Friedrich
 *
 * @since 0.34.0
 */
public class Circle extends pi.actor.Circle implements InstantActor
{

    /**
     * Erzeugt einen <b>Kreis</b> mit <b>einem Meter Durchmesser</b>.
     *
     * @see pi.actor.Circle#Circle()
     * @see ActorAdder#addCircle()
     *
     * @since 0.34.0
     */
    public Circle()
    {
        this(1);
    }

    /**
     * Erzeugt einen <b>Kreis</b> durch Angabe des <b>Durchmessers</b>.
     *
     * @param diameter Der <b>Durchmesser</b> des Kreises.
     *
     * @see pi.actor.Circle#Circle(double)
     * @see ActorAdder#addCircle(double)
     *
     * @since 0.34.0
     */
    public Circle(double diameter)
    {
        super(diameter);
        Controller.addActors(this);
    }

    /**
     * @since 0.34.0
     */
    public Scene getMainScene()
    {
        return Controller.getMainScene();
    }

    public static void main(String[] args)
    {
        new Circle();
    }
}
