/*
 * https://github.com/engine-alpha/engine-alpha/blob/4.x/engine-alpha-edu/src/main/java/ea/edu/turtle/Turtle.java
 *
 * Engine Alpha ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2011 - 2020 Michael Andonie and contributors.
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

import static pi.Resources.colors;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;

import pi.Game;
import pi.Vector;
import pi.animation.ValueAnimator;
import pi.animation.interpolation.LinearDouble;
import pi.annotations.Setter;
import pi.graphics.PaintingSurface;
import pi.resources.color.ColorUtil;

/*
 * Implementation so ähnlich wie <a href="https://github.com/engine-pi/engine-pi/blob/main/modules/games/blockly-robot/src/main/java/de/pirckheimer_gymnasium/blockly_robot/robot/gui/robot/ImageRobot.java">ImageRobot
 * </a>in Blockly Robot
 */

// Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/modules/demos/src/main/java/de/pirckheimer_gymnasium/demos/classes/dsa/turtle/TurtleDemo.java

/**
 * Vereint alle Teil-Klassen in einer <b>Szene</b>.
 *
 * @since 0.38.0
 *
 * @author Josef Friedrich
 * @author Michael Andonie
 * @author Niklas Keller
 */
public class TurtleScene extends PaintingSurfaceScene
{
    final TurtleAnimationController animation;

    /**
     * Die <b>grafische Repräsentation</b> der Schildkröte.
     */
    final TurtleDressController dress;

    final TurtlePenController pen;

    final TurtleStatisticsController stats;

    /**
     * Die Hintergrundfarbe der Zeichenfläche.
     */
    private Color backgroundColor = ColorUtil
            .changeSaturation(colors.get("yellow"), 0.7);

    /**
     * Die <b>Malfläche</b>, in die die Schildkröte zeichnet.
     */
    private PaintingSurface surface;

    /**
     * Das Zentrum der Schildkröte vor der Aktualisierung. Diese Position wird
     * verwendet, um die einzelnen Liniensegmente zu zeichnen.
     */
    private Vector lastPosition;

    /**
     * Erzeugt eine Schildkröte in einer <b>gegebenen Szene</b>.
     *
     * @since 0.38.0
     */
    public TurtleScene()
    {
        // Den Instant-Modus deaktivieren, da wir die benötigten Figuren „von
        // Hand“ hinzufügen.
        Game.instantMode(false);
        backgroundColor(backgroundColor);
        animation = new TurtleAnimationController();
        dress = new TurtleDressController(this);
        pen = new TurtlePenController();
        stats = new TurtleStatisticsController(pen);
    }

    /* Hauptmethoden */

    /**
     * <b>Bewegt</b> die Schildkröte in Blickrichtung die angegebene Entfernung
     * nach <b>vorne</b>.
     *
     * @param distance Die <b>Entfernung</b> in Meter, die die Schildkröte
     *     zurücklegen soll. Negative Werte bewegen die Schildkröte rückwärts
     *     statt vorwärts.
     *
     * @see TurtleController#forward(double)
     * @see TurtleController#forward()
     * @see TurtleController#backward(double)
     * @see TurtleController#backward()
     * @see TurtleController#move()
     *
     * @since 0.38.0
     */
    void moveTurtleForward(double distance)
    {
        Vector movement = Vector.ofAngle(pen.direction).multiply(distance);
        Vector initial = pen.position;
        if (animation.warpMode)
        {
            lastPosition = pen.position;
            pen.position = initial.add(movement);
            drawLineInSurface(lastPosition, pen.position);
            // Bei sehr hohen Rekursionstiefen im Warp-Modus kommt die
            // Schildkröte nicht nach. Man sieht die Schildkröte doppelt. Wir
            // blenden sie deshalb aus.
            dress.hide();
        }
        else
        {
            dress.show();
            double duration = Math.abs(distance) / animation.speed;
            animate(duration, progress -> {
                lastPosition = pen.position;
                pen.position = initial.add(movement.multiply(progress));
                dress.position(pen.position);
                dress.showNextAnimation();
                drawLineInSurface(lastPosition, pen.position);
            });
        }
        stats.addTraveledDistance(distance);
    }

