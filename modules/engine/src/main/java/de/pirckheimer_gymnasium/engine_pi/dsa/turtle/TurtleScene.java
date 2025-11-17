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
package de.pirckheimer_gymnasium.engine_pi.dsa.turtle;

import static de.pirckheimer_gymnasium.engine_pi.Resources.colors;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;

import de.pirckheimer_gymnasium.engine_pi.Vector;
import de.pirckheimer_gymnasium.engine_pi.animation.ValueAnimator;
import de.pirckheimer_gymnasium.engine_pi.animation.interpolation.LinearDouble;
import de.pirckheimer_gymnasium.engine_pi.graphics.PaintingSurface;
import de.pirckheimer_gymnasium.engine_pi.util.ColorUtil;

/*
 * Implementation so ähnlich wie <a href="https://github.com/engine-pi/engine-pi/blob/main/modules/games/blockly-robot/src/main/java/de/pirckheimer_gymnasium/blockly_robot/robot/gui/robot/ImageRobot.java">ImageRobot
 * </a>in Blockly Robot
 */

// Go to file: file:///home/jf/repos/school/monorepo/inf/java/engine-pi/modules/demos/src/main/java/de/pirckheimer_gymnasium/demos/classes/dsa/turtle/TurtleDemo.java

/**
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
     * Die <b>graphische Repräsentation</b> der Schildkröte.
     */
    final TurtleDressController dress;

    final TurtlePenController pen;

    private final TurtleStatistics statistics;

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
        setBackgroundColor(backgroundColor);
        animation = new TurtleAnimationController();
        dress = new TurtleDressController(this);
        pen = new TurtlePenController();
        statistics = new TurtleStatistics();
    }

    /* Hauptmethoden */

    void moveTurtleForward(double distance)
    {
        // Vielleicht wäre es besser die Rotation auch extra zu speichern wie
        // bei currentPenPosition und nicht aus der Grafik raus zu lesen
        Vector movement = Vector.ofAngle(pen.direction).multiply(distance);
        Vector initial = pen.position;
        if (animation.warpMode)
        {
            lastPosition = pen.position;
            pen.position = initial.add(movement);
            drawLineInSurface(lastPosition, pen.position);
        }
        else
        {
            double duration = Math.abs(distance) / animation.speed;
            animate(duration, progress -> {
                lastPosition = pen.position;
                pen.position = initial.add(movement.multiply(progress));
                dress.setPosition(pen.position);
                dress.showNextAnimation();
                drawLineInSurface(lastPosition, pen.position);
            });
        }
        statistics.addTraveledDistance(distance);
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

    private void setCurrentRotation(double rotation)
    {
        pen.direction = rotation % 360;

    }

    private void doRotation(double rotation)
    {
        setCurrentRotation(rotation);
        dress.setRotation(rotation);
        // die Rotation kann den Mittelpunkt verschieben.
        dress.setPosition(pen.position);
    }

    void rotateTurtle(double rotation)
    {
        double start = pen.direction;
        if (animation.warpMode)
        {
            doRotation(start + rotation);
        }
        else
        {
            double duration = Math.abs(rotation) / 360 / animation.speed;
            // * 4 damit man die Rotation auch sieht
            animate(duration * 4, progress -> {
                doRotation(start + progress * rotation);
            });
        }
    }

    /**
     * @since 0.40.0
     */
    void setPen(boolean isDown)
    {
        pen.isDown = isDown;
    }

    /* Setter */

    /**
     * Setzt die <b>Linienstärke</b> in Pixel
     *
     * @param lineWidth Die Linienstärke in Pixel.
     *
     * @since 0.38.0
     */
    void setLineWidth(int lineWidth)
    {
        pen.thickness = lineWidth;
    }

    /**
     * <b>Ändert</b> die aktuelle <b>Linienstärke</b> um einen gegeben Wert.
     *
     * Positive Werte erhöhen die Linienstärke, negative Werte verringern sie.
     * Führt die Änderung zu einer negativen Linienstärke, wird die Änderung
     * verworfen und der vorhandene Wert bleibt unverändert.
     *
     * @param lineWidthChange Differenz der Linienstärke (positiv zum Erhöhen,
     *     negativ zum Verringern); wird ignoriert, wenn die resultierende
     *     Linienstärke negativ wäre.
     *
     * @since 0.38.0
     */
    void changeLineWidth(int lineWidthChange)
    {
        if (pen.thickness + lineWidthChange < 1)
        {
            return;
        }
        pen.thickness += lineWidthChange;
    }

    /**
     * Setzt die Farbe der Linie als {@link Color}-Objekt.
     *
     * @param lineColor Die Farbe der Linie.
     *
     * @since 0.38.0
     */
    void setLineColor(Color lineColor)
    {
        pen.color = lineColor;
    }

    /**
     * Setzt die Farbe der Linie als Zeichenkette.
     *
     * @param lineColor Die Farbe der Linie.
     *
     * @since 0.38.0
     */
    void setLineColor(String lineColor)
    {
        pen.color = colors.get(lineColor);
    }

    void setPosition(Vector position)
    {
        lastPosition = pen.position;
        pen.position = position;
        dress.setPosition(position);
    }

    void setDirection(double direction)
    {
        setCurrentRotation(direction);
        dress.setRotation(direction);
        // Unbedingt notwendig, da eine Rotation das Zentrum verändert
        dress.setPosition(pen.position);
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
     * Animation (sie wartet intern auf ein {@link CompletableFuture})..
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
        statistics.render(g);
    }

}
