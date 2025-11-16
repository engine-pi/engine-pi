package de.pirckheimer_gymnasium.engine_pi.dsa.turtle;

import de.pirckheimer_gymnasium.engine_pi.Vector;

// Go to file: file:///home/jf/repos/school/monorepo/inf/java/engine-pi/modules/demos/src/main/java/de/pirckheimer_gymnasium/demos/classes/dsa/turtle/TurtleDemo.java

/**
 * Definiert die Methoden, mit denen die Schildkröte gesteuert werden kann.
 *
 * <p>
 * Es handelt sich um das absolute Minimun an Methoden um eine Turtlegrafik
 * zeichnen zu können. Methoden, die grafische Einstellungen vornehmen sind
 * nicht enhalten.
 * </p>
 *
 * @since 0.40.0
 */
public class TurtleController
{
    /**
     * @since 0.40.0
     */
    protected TurtleScene scene;

    /**
     * @since 0.40.0
     */
    public TurtleBackgroundController background;

    /**
     * @since 0.40.0
     */
    public TurtlePen pen;

    /**
     * @since 0.40.0
     */
    public TurtleAnimationController animation;

    /**
     * @since 0.40.0
     */
    private double defaultDistance = 3;

    /**
     * @since 0.40.0
     */
    public TurtleController(TurtleScene scene)
    {
        this.scene = scene;
        background = new TurtleBackgroundController(scene);
        pen = scene.pen;
        animation = new TurtleAnimationController(scene);
    }

    /**
     * <b>Bewegt</b> die Schildkröte in Blickrichtung 3 Meter nach vorne.
     *
     * @since 0.38.0
     */
    public void forward()
    {
        scene.moveTurtleForward(defaultDistance);
    }

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
    public void forward(double distance)
    {
        scene.moveTurtleForward(distance);
    }

    /**
     * @since 0.40.0
     */
    public void backward()
    {
        scene.moveTurtleForward(defaultDistance * -1);
    }

    /**
     * @since 0.40.0
     */
    public void backward(double distance)
    {
        scene.moveTurtleForward(distance * -1);
    }

    /**
     * Setzt die Schildkröte auf eine neue <b>Position</b>.
     *
     * <p>
     * Im Gegensatz zur {@link #forward(double)}-Methode geschieht die Bewegung
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
    public void setPosition(Vector position)
    {
        scene.setPosition(position);
    }

    /**
     * Setzt die Schildkröte auf eine neue <b>Position</b>.
     *
     * <p>
     * Im Gegensatz zur {@link #forward(double)}-Methode geschieht die Bewegung
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
    public void setPosition(double x, double y)
    {
        scene.setPosition(new Vector(x, y));
    }

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
    public void left(double rotation)
    {
        scene.rotateTurtle(rotation);
    }

    /**
     * @since 0.40.0
     */
    public void right(double rotation)
    {
        left(rotation * -1);
    }

    /**
     * Setzt die <b>Blickrichtung</b> der Schildkröte.
     *
     * @param direction Die Blickrichtung der Schildkröte in Grad: 0°: nach
     *     rechts (Osten), 90°: nach oben (Norden) 180°: nach links (Westen)
     *     270°: nach unten (Süden)
     *
     * @since 0.38.0
     */
    public void setDirection(double direction)
    {
        scene.setDirection(direction);
    }

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
    public void lowerPen()
    {
        pen.lower();
    }

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
    public void liftPen()
    {
        pen.lift();
    }
}
