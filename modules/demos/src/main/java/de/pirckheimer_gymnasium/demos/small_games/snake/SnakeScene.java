/*
 * Nach: https://github.com/engine-alpha/tutorials/blob/master/src/eatutorials/gameloop/ActualSnake.java
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
package de.pirckheimer_gymnasium.demos.small_games.snake;

import java.awt.event.KeyEvent;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Random;
import de.pirckheimer_gymnasium.engine_pi.Scene;
import de.pirckheimer_gymnasium.engine_pi.Vector;
import de.pirckheimer_gymnasium.engine_pi.actor.Text;
import de.pirckheimer_gymnasium.engine_pi.event.FrameUpdateListener;

public class SnakeScene extends Scene implements FrameUpdateListener
{
    private Text scoreText = new Text("Score: 0", 1.4);

    private int score = 0;

    private Snake snakeHead = new Snake();

    private double snakeSpeed = 5;

    boolean makeNewHead = false;

    double dX = 0;

    double dY = 0;

    public SnakeScene()
    {
        add(snakeHead);
        scoreText.setPosition(-9, 5);
        add(scoreText);
        placeRandomGoodie();
    }

    public void setScore(int score)
    {
        this.score = score;
        snakeSpeed = 5 + (score * 0.1);
        scoreText.setContent("Score: " + score);
    }

    public void increaseScore()
    {
        setScore(score + 1);
    }

    public void placeRandomGoodie()
    {
        double x = Random.range() * 10 - 5;
        double y = Random.range() * 10 - 5;
        Goodie goodie = new Goodie(this);
        goodie.setCenter(x, y);
        add(goodie);
        goodie.addCollisionListener(snakeHead, goodie);
    }

    @Override
    public void onFrameUpdate(double timeInS)
    {
        if (Game.isKeyPressed(KeyEvent.VK_W))
        {
            dY = snakeSpeed * timeInS;
            dX = 0;
        }
        if (Game.isKeyPressed(KeyEvent.VK_A))
        {
            dX = -snakeSpeed * timeInS;
            dY = 0;
        }
        if (Game.isKeyPressed(KeyEvent.VK_S))
        {
            dY = -snakeSpeed * timeInS;
            dX = 0;
        }
        if (Game.isKeyPressed(KeyEvent.VK_D))
        {
            dX = snakeSpeed * timeInS;
            dY = 0;
        }
        if (makeNewHead)
        {
            Snake newHead = new Snake();
            newHead.setCenter(snakeHead.getCenter());
            newHead.next = snakeHead;
            newHead.moveBy(new Vector(dX, dY).multiply(1));
            add(newHead);
            snakeHead = newHead;
            makeNewHead = false;
        }
        else if (dX != 0 || dY != 0)
        {
            snakeHead.moveHead(dX, dY);
        }
    }

    public static void main(String[] args)
    {
        Game.start(new SnakeScene(), 600, 400);
    }
}
