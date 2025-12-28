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
package demos.small_games.pong;

import java.awt.event.KeyEvent;

import pi.Bounds;
import pi.Game;
import pi.Random;
import pi.Scene;
import pi.Vector;
import pi.event.FrameUpdateListener;
import pi.event.KeyStrokeListener;
import pi.event.PressedKeyRepeater;

/**
 * Der Ping-Pong-Tisch enthält zwei Schläger, einen Ball und zwei unsichtbare
 * Abprallflächen.
 *
 * @author Josef Friedrich
 */
public class Table extends Scene
        implements KeyStrokeListener, FrameUpdateListener
{
    /**
     * Die linke Tischhälfte.
     */
    private final TableSide left;

    /**
     * Die rechte Tischhälfte.
     */
    private final TableSide right;

    /**
     * Der Ball.
     */
    private final Ball ball;

    /**
     * Der obere unsichtbare Abprallbalken.
     */
    private final BounceBar topBouncer;

    /**
     * Der untere unsichtbare Abprallbalken.
     */
    private final BounceBar bottomBouncer;

    /**
     * Die sichtbare Fläche der Szene in Meter.
     */
    final Bounds bounds;

    /**
     * Damit man die Schläger mit gedrückter Taste bewegen kann und nicht
     * mehrmals auf einen Taste drücken muss, bis die Schläger an die bewünschte
     * Position gelangen.
     */
    private PressedKeyRepeater repeater;

    public Table()
    {
        bounds = getVisibleArea();

        left = new TableSide(-1, this);
        right = new TableSide(1, this);

        ball = new Ball();
        ball.setCenter(0, 0);

        topBouncer = new BounceBar(bounds.width());
        topBouncer.setPosition(bounds.xLeft(), bounds.yTop());

        bottomBouncer = new BounceBar(bounds.width());
        bottomBouncer.setPosition(bounds.xLeft(),
                bounds.yBottom() - bottomBouncer.getHeight());

        add(ball, topBouncer, bottomBouncer);

        repeater = new PressedKeyRepeater();

        // Steuerung für den linken Schläger
        repeater.addListener(KeyEvent.VK_Q, () -> left.movePaddleUp());
        repeater.addListener(KeyEvent.VK_A, () -> left.movePaddleDown());

        // Steuerung für den rechten Schläger
        repeater.addListener(KeyEvent.VK_UP, () -> right.movePaddleUp());
        repeater.addListener(KeyEvent.VK_DOWN, () -> right.movePaddleDown());
    }

    /**
     * Wendet einen zufälligen Startimpuls auf den Ball an.
     *
     * <p>
     * Der Ball wird zuerst in den Ruhezustand versetzt, dann auf die Position
     * 0|0 und schießlich in eine zufällige Richtung geschleudert.
     * </p>
     */
    public void applyImpulseToBall()
    {
        ball.resetMovement();
        ball.setCenter(0, 0);
        ball.applyImpulse(
                Vector.ofAngle(Random.range(0.0, 360.0)).multiply(100));
    }

    @Override
    public void onKeyDown(KeyEvent event)
    {
        switch (event.getKeyCode())
        {
        case KeyEvent.VK_ENTER:
            applyImpulseToBall();
            break;

        default:
            break;
        }
    }

    @Override
    public void onFrameUpdate(double pastTime)
    {
        double x = ball.getCenter().getX();
        if (x < bounds.xLeft())
        {
            left.increaseScore();
            applyImpulseToBall();
        }

        else if (x > bounds.xRight())
        {
            right.increaseScore();
            applyImpulseToBall();
        }
    }

    public static void main(String[] args)
    {
        Game.start(new Table());
    }
}
