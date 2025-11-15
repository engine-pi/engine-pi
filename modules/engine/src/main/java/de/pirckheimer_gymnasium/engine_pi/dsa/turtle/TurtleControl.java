package de.pirckheimer_gymnasium.engine_pi.dsa.turtle;

import de.pirckheimer_gymnasium.engine_pi.Vector;

// Demo: file:///home/jf/repos/school/monorepo/inf/java/engine-pi/modules/demos/src/main/java/de/pirckheimer_gymnasium/demos/classes/dsa/turtle/TurtleDemo.java

/**
 * Definiert die Methoden mit denen die Schildkröte gesteuert werden kann.
 *
 * Es handelt sich um das absolute Minimun an Methoden um eine Turtlegrafik
 * zeichnen zu können. Methoden, die grafische Einstellungen vornehmen sind
 * nicht enhalten.
 */
public interface TurtleControl
{
    /**
     * <b>Bewegt</b> die Schildkröte in Blickrichtung 3 Meter nach vorne.
     *
     * @since 0.38.0
     */
    public void move();

    /**
     * <b>Bewegt</b> die Schildkröte in Blickrichtung nach vorne.
     *
     * <p>
     * In der GraphicsAndGames-Engine des Cornelsen Verlags heißt die Methode
     * {@code Gehen}, in der Engine Alpha {@code laufe}.
     * </p>
     *
     * @param distance Die <b>Entfernung</b> in Meter, die die Schildkröte
     *     zurücklegen soll.
     *
     * @since 0.38.0
     */
    public void move(double distance);

    /**
     * Setzt die Schildkröte auf eine neue <b>Position</b>.
     *
     * <p>
     * Im Gegensatz zur {@link #move(double)}-Methode geschieht die Bewegung
     * hier ruckhaft. Die Schildkröte wird quasi in die Luft gehoben und an
     * einer anderen Stelle wieder abgesetzt. Deshalb wird auch keine Linie
     * gezeichnet und auch keine Animation durchgeführt. Sonst könnte mit dieser
     * Methode „geschummelt“ werden.
     * </p>
     *
     * @param position Die gewünschte <b>Position</b> als Vektor in Meter.
     *
     * @since 0.38.0
     */
    void setPosition(Vector position);

    /**
     * Setzt die Schildkröte auf eine neue <b>Position</b>.
     *
     * <p>
     * Im Gegensatz zur {@link #move(double)}-Methode geschieht die Bewegung
     * hier ruckhaft. Die Schildkröte wird quasi in die Luft gehoben und an
     * einer anderen Stelle wieder abgesetzt. Deshalb wird auch keine Linie
     * gezeichnet und auch keine Animation durchgeführt. Sonst könnte mit dieser
     * Methode „geschummelt“ werden.
     * </p>
     *
     * @param x Die <b>x</b>-Koordinate der gewünschten Position in Meter.
     * @param y Die <b>y</b>-Koordinate der gewünschten Position in Meter.
     *
     * @since 0.38.0
     */
    void setPosition(double x, double y);

    /**
     * <b>Dreht</b> die Schildkröte.
     *
     * <p>
     * In der GraphicsAndGames-Engine des Cornelsen Verlags heißt die Methode
     * {@code Drehen}, in der Engine Alpha {@code rotiere}.
     * </p>
     *
     * @param rotation Der <b>Drehwinkel</b> in Grad. Positive Werte drehen
     *     gegen den Uhrzeigersinn, negative Werte im Uhrzeigersinn.
     *
     * @since 0.38.0
     */
    public void rotate(double rotation);

    /**
     * Setzt die <b>Blickrichtung</b> der Schildkröte.
     *
     * @param direction Die Blickrichtung der Schildkröte in Grad: 0°: nach
     *     rechts (Osten), 90°: nach oben (Norden) 180°: nach links (Westen)
     *     270°: nach unten (Süden)
     *
     * @since 0.38.0
     */
    void setDirection(double direction);

    /**
     * <b>Wechselt</b> in den Modus <em>„zeichnen“</em>.
     *
     * <p>
     * In der GraphicsAndGames-Engine des Cornelsen Verlags heißt die Methode
     * {@code StiftSenken}, in der Engine Alpha {@code ansetzen}.
     * </p>
     *
     * @since 0.38.0
     */
    public void lowerPen();

    /**
     * <b>Wechselt</b> in den Modus <em>„nicht zeichnen“</em>.
     *
     * <p>
     * In der GraphicsAndGames-Engine des Cornelsen Verlags heißt die Methode
     * {@code StiftHeben}, in der Engine Alpha {@code absetzen}.
     * </p>
     *
     * @since 0.38.0
     */
    public void liftPen();

}
