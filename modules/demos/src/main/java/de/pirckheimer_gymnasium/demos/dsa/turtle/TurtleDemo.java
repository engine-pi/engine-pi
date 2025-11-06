package de.pirckheimer_gymnasium.demos.dsa.turtle;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Scene;
import de.pirckheimer_gymnasium.engine_pi.dsa.turtle.TurtleAlgorithm;
import de.pirckheimer_gymnasium.engine_pi.event.KeyStrokeListener;

public class TurtleDemo extends Scene implements KeyStrokeListener
{
    ArrayList<TurtleAlgorithm> algos;

    Thread thread;

    public TurtleDemo()
    {
        algos = new ArrayList<>();
        algos.add(new SquareTurtle(this));
    }

    @Override
    public void onKeyDown(KeyEvent event)
    {
        switch (event.getKeyCode())
        {
        case KeyEvent.VK_SPACE:
            thread = new Thread(algos.get(0));
            thread.start();
            break;

        case KeyEvent.VK_E:
            thread.interrupt();
            break;

        case KeyEvent.VK_1:
            System.out.println("Set speed");
            // turtle.setSpeed(1);
            break;

        case KeyEvent.VK_9:
            // turtle.setSpeed(9);
            break;

        default:
            break;
        }

    }

    public static void main(String[] args)
    {
        Game.start(new TurtleDemo());
    }

}
