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

import pi.Game;
import pi.Scene;
import pi.event.KeyStrokeListener;

/**
 * Die Szene enthält zwei Schläger, einen Ball und zwei unsichtbare
 * Abprallflächen.
 *
 * @author Josef Friedrich
 */
public class Pong extends Scene implements KeyStrokeListener
{
    Paddle paddleLeft;

    Paddle paddleRight;

    Ball ball;

    BounceBar topBorder;

    BounceBar bottomBorder;

    public Pong()
    {
        paddleLeft = new Paddle();
        paddleLeft.setPosition(-11.5, 0);

        paddleRight = new Paddle();
        paddleRight.setPosition(11, 0);

        ball = new Ball();
        ball.setCenter(0, 0);

        topBorder = new BounceBar();
        topBorder.setPosition(-15, 9);

        bottomBorder = new BounceBar();
        bottomBorder.setPosition(-15, -10);

        add(paddleLeft, paddleRight, ball, topBorder, bottomBorder);
    }

    @Override
    public void onKeyDown(KeyEvent event)
    {
        switch (event.getKeyCode())
        {
        case KeyEvent.VK_Q:
            paddleLeft.moveBy(0, 2);
            break;

        case KeyEvent.VK_A:
            paddleLeft.moveBy(0, -2);
            break;

        case KeyEvent.VK_UP:
            paddleRight.moveBy(0, 2);
            break;

        case KeyEvent.VK_DOWN:
            paddleRight.moveBy(0, -2);
            break;

        case KeyEvent.VK_ENTER:
            ball.applyImpulse(50, 100);
            break;

        default:
            break;
        }
    }

    public static void main(String[] args)
    {
        Game.start(new Pong());
    }
}
