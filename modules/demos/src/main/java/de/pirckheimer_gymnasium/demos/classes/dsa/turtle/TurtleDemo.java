package de.pirckheimer_gymnasium.demos.classes.dsa.turtle;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import de.pirckheimer_gymnasium.engine_pi.dsa.turtle.Turtle;
import de.pirckheimer_gymnasium.engine_pi.dsa.turtle.TurtleAlgorithm;
import de.pirckheimer_gymnasium.engine_pi.event.KeyStrokeListener;

public class TurtleDemo extends Turtle implements KeyStrokeListener
{
    private ArrayList<TurtleAlgorithm> algos;

    private TurtleAlgorithm currentAlgo;

    private int currentAlgoIndex = -1;

    private Thread thread;

    public TurtleDemo()
    {
        algos = new ArrayList<>();
        algos.add(new TriangleTurtle(this));
        algos.add(new SquareTurtle(this));
        algos.add(new SetDirectionTurtle(this));
        algos.add(new SnowflakeTurtle(this));
        algos.add(new HilbertCurveTurtle(this));
        algos.add(new LevyCCurveTurtle(this));
        algos.add(new SierpinskiCurveTurtle(this));
        clearAll();
    }

    public void registerStartNext()
    {
        for (TurtleAlgorithm turtleAlgorithm : algos)
        {
            turtleAlgorithm.onFinished(() -> {
                startNextAlgorithm();
            });
        }
    }

    public void clearAll()
    {
        for (TurtleAlgorithm turtleAlgorithm : algos)
        {
            turtleAlgorithm.getTurtle().hide();
            turtleAlgorithm.getTurtle().clearBackground();
        }
    }

    private void startAlgorithm(int index)
    {
        if (thread != null)
        {
            thread.interrupt();
        }
        currentAlgo = algos.get(index);
        clearAll();
        currentAlgo.getTurtle().show();
        thread = new Thread(currentAlgo);
        thread.start();
    }

    private void startNextAlgorithm()
    {
        if (currentAlgoIndex == algos.size() - 1)
        {
            currentAlgoIndex = 0;
        }
        else
        {
            currentAlgoIndex++;
        }
        startAlgorithm(currentAlgoIndex);
    }

    private void runAll()
    {
        registerStartNext();
        startAlgorithm(0);
        currentAlgoIndex = 0;
    }

    @Override
    public void onKeyDown(KeyEvent event)
    {
        switch (event.getKeyCode())
        {
        case KeyEvent.VK_ENTER:
            runAll();
            break;

        case KeyEvent.VK_SPACE:
            startNextAlgorithm();
            break;

        case KeyEvent.VK_E:
            thread.interrupt();
            break;

        case KeyEvent.VK_1:
            currentAlgo.getTurtle().changeSpeed(+3);
            break;

        case KeyEvent.VK_2:
            currentAlgo.getTurtle().changeSpeed(-3);
            break;

        case KeyEvent.VK_3:
            currentAlgo.getTurtle().changeLineWidth(+1);
            break;

        case KeyEvent.VK_4:
            currentAlgo.getTurtle().changeLineWidth(-1);
            break;

        case KeyEvent.VK_W:
            currentAlgo.getTurtle().toggleWarpMode();
            break;

        case KeyEvent.VK_D:
            currentAlgo.getTurtle().setNextDress();
            break;

        default:
            break;
        }

    }

    public static void main(String[] args)
    {
        new TurtleDemo();
    }

}
