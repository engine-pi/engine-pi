/*
 * Source: https://github.com/engine-alpha/tutorials/blob/master/src/eatutorials//.java
 *
 * Engine Alpha ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2011 - 2024 Michael Andonie and contributors.
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
package rocks.friedrich.engine_omega.tutorials.gameloop;

import java.awt.Color;
import java.awt.event.KeyEvent;

import rocks.friedrich.engine_omega.Game;
import rocks.friedrich.engine_omega.Random;
import rocks.friedrich.engine_omega.Scene;
import rocks.friedrich.engine_omega.Vector;
import rocks.friedrich.engine_omega.actor.Circle;
import rocks.friedrich.engine_omega.actor.Text;
import rocks.friedrich.engine_omega.event.CollisionEvent;
import rocks.friedrich.engine_omega.event.CollisionListener;
import rocks.friedrich.engine_omega.event.FrameUpdateListener;
import rocks.friedrich.engine_omega.tutorials.util.Util;

public class ActualSnake extends Scene implements FrameUpdateListener
{
    private Text scoreText = new Text("Score: 0", 1.4);

    private int score = 0;

    private Snake snakeHead = new Snake();

    private double snake_speed = 5;

    private boolean makeNewHead = false;

    public ActualSnake()
    {
        add(snakeHead);
        scoreText.setPosition(-9, 5);
        add(scoreText);
        placeRandomGoodie();
    }

    public void setScore(int score)
    {
        this.score = score;
        this.snake_speed = 5 + (score * 0.1);
        this.scoreText.setContent("Score: " + score);
    }

    public void increaseScore()
    {
        setScore(score + 1);
    }

    public void placeRandomGoodie()
    {
        double x = Random.range() * 10 - 5;
        double y = Random.range() * 10 - 5;
        Goodie goodie = new Goodie();
        goodie.setCenter(x, y);
        add(goodie);
        goodie.addCollisionListener(snakeHead, goodie);
    }

    @Override
    public void onFrameUpdate(double timeInS)
    {
        double dX = 0, dY = 0;
        if (Game.isKeyPressed(KeyEvent.VK_W))
        {
            dY = snake_speed * timeInS;
        }
        if (Game.isKeyPressed(KeyEvent.VK_A))
        {
            dX = -snake_speed * timeInS;
        }
        if (Game.isKeyPressed(KeyEvent.VK_S))
        {
            dY = -snake_speed * timeInS;
        }
        if (Game.isKeyPressed(KeyEvent.VK_D))
        {
            dX = snake_speed * timeInS;
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
            snakeHead.snakeHeadMove(dX, dY);
        }
    }

    private class Snake extends Circle
    {
        private Snake next = null;

        public Snake()
        {
            super(1);
            setColor(Color.GREEN);
        }

        private void snakeHeadMove(double dX, double dY)
        {
            Vector mycenter = getCenter();
            moveBy(dX, dY);
            if (next != null)
            {
                next.snakeChildrenMove(mycenter);
            }
        }

        private void snakeChildrenMove(Vector newCenter)
        {
            Vector mycenter = getCenter();
            setCenter(newCenter);
            if (next != null)
            {
                next.snakeChildrenMove(mycenter);
            }
        }
    }

    private class Goodie extends Text implements CollisionListener<Snake>
    {
        public Goodie()
        {
            super("Eat Me!", 1);
            setColor(Color.RED);
        }

        @Override
        public void onCollision(CollisionEvent<Snake> collisionEvent)
        {
            increaseScore();
            makeNewHead = true;
            this.remove();
            placeRandomGoodie();
        }
    }

    public static void main(String[] args)
    {
        Game.start(600, 400, new ActualSnake());
        Util.addScreenshotKey("Game Loop Actual Snake");
    }
}
