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
import static de.pirckheimer_gymnasium.engine_pi.Resources.images;

import static de.pirckheimer_gymnasium.engine_pi.Vector.v;

import java.awt.Color;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Scene;
import de.pirckheimer_gymnasium.engine_pi.Vector;
import de.pirckheimer_gymnasium.engine_pi.actor.Actor;
import de.pirckheimer_gymnasium.engine_pi.actor.Animation;
import de.pirckheimer_gymnasium.engine_pi.actor.Polygon;
import de.pirckheimer_gymnasium.engine_pi.animation.ValueAnimator;
import de.pirckheimer_gymnasium.engine_pi.animation.interpolation.LinearDouble;
import de.pirckheimer_gymnasium.engine_pi.graphics.PaintingSurface;
import de.pirckheimer_gymnasium.engine_pi.util.ColorUtil;

/**
 * Eine <b>Schildkröte</b> um Turtle-Grafiken zu malen.
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
 * // Schönes Schildkröten-Logo: https://github.com/AmrDeveloper/Turtle
 *
 * @since 0.38.0
 *
 * @see <a href=
 *     "https://github.com/engine-alpha/engine-alpha/blob/4.x/engine-alpha-edu/src/main/java/ea/edu/turtle/Turtle.java">Turtle
 *     in der Engine-Alpha-Edu-Version</a>
 * @see <a href=
 *     "https://github.com/engine-pi/engine-pi/blob/main/modules/games/blockly-robot/src/main/java/de/pirckheimer_gymnasium/blockly_robot/robot/gui/robot/ImageRobot.java">ImageRobot
 *     in Blockly Robot</a>
 *
 * @author Josef Friedrich
 * @author Michael Andonie
 * @author Niklas Keller
 */
public class Turtle
{
    private final Scene scene;

    private Actor turtle;

    /**
     * Zeigt an, ob die Schildkröte momentatn den Stift gesenkt hat und zeichnet
     * oder nicht.
     */
    private boolean drawLine = true;

    /**
     * Die Geschwindigkeit mit der sich die Schildkröte bewegt (in Meter pro
     * Sekunde).
     */
    private double speed = 6;

    /**
     * Die Linienstärke in Pixel.
     */
    private double lineWidth = 1;

    /**
     * Die Farbe der Linie.
     */
    private Color lineColor = colors.get("yellow");

    private Color backgroundColor = ColorUtil
            .changeSaturation(colors.get("yellow"), 0.7);

    private double turtleSize = 2;

    private PaintingSurface surface;

    /**
     * Die aktuelle Position des Stifts. Diese Position wird bewegt und das
     * Zentrum der Figur wird auf diese Position gesetzt.
     *
     * <p>
     * Es reicht nicht, die Stiftposition über die Methode
     * {@link Actor#getCenter()} der Schildkrötenfigur zu bestimmen, denn bei
     * einer Rotation ändert sich das Zentrum.
     * </p>
     */
    private Vector currentPenPosition;

    /**
     * Das Zentrum der Schildkröte vor der Aktualisierung. Diese Position wird
     * verwendet, um die einzelnen Liniensegmente zu zeichnen.
     */
    private Vector lastPosition;

    public Turtle()
    {
        this(new Scene());
        if (!Game.isRunning())
        {
            Game.start(scene);
        }
        else
        {
            Game.transitionToScene(scene);
        }
    }

    public Turtle(Scene scene)
    {
        this.scene = scene;
        scene.setBackgroundColor(backgroundColor);
        currentPenPosition = new Vector(0, 0);
        setActor(true);
        scene.add(turtle);
    }

    /**
     * Ob die Schildkröte durch ein Bild gezeichnet werden soll. Falls falsch,
     * dann wird eine Dreieck gezeichnet.
     */
    public void setActor(boolean actorAsImage)
    {
        if (actorAsImage)
        {
            Animation animation = Animation.createFromImages(1 / speed,
                    turtleSize, turtleSize, images.get("turtle.png"),
                    images.get("turtle2.png"));
            animation.enableManualMode();
            turtle = animation;
        }
        else
        {
            turtle = new Polygon(v(-turtleSize / 4, turtleSize / 4),
                    v(turtleSize, 0), v(-turtleSize / 4, -turtleSize / 4));
            turtle.setColor("green");
        }
        turtle.setCenter(currentPenPosition);
    }

    /**
     * Die Geschwindigkeit mit der sich die Schildkröte bewegt (in Meter pro
     * Sekunde).
     */
    public void setSpeed(double speed)
    {
        this.speed = speed;
    }

    /**
     * Setzt die Linienstärke in Pixel
     *
     * @param lineWidth Die Linienstärke in Pixel.
     */
    public void setLineWidth(double lineWidth)
    {
        this.lineWidth = lineWidth;
    }

    /**
     * Setzt die Farbe der Linie als {@link Color}-Objekt.
     *
     * @param lineColor Die Farbe der Linie.
     */
    public void setLineColor(Color lineColor)
    {
        this.lineColor = lineColor;
    }

    /**
     * Setzt die Farbe der Linie als Zeichenkette.
     *
     * @param lineColor Die Farbe der Linie.
     */
    public void setLineColor(String lineColor)
    {
        this.lineColor = colors.get(lineColor);
    }

