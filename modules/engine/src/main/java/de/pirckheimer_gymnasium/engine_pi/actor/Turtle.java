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
package de.pirckheimer_gymnasium.engine_pi.actor;

import static de.pirckheimer_gymnasium.engine_pi.Vector.v;

import java.awt.Color;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Scene;
import de.pirckheimer_gymnasium.engine_pi.Vector;
import de.pirckheimer_gymnasium.engine_pi.animation.ValueAnimator;
import de.pirckheimer_gymnasium.engine_pi.animation.interpolation.LinearDouble;

/**
 * @since 0.38.0
 *
 * @see <a href=
 *     "https://github.com/engine-alpha/engine-alpha/blob/4.x/engine-alpha-edu/src/main/java/ea/edu/turtle/Turtle.java">Turtle
 *     in der Engine-Alpha-Edu-Version</a>
 * @see <a href=
 *     "https://github.com/engine-pi/engine-pi/blob/main/modules/games/blockly-robot/src/main/java/de/pirckheimer_gymnasium/blockly_robot/robot/gui/robot/ImageRobot.java">ImageRobot
 *     in Blockly Robot</a>
 */
public class Turtle
{

    private final Scene scene;

    private final Polygon turtle;

    private boolean drawLine;

    private Color lineColor = Color.BLACK;

    private double speed = 3;

    public Turtle()
    {
        scene = new Scene();
        scene.setBackgroundColor(new Color(240, 240, 240));

        turtle = new Polygon(v(-0.5, 0.5), v(1, 0), v(-0.5, -0.5));
        turtle.setCenter(0, 0);
        turtle.setColor(Color.RED);

        scene.add(turtle);

        if (!Game.isRunning())
        {
            Game.start(scene);
        }
        else
        {
            Game.transitionToScene(scene);
        }

        // scene.getCamera().setFocus(turtle);
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
        Vector initial = turtle.getCenter();

        double duration = distance / speed;

        AtomicReference<Rectangle> line = new AtomicReference<>();

        animate(duration, progress -> {
            turtle.setCenter(initial.add(move.multiply(progress)));

            if (drawLine)
            {
                if (line.get() != null)
                {
                    line.get().remove();
                }

                line.set(new Rectangle(distance * progress, 0.1));
                line.get().setRotation(turtle.getRotation());
                line.get().setCenter(turtle.getCenter()
                        .subtract(move.multiply(progress * 0.5)));
                line.get().setColor(lineColor);
                line.get().setBorderRadius(1);
                line.get().setBodyType(BodyType.PARTICLE);

                scene.add(line.get());
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
     */
    public void rotate(double rotation)
    {
        Vector center = turtle.getCenter();
        double start = turtle.getRotation();
        double duration = rotation / 360 / speed;

        animate(duration, progress -> {
            turtle.setRotation(start + progress * rotation);
            turtle.setCenter(center);
        });
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

        for (int i = 0; i < 4; i++)
        {
            turtle.lowerPen();
            turtle.move(3);
            turtle.rotate(90);
        }
    }
}
