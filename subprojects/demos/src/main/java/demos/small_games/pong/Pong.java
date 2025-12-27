package demos.small_games.pong;

import java.awt.event.KeyEvent;

import pi.Game;
import pi.Scene;
import pi.event.KeyStrokeListener;

public class Pong extends Scene implements KeyStrokeListener
{
    Paddle paddleLeft;

    Paddle paddleRight;

    Ball ball;

    Border topBorder;

    Border bottomBorder;

    public Pong()
    {
        paddleLeft = new Paddle();
        paddleLeft.setPosition(-11, 0);

        paddleRight = new Paddle();
        paddleRight.setPosition(10, 0);

        ball = new Ball();

        topBorder = new Border();
        topBorder.setPosition(-10, 8);

        bottomBorder = new Border();
        bottomBorder.setPosition(-10, -9);

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
