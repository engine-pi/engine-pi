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
import pi.Scene;
import pi.event.FrameUpdateListener;
import pi.event.KeyStrokeListener;
import pi.event.PressedKeyRepeater;

/**
 * Die Szene enthält zwei Schläger, einen Ball und zwei unsichtbare
 * Abprallflächen.
 *
 * @author Josef Friedrich
 */
public class Pong extends Scene
        implements KeyStrokeListener, FrameUpdateListener
{
    /**
     * Der linke Schläger.
     */
    private final Paddle paddleLeft;

    /**
     * Der rechte Schläger
     */
    private final Paddle paddleRight;

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
    private final Bounds bounds;

    /**
     * Damit man die Schläger mit gedrückter Taste bewegen kann und nicht
     * mehrmals auf einen Taste drücken muss, bis die Schläger an die bewünschte
     * Position gelangen.
     */
    private PressedKeyRepeater repeater;

    public Pong()
    {
        bounds = getVisibleArea();

        paddleLeft = new Paddle(Side.LEFT, bounds);
        paddleRight = new Paddle(Side.RIGHT, bounds);

        ball = new Ball();
        ball.setCenter(0, 0);

        topBouncer = new BounceBar(bounds.width());
        topBouncer.setPosition(bounds.xLeft(), bounds.yTop());

        bottomBouncer = new BounceBar(bounds.width());
        bottomBouncer.setPosition(bounds.xLeft(),
                bounds.yBottom() - bottomBouncer.getHeight());

        add(paddleLeft, paddleRight, ball, topBouncer, bottomBouncer);

        repeater = new PressedKeyRepeater();

        // Steuerung für den linken Schläger
        repeater.addListener(KeyEvent.VK_Q, () -> paddleLeft.moveUp());
        repeater.addListener(KeyEvent.VK_A, () -> paddleLeft.moveDown());

        // Steuerung für den rechten Schläger
        repeater.addListener(KeyEvent.VK_UP, () -> paddleRight.moveUp());
        repeater.addListener(KeyEvent.VK_DOWN, () -> paddleRight.moveDown());
    }

    @Override
    public void onKeyDown(KeyEvent event)
    {
        switch (event.getKeyCode())
        {
        case KeyEvent.VK_ENTER:
            ball.applyImpulse(50, 100);
            break;

        default:
            break;
        }
    }

    @Override
    public void onFrameUpdate(double pastTime)
    {
    }

    public static void main(String[] args)
    {
        Game.start(new Pong());
    }
}
