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

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Vector;
import de.pirckheimer_gymnasium.engine_pi.animation.ValueAnimator;
import de.pirckheimer_gymnasium.engine_pi.animation.interpolation.LinearDouble;
import de.pirckheimer_gymnasium.engine_pi.graphics.PaintingSurface;
import de.pirckheimer_gymnasium.engine_pi.graphics.boxes.FramedTextBox;
import de.pirckheimer_gymnasium.engine_pi.util.ColorUtil;

/*
 * Implementation so ähnlich wie <a href="https://github.com/engine-pi/engine-pi/blob/main/modules/games/blockly-robot/src/main/java/de/pirckheimer_gymnasium/blockly_robot/robot/gui/robot/ImageRobot.java">ImageRobot
 * </a>in Blockly Robot
 */

/**
 * Eine <b>Schildkröte</b> um Turtle-Grafiken zu malen.
 *
 * <h2>Die Hauptmethoden:</h2>
 *
 * <ul>
 * <li>{@link #move()}: Bewegt die Schildkröte in Blickrichtung nach vorne.</li>
 * <li>{@link #rotate(double)}: Dreht die Schildkröte.</li>
 * <li>{@link #lowerPen()}: Wechselt in den Modus „zeichnen“.</li>
 * <li>{@link #liftPen()}: Wechselt in den Modus „nicht zeichnen“.</li>
 * </ul>
 *
 * <p>
 * Folgendes Code-Beispiel demonstiert, wie mit <b>minimalen</b>
 * Programmieraufwand eine <b>Turtle</b>-Grafik (hier ein Dreieck) gezeichnet
 * werden kann:
 * </p>
 *
 * <pre>{@code
 * import de.pirckheimer_gymnasium.engine_pi.dsa.turtle.Turtle;
 *
 * public class MinimalTurtleDemo
 * {
 *     public static void main(String[] args)
 *     {
 *         Turtle turtle = new Turtle();
 *         turtle.move();
 *         turtle.rotate(120);
 *         turtle.move();
 *         turtle.rotate(120);
 *         turtle.move();
 *     }
 * }
 * }</pre>
 *
 * <p>
 * Die Klasse hat animierte Methoden, die künstliche verlangsamt werden, damit
 * der Malprozess nachvollzogen werden kann. Das Zeichnen einer Turtle-Grafik
 * kann unter Umständen sehr lange dauern. Deshalb sollten keine animierten
 * Methodenaufrufen in Konstrukturen geschrieben werden, da das Objekt lange
 * nicht erzeugt werden kann. Diese Klasse startet deshalb automatische eine
 * Szene.
 * </p>
 *
 * @since 0.38.0
 *
 * @author Josef Friedrich
 * @author Michael Andonie
 * @author Niklas Keller
 */
public class Turtle extends PaintingSurfaceScene
{
    /**
     * Die <b>graphische Repräsentation</b> der Schildkröte.
     */
    private TurtleImage turtleImage;

    /**
     * Die <b>Geschwindigkeit</b>, mit der sich die Schildkröte bewegt (in Meter
     * pro Sekunde).
     */
    private double speed = 6;

    /**
     * Die Hintergrundfarbe der Zeichenfläche.
     */
    private Color backgroundColor = ColorUtil
            .changeSaturation(colors.get("yellow"), 0.7);

    /**
     * Die Malfläche, in die die Schildkröte zeichnet.
     */
    private PaintingSurface surface;

    /**
     * Das Zentrum der Schildkröte vor der Aktualisierung. Diese Position wird
     * verwendet, um die einzelnen Liniensegmente zu zeichnen.
     */
    private Vector lastPosition;

    /**
     * Im sogenannte Warp-Modus finden keine Animationen statt. Die
     * Turtle-Grafik wird so schnell wie möglich gezeichnet.
     */
    private boolean warpMode = false;

    private double traveledDistance;

    private FramedTextBox statistics;

    public final TurtlePen pen;

    /**
     * Erzeugt neue eine Schildkröte in einem <b>neuen Fenster</b>.
     *
     * @since 0.38.0
     */
    public Turtle()
    {
        this(true);
    }

    /**
     * Erzeugt eine Schildkröte in einer <b>gegebenen Szene</b>.
     *
     * @param autoStart Falls wahr, dann wird das das Fenster <b>automatisch
     *     gestartet</b>.
     *
     * @since 0.38.0
     */
    public Turtle(boolean autoStart)
    {
        setBackgroundColor(backgroundColor);
        pen = new TurtlePen();
        turtleImage = new TurtleImage(this);

        statistics = new FramedTextBox(String.valueOf(traveledDistance))
                .borderThickness(0).backgroundColor(colors.get("grey", 50))
                .textColor(colors.get("black"));
        statistics.anchor(20, 20);

        if (autoStart)
        {
            Game.startSafe(this);
        }
    }

    /* Hauptmethoden */