    /**
     * <b>Warte</b> die angegeben Anzahl an Sekunden.
     *
     * <p>
     * In der GraphicsAndGames-Engine des Cornelsen Verlags gibt es keine
     * ähnliche Methode, in der Engine Alpha {@code warte}.
     * </p>
     *
     * @param seconds Wie lange die Schildkröte warten soll.
     */
    public void wait(double seconds)
    {
        try
        {
            Thread.sleep((long) (1000 * seconds));
        }
        catch (InterruptedException e)
        {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * <b>Wechselt</b> in den Modus <em>„zeichnen“</em>.
     *
     * <p>
     * In der GraphicsAndGames-Engine des Cornelsen Verlags heißt die Methode
     * {@code StiftSenken}, in der Engine Alpha {@code ansetzen}.
     * </p>
     */
    public void lowerPen()
    {
        drawLine = true;
    }

    /**
     * <b>Wechselt</b> in den Modus <em>„nicht zeichnen“</em>.
     *
     * <p>
     * In der GraphicsAndGames-Engine des Cornelsen Verlags heißt die Methode
     * {@code StiftHeben}, in der Engine Alpha {@code absetzen}.
     * </p>
     */
    public void liftPen()
    {
        drawLine = false;
    }

    /**
     * <b>Bewegt</b> die Schildkröte nach vorne.
     *
     * <p>
     * In der GraphicsAndGames-Engine des Cornelsen Verlags heißt die Methode
     * {@code Gehen}, in der Engine Alpha {@code laufe}.
     * </p>
     *
     * @param distance Die Entfernung in Meter, die die Schildkröte zurück legen
     *     soll.
     */
    public void move(double distance)
    {
        Vector move = Vector.ofAngle(turtle.getRotation()).multiply(distance);
        Vector initial = currentPenPosition;
        double duration = distance / speed;
        animate(duration, progress -> {
            lastPosition = currentPenPosition;
            currentPenPosition = initial.add(move.multiply(progress));
            turtle.setCenter(currentPenPosition);
            if (turtle instanceof Animation)
            {
                Animation animation = (Animation) turtle;
                animation.showNext();
            }
            if (drawLine)
            {
                if (surface == null)
                {
                    surface = scene.getPaintingSurface();
                }
                surface.drawLine(lastPosition, turtle.getCenter(), lineColor,
                        lineWidth);
            }
        });
    }

    /**
     * <b>Dreht</b> die Schildkröte.
     *
     * <p>
     * In der GraphicsAndGames-Engine des Cornelsen Verlags heißt die Methode
     * {@code Drehen}, in der Engine Alpha {@code rotiere}.
     * </p>
     *
     * @param rotation Der Drehwinkel in Grad. Positive Werte drehen gegen den
     *     Uhrzeigersinn, negative Werte im Uhrzeigersinn.
     */
    public void rotate(double rotation)
    {
        double start = turtle.getRotation();
        double duration = Math.abs(rotation) / 360 / speed;
        // * 4 damit man die Rotation auch sieht
        animate(duration * 4, progress -> {
            turtle.setRotation(start + progress * rotation);
            turtle.setCenter(currentPenPosition);
        });
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
     * @param position Die bewünschte Position als Vektor in Meter.
     */
    public void setStartPosition(Vector position)
    {
        lastPosition = currentPenPosition;
        currentPenPosition = position;
        turtle.setCenter(position);
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
     * @param x Die x-Koordinate der gewünschten Position in Meter.
     * @param y Die y-Koordinate der gewünschten Position in Meter.
     */
    public void setStartPosition(double x, double y)
    {
        setStartPosition(new Vector(x, y));
    }

    /**
     * Setzt die <b>Blickrichtung</b> der Schildkröte.
     *
     * @param direction Die Blickrichtung der Schildkröte in Grad: 0°: nach
     *     rechts (Osten), 90°: nach oben (Norden) 180°: nach links (Westen)
     *     270°: nach unten (Süden)
     */
    public void setDirection(double direction)
    {
        turtle.setRotation(direction);
        // Unbedingt notwendig, da eine Rotation das Zentrum verändert
        turtle.setCenter(currentPenPosition);
    }

    /**
     * Löscht den Hintergrund, d.h. alle bisher eingezeichneten Malspuren.
     */
    public void clearBackground()
    {
        if (surface != null)
        {
            surface.fill(backgroundColor);
        }
    }

    /**
     * Blenden die Schildkröte aus.
     */
    public void hide()
    {
        turtle.hide();
    }

    /**
     * Blendet die Schildkröte ein.
     */
    public void show()
    {
        turtle.show();
    }

    private void animate(double duration, Consumer<Double> setter)
    {
        CompletableFuture<Void> future = new CompletableFuture<>();

        ValueAnimator<Double> animator = new ValueAnimator<>(duration, setter,
                new LinearDouble(0, 1), turtle);
        animator.addCompletionListener(value -> {
            setter.accept(value);
            future.complete(null);
        });

        turtle.addFrameUpdateListener(animator);

        try
        {
            future.get();
        }
        catch (InterruptedException | ExecutionException e)
        {
            throw new RuntimeException(e);
        }
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
