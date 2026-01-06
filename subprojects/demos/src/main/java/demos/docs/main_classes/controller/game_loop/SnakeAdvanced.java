/*
 * Source: https://github.com/engine-alpha/tutorials/blob/master/src/eatutorials/gameloop/ActualSnake.java
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
package demos.docs.main_classes.controller.game_loop;

import java.awt.event.KeyEvent;

import pi.Controller;
import pi.Random;
import pi.Scene;
import pi.Vector;
import pi.Circle;
import pi.Text;
import pi.event.CollisionEvent;
import pi.event.CollisionListener;
import pi.event.FrameUpdateListener;

// Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/docs/manual/main-classes/controller/game-loop.md

public class SnakeAdvanced extends Scene implements FrameUpdateListener
{
    private Text scoreText = new Text("Score: 0", 1.4);

    private int score = 0;

    private Snake snakeHead = new Snake();

    private double snakeSpeed = 5;

    private boolean makeNewHead = false;

    public SnakeAdvanced()
    {
        add(snakeHead);
        scoreText.position(-9, 5);
        add(scoreText);
        placeRandomGoodie();
    }

    public void setScore(int score)
    {
        this.score = score;
        snakeSpeed = 5 + (score * 0.1);
        scoreText.content("Score: " + score);
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
        goodie.center(x, y);
        add(goodie);
        goodie.addCollisionListener(snakeHead, goodie);
    }

    @Override
    public void onFrameUpdate(double pastTime)
    {
        double dX = 0, dY = 0;
        if (Controller.isKeyPressed(KeyEvent.VK_W))
        {
            dY = snakeSpeed * pastTime;
        }
        if (Controller.isKeyPressed(KeyEvent.VK_A))
        {
            dX = -snakeSpeed * pastTime;
        }
        if (Controller.isKeyPressed(KeyEvent.VK_S))
        {
            dY = -snakeSpeed * pastTime;
        }
        if (Controller.isKeyPressed(KeyEvent.VK_D))
        {
            dX = snakeSpeed * pastTime;
        }
        if (makeNewHead)
        {
            Snake newHead = new Snake();
            newHead.center(snakeHead.center());
            newHead.next = snakeHead;
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
            color("green");
        }

        private void snakeHeadMove(double dX, double dY)
        {
            Vector mycenter = center();
            moveBy(dX, dY);
            if (next != null)
            {
                next.snakeChildrenMove(mycenter);
            }
        }

        private void snakeChildrenMove(Vector newCenter)
        {
            Vector mycenter = center();
            center(newCenter);
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
            color("red");
        }

        @Override
        public void onCollision(CollisionEvent<Snake> collisionEvent)
        {
            increaseScore();
            makeNewHead = true;
            remove();
            placeRandomGoodie();
        }
    }

    public static void main(String[] args)
    {
        Controller.start(new SnakeAdvanced(), 600, 400);
    }
}
