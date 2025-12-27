/*
 * Engine Pi ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2025 Josef Friedrich and contributors.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package pi.dsa.turtle;

import pi.Vector;

// Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/modules/demos/src/main/java/de/pirckheimer_gymnasium/demos/classes/dsa/turtle/TurtleDemo.java

/**
 * Definiert die Methoden, mit denen die Schildkröte <b>gesteuert</b> werden
 * kann.
 *
 * <p>
 * Es handelt sich um das absolute Minimun an Methoden um eine Turtlegrafik
 * zeichnen zu können. Methoden, die grafische Einstellungen vornehmen sind
 * nicht enhalten.
 * </p>
 *
 * @author Josef Friedrich
 *
 * @since 0.40.0
 */
public class TurtleController
{

    /* Öffentliche “Controller” als deligierte Klasse, alphabetisch sortiert */

    /**
     * Steuert die <b>Animationen</b>, die während des Malprozesses der
     * Schildkröte zu sehen sind.
     *
     * @since 0.40.0
     */
    public final TurtleAnimationController animation;

    /**
     * Steuert die Malfläche, also den <b>Hintergrund</b>, auf dem die
     * Schildkröte malt.
     *
     * @since 0.40.0
     */
    public final TurtleBackgroundController background;

    /**
     * Steuert die <b>Kleidung</b> der Schildkröte, d. h. das Aussehen der
     * Schildkröte.
     *
     * @since 0.41.0
     */
    public final TurtleDressController dress;

    /**
     * Steuert den <b>Mal- bzw{@literal .} Zeichenstift</b> der Schildkröte.
     *
     * @since 0.40.0
     */
    public final TurtlePenController pen;

    /**
     * @since 0.41.0
     */
    public final TurtleWindowController window;

    /* private Attribute */

    /**
     * @since 0.40.0
     */
    protected TurtleScene scene;

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
        animation = scene.animation;
        dress = scene.dress;
        window = new TurtleWindowController();
    }

    /**
     * <b>Bewegt</b> die Schildkröte in Blickrichtung 3 Meter nach <b>vorne</b>.
     *
     * @since 0.38.0
     */
    public void forward()
    {
        scene.moveTurtleForward(defaultDistance);
    }

    /**
     * <b>Bewegt</b> die Schildkröte in Blickrichtung die angegebene Entfernung
     * nach <b>vorne</b>.
     *
     * <p class="development-note">
     * In der GraphicsAndGames-Engine des Cornelsen Verlags heißt die Methode
     * {@code Gehen}, in der Engine Alpha {@code laufe}.
     * </p>
     *
     * @param distance Die <b>Entfernung</b> in Meter, die die Schildkröte
     *     zurücklegen soll. Negative Werte bewegen die Schildkröte rückwärts
     *     statt vorwärts.
     *
     * @since 0.38.0
     */
    public void forward(double distance)
    {
        scene.moveTurtleForward(distance);
    }

    /**
     * <b>Bewegt</b> die Schildkröte in Blickrichtung 3 Meter nach <b>vorne</b>.
     *
     * <p>
     * Diese Methode ist identisch mit der {@link #forward()}-Methode. Es
     * handelt sich um eine Alias-Methode.
     * </p>
     *
     * @see #forward()
     *
     * @since 0.41.0
     */
    public void move()
    {
        scene.moveTurtleForward(defaultDistance);
    }

    /**
     * <b>Bewegt</b> die Schildkröte in Blickrichtung die angegebene Entfernung
     * nach <b>vorne</b>.
     *
     * <p>
     * Diese Methode ist identisch mit der {@link #forward(double)}-Methode. Es
     * handelt sich um eine Alias-Methode.
     * </p>
     *
     * <p class="development-note">
     * In der GraphicsAndGames-Engine des Cornelsen Verlags heißt die Methode
     * {@code Gehen}, in der Engine Alpha {@code laufe}.
     * </p>
     *
     * @param distance Die <b>Entfernung</b> in Meter, die die Schildkröte
     *     zurücklegen soll. Negative Werte bewegen die Schildkröte rückwärts
     *     statt vorwärts.
     *
     * @see #forward(double)
     *
     * @since 0.41.0
     */
    public void move(double distance)
    {
        scene.moveTurtleForward(distance);
    }

    /**
     * <b>Bewegt</b> die Schildkröte in Blickrichtung 3 Meter nach
     * <b>hinten</b>, also <b>rückwärts</b>.
     *
     * @since 0.40.0
     */
    public void backward()
    {
        scene.moveTurtleForward(defaultDistance * -1);
    }

    /**
     * <b>Bewegt</b> die Schildkröte in Blickrichtung die angegebene Entfernung
     * nach <b>hinten</b>, also <b>rückwärts</b>.
     *
     * @param distance Die <b>Entfernung</b> in Meter, die die Schildkröte
     *     zurücklegen soll. Negative Werte bewegen die Schildkröte vorwärts
     *     statt rückwärts.
     *
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
     * <b>Dreht</b> die Blickrichtung der Schildkröte nach <b>links</b>.
     *
     * @param rotation Der <b>Drehwinkel</b> in Grad. Negative Werte drehen die
     *     Schilkröte nach rechts.
     *
     * @since 0.38.0
     */
    public void left(double rotation)
    {
        scene.rotateTurtle(rotation);
    }

    /**
     * <b>Dreht</b> die Blickrichtung der Schildkröte nach <b>rechts</b>.
     *
     * @param rotation Der <b>Drehwinkel</b> in Grad. Negative Werte drehen die
     *     Schilkröte nach links.
     *
     * @since 0.40.0
     */
    public void right(double rotation)
    {
        left(rotation * -1);
    }

    /**
     * <b>Dreht</b> die Blickrichtung der Schildkröte.
     *
     * <p class="development-note">
     * In der GraphicsAndGames-Engine des Cornelsen Verlags heißt die Methode
     * {@code Drehen}, in der Engine Alpha {@code rotiere}.
     * </p>
     *
     * @param rotation Der <b>Drehwinkel</b> in Grad. Positive Werte drehen
     *     gegen den Uhrzeigersinn also nach links, negative Werte im
     *     Uhrzeigersinn also nach rechts.
     *
     * @see #left(double)
     *
     * @since 0.38.0
     */
    public void rotate(double rotation)
    {
        scene.rotateTurtle(rotation);
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
     * <p class="development-note">
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
     * <p class="development-note">
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
