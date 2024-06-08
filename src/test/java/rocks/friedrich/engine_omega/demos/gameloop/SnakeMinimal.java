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
package rocks.friedrich.engine_omega.demos.gameloop;

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
import rocks.friedrich.engine_omega.event.KeyListener;

public class SnakeMinimal extends Scene
{
    private Text scoreText = new Text("Score: 0", 1.4);

    private int score = 0;

    private Snake snake = new Snake();

    public SnakeMinimal()
    {
        add(snake);
        scoreText.setPosition(-9, 5);
        add(scoreText);
        placeRandomGoodie();
    }

    public void setScore(int score)
    {
        this.score = score;
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
        Goodie goodie = new Goodie();
        goodie.setCenter(x, y);
        add(goodie);
        goodie.addCollisionListener(snake, goodie);
    }

    private class Snake extends Circle
            implements FrameUpdateListener, KeyListener
    {
        private Vector movement = new Vector(0, 0);

        public Snake()
        {
            super(1);
            setColor(Color.GREEN);
        }

        @Override
        public void onFrameUpdate(double timeInS)
        {
            moveBy(movement.multiply(timeInS));
        }

        @Override
        public void onKeyDown(KeyEvent keyEvent)
        {
            switch (keyEvent.getKeyCode())
            {
            case KeyEvent.VK_W:
                movement = new Vector(0, 5);
                break;

            case KeyEvent.VK_A:
                movement = new Vector(-5, 0);
                break;

            case KeyEvent.VK_S:
                movement = new Vector(0, -5);
                break;

            case KeyEvent.VK_D:
                movement = new Vector(5, 0);
                break;
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
            remove();
            placeRandomGoodie();
        }
    }

    public static void main(String[] args)
    {
        Game.start(600, 400, new SnakeMinimal());
    }
}
