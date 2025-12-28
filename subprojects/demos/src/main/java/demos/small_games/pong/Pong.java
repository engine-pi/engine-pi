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
    private final Bounds area;

    public Pong()
    {
        area = getVisibleArea();

        double PADDLE_X_PADDING = 1;

        paddleLeft = new Paddle();
        paddleLeft.setPosition(area.xLeft() + PADDLE_X_PADDING, 0);

        paddleRight = new Paddle();
        paddleRight.setPosition(
                area.xRight() - PADDLE_X_PADDING - paddleRight.getWidth(), 0);

        ball = new Ball();
        ball.setCenter(0, 0);

        topBouncer = new BounceBar(area.width()).debug();
        topBouncer.setPosition(area.xLeft(), area.yTop());

        bottomBouncer = new BounceBar(area.width()).debug();
        bottomBouncer.setPosition(area.xLeft(),
                area.yBottom() - bottomBouncer.getHeight());

        add(paddleLeft, paddleRight, ball, topBouncer, bottomBouncer);
    }

    @Override
    public void onKeyDown(KeyEvent event)
    {
        switch (event.getKeyCode())
        {
        // Steuerung für den linken Schläger
        case KeyEvent.VK_Q:
            paddleLeft.moveUp();
            break;

        case KeyEvent.VK_A:
            paddleLeft.moveDown();
            break;

        // Steuerung für den rechten Schläger
        case KeyEvent.VK_UP:
            paddleRight.moveUp();
            break;

        case KeyEvent.VK_DOWN:
            paddleRight.moveDown();
            break;

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
