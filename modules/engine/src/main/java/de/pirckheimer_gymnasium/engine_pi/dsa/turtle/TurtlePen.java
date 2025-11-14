package de.pirckheimer_gymnasium.engine_pi.dsa.turtle;

import static de.pirckheimer_gymnasium.engine_pi.Resources.colors;

import java.awt.Color;

import de.pirckheimer_gymnasium.engine_pi.Vector;
import de.pirckheimer_gymnasium.engine_pi.actor.Actor;

/**
 * Der <b>Mal- bzw. Zeichenstift</b> der Schildkröte.
 *
 * @author Josef Friedrich
 *
 * @since 0.40.0
 */
public class TurtlePen
{
    /**
     * Zeigt an, ob die Schildkröte momentan den <b>Stift gesenkt</b> hat und
     * zeichnet oder nicht.
     *
     * @since 0.40.0
     */
    public boolean isDown = true;

    /**
     * Die <b>Linienstärke</b> in Pixel.
     *
     * @since 0.40.0
     */
    public int thickness = 3;

    /**
     * Die <b>Farbe</b> der Linie.
     *
     * @since 0.40.0
     */
    public Color color = colors.get("yellow");

    /**
     * Die aktuelle <b>Position des Stifts</b>.
     *
     * <p>
     * Diese Position wird bewegt und das Zentrum der Figur wird auf diese
     * Position gesetzt. Es reicht nicht, die Stiftposition über die Methode
     * {@link Actor#getCenter()} der Schildkrötenfigur zu bestimmen, denn bei
     * einer Rotation ändert sich das Zentrum.
     * </p>
     *
     * @since 0.40.0
     */
    public Vector position = new Vector(0, 0);

    /**
     * Die aktuelle Schreib<b>richtung</b>.
     *
     * <p>
     * Wir speichern die aktuelle Rotation und verwenden nicht die Rotation der
     * grafischen Repräsentation. Möglicherweise läuft die Physics-Engine in
     * einem anderen Thread. Im Warp-Modus entstehen komisch verzerrte Grafiken.
     * </p>
     *
     * @since 0.40.0
     */
    public double direction = 0;

}