    /**
     * <b>Bewegt</b> die Schildkröte in Blickrichtung 3 Meter nach vorne.
     *
     * @since 0.38.0
     */
    public void move()
    {
        move(3);
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
    public void move(double distance)
    {
        // Vielleicht wäre es besser die Rotation auch extra zu speichern wie
        // bei currentPenPosition und nicht aus der Grafik raus zu lesen
        Vector movement = Vector.ofAngle(pen.direction).multiply(distance);
        Vector initial = pen.position;
        if (warpMode)
        {
            lastPosition = pen.position;
            pen.position = initial.add(movement);
            drawLineInSurface(lastPosition, pen.position);
        }
        else
        {
            double duration = distance / speed;
            animate(duration, progress -> {
                lastPosition = pen.position;
                pen.position = initial.add(movement.multiply(progress));
                turtleImage.setPosition(pen.position);
                turtleImage.showNextAnimation();
                drawLineInSurface(lastPosition, pen.position);
            });
        }
        traveledDistance += distance;
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
        turtleImage.setRotation(rotation);
        // die Rotation kann den Mittelpunkt verschieben.
        turtleImage.setPosition(pen.position);
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
    public void rotate(double rotation)
    {
        double start = pen.direction;
        if (warpMode)
        {
            doRotation(start + rotation);
        }
        else
        {
            double duration = Math.abs(rotation) / 360 / speed;
            // * 4 damit man die Rotation auch sieht
            animate(duration * 4, progress -> {
                doRotation(start + progress * rotation);
            });
        }
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
        pen.isDown = true;
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
        pen.isDown = false;
    }

    /**
     * @since 0.40.0
     */
    public void setPen(boolean isDown)
    {
        pen.isDown = isDown;
    }

    /* Setter */

    /**
     * Setzt die <b>Geschwindigkeit</b>, mit der sich die Schildkröte bewegt (in
     * Meter pro Sekunde).
     *
     * @param speed Die <b>Geschwindigkeit</b>, mit der sich die Schildkröte
     *     bewegt (in Meter pro Sekunde).
     *
     * @since 0.38.0
     */
    public void setSpeed(double speed)
    {
        this.speed = speed;
    }

    /**
     * <b>Ändert</b> die aktuelle <b>Geschwindigkeit</b> um den angegebenen
     * Wert.
     *
     * Positive Werte erhöhen die Geschwindigkeit, negative Werte verringern
     * sie. Führt die geplante Änderung dazu, dass die Geschwindigkeit negativ
     * würde, so wird die Änderung verworfen und die Geschwindigkeit bleibt
     * unverändert.
     *
     * @param speedChange Der Betrag, um den die Geschwindigkeit erhöht
     *     (positiv) oder verringert (negativ) werden soll.
     *
     * @since 0.38.0
     */
    public void changeSpeed(double speedChange)
    {
        if (speed + speedChange < 0)
        {
            return;
        }
        speed += speedChange;
    }

    /**
     * Schaltet in den sogenannten „<b>Warp-Modus</b>“.
     *
     * <b>Im Warp-Modus finden keine Animationen statt. Die Turtle-Grafik wird
     * so schnell wie möglich gezeichnet.</b>
     *
     * @since 0.38.0
     */
    public void enableWarpMode()
    {
        warpMode = true;
    }

    /**
     * Schaltet den sogenannten „<b>Warp-Modus</b>“ <b>an oder aus</b>.
     *
     * <b>Im Warp-Modus finden keine Animationen statt. Die Turtle-Grafik wird
     * so schnell wie möglich gezeichnet.</b>
     *
     * @since 0.38.0
     */
    public void toggleWarpMode()
    {
        warpMode = !warpMode;
    }

    /**
     * Setzt den Zustand des sogenannten „<b>Warp-Modus</b>“.
     *
     * <b>Im Warp-Modus finden keine Animationen statt. Die Turtle-Grafik wird
     * so schnell wie möglich gezeichnet.</b>
     *
     * @param warpMode Der Warp-Modulus wird angeschaltet, falls der Wert wahr
     *     ist. Er wird ausgeschaltet, falls der Wert falsch ist.
     *
     * @since 0.40.0
     */
    public void setWarpMode(boolean warpMode)
    {
        this.warpMode = warpMode;
    }

    /**
     * Setzt die <b>Linienstärke</b> in Pixel
     *
     * @param lineWidth Die Linienstärke in Pixel.
     *
     * @since 0.38.0
     */
    public void setLineWidth(int lineWidth)
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
    public void changeLineWidth(int lineWidthChange)
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
    public void setLineColor(Color lineColor)
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
    public void setLineColor(String lineColor)
    {
        pen.color = colors.get(lineColor);
    }

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
    public void setPosition(Vector position)
    {
        lastPosition = pen.position;
        pen.position = position;
        turtleImage.setPosition(position);
    }

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
    public void setPosition(double x, double y)
    {
        setPosition(new Vector(x, y));
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
        setCurrentRotation(direction);
        turtleImage.setRotation(direction);
        // Unbedingt notwendig, da eine Rotation das Zentrum verändert
        turtleImage.setPosition(pen.position);
    }

    public void setDress(TurtleDress dress)
    {
        turtleImage.setDress(dress);
    }

    public void setNextDress()
    {
        turtleImage.setNextDress();
    }

    /**
     * Löscht den Hintergrund, d.h. alle bisher eingezeichneten Malspuren.
     *
     * @since 0.38.0
     */
    public void clearBackground()
    {
        if (surface != null)
        {
            surface.fill(backgroundColor);
        }
    }

    /**
     * <b>Blendet</b> die Schildkröte <b>aus</b>.
     *
     * @since 0.38.0
     */
    public void hide()
    {
        turtleImage.hide();
    }

    /**
     * <b>Blendet</b> die Schildkröte <b>ein</b>.
     *
     * @since 0.38.0
     */
    public void show()
    {
        turtleImage.show();
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
        statistics.content(String.valueOf(traveledDistance));
        statistics.measure();
        statistics.render(g);
    }

    public static void main(String[] args)
    {
        Turtle turtle = new Turtle();
        turtle.setSpeed(1);

        for (int i = 0; i < 3; i++)
        {
            turtle.lowerPen();
            turtle.move(3);
            turtle.rotate(120);
        }
    }
}