    private void drawLineInSurface(Vector from, Vector to)
    {
        if (!pen.isDown)
        {
            return;
        }
        if (surface == null)
        {
            surface = getPaintingSurface();
        }
        surface.drawLine(from, to, pen.color, pen.thickness);
    }

    @Setter
    private void currentDirection(double direction)
    {
        pen.direction = direction % 360;
    }

    private void doDirection(double direction)
    {
        currentDirection(direction);
        if (!animation.warpMode)
        {
            dress.direction(direction);
            // die Rotation kann den Mittelpunkt verschieben.
            dress.position(pen.position);
        }
    }

    /**
     * <b>Dreht</b> die Blickrichtung der Schildkröte.
     *
     * @param rotation Der <b>Drehwinkel</b> in Grad. Positive Werte drehen
     *     gegen den Uhrzeigersinn also nach links, negative Werte im
     *     Uhrzeigersinn also nach rechts.
     *
     * @see TurtleController#rotate(double)
     * @see TurtleController#left(double)
     */
    void rotateTurtle(double rotation)
    {
        double start = pen.direction;
        if (animation.warpMode)
        {
            doDirection(start + rotation);
            // Bei sehr hohen Rekursionstiefen im Warp-Modus kommt die
            // Schildkröte nicht nach. Man sieht die Schildkröte doppelt. Wir
            // blenden sie deshalb aus.
            dress.hide();
        }
        else
        {
            dress.show();
            double duration = Math.abs(rotation) / 360 / animation.speed;
            // * 4 damit man die Rotation auch sieht
            animate(duration * 4, progress -> {
                doDirection(start + progress * rotation);
            });
        }
    }

    /* Setter */

    @Setter
    void position(Vector position)
    {
        lastPosition = pen.position;
        pen.position = position;
        dress.position(position);
    }

    @Setter
    void direction(double direction)
    {
        currentDirection(direction);
        dress.direction(direction);
        // Unbedingt notwendig, da eine Rotation das Zentrum verändert
        dress.position(pen.position);
    }

    /**
     * Löscht den Hintergrund, d.h. alle bisher eingezeichneten Malspuren.
     *
     * @since 0.38.0
     */
    void clearBackground()
    {
        if (surface != null)
        {
            surface.clear();
        }
    }

    /**
     * Führt eine lineare Animation von 0 bis 1 über die angegebene Dauer aus
     * und ruft dabei den übergebenen Setter kontinuierlich mit dem aktuellen
     * Animationswert auf.
     *
     * <p>
     * Die Methode blockiert den aufrufenden Thread bis zum Abschluss der
     * Animation (sie wartet intern auf ein {@link CompletableFuture}).
     * </p>
     *
     * @param duration Dauer der Animation in Sekunden.
     * @param setter Callback, das mit dem aktuellen interpolierten Wert
     *     (zwischen 0.0 und 1.0) während der Animation sowie abschließend mit
     *     dem Endwert aufgerufen wird.
     *
     * @throws RuntimeException Falls der Thread unterbrochen wird oder im
     *     Animator eine Ausnahme auftritt.
     *
     * @since 0.38.0
     */
    private void animate(double duration, Consumer<Double> setter)
    {
        CompletableFuture<Void> future = new CompletableFuture<>();

        ValueAnimator<Double> animator = new ValueAnimator<>(duration, setter,
                new LinearDouble(0, 1), this);
        animator.addCompletionListener(value -> {
            setter.accept(value);
            future.complete(null);
        });

        addFrameUpdateListener(animator);

        try
        {
            future.get();
        }
        catch (InterruptedException | ExecutionException e)
        {
            throw new RuntimeException(e);
        }

    }

    public void renderOverlay(Graphics2D g, int width, int height)
    {
        stats.render(g);
    }
}
