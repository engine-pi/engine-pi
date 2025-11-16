package de.pirckheimer_gymnasium.engine_pi.dsa.turtle;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import de.pirckheimer_gymnasium.engine_pi.dsa.turtle.graphics.HilbertCurveTurtle;
import de.pirckheimer_gymnasium.engine_pi.dsa.turtle.graphics.LevyCCurveTurtle;
import de.pirckheimer_gymnasium.engine_pi.dsa.turtle.graphics.PythagorasTreeTurtle;
import de.pirckheimer_gymnasium.engine_pi.dsa.turtle.graphics.SetDirectionTurtle;
import de.pirckheimer_gymnasium.engine_pi.dsa.turtle.graphics.SierpinskiCurveTurtle;
import de.pirckheimer_gymnasium.engine_pi.dsa.turtle.graphics.SnowflakeTurtle;
import de.pirckheimer_gymnasium.engine_pi.dsa.turtle.graphics.SquareTurtle;
import de.pirckheimer_gymnasium.engine_pi.dsa.turtle.graphics.TriangleTurtle;
import de.pirckheimer_gymnasium.engine_pi.event.KeyStrokeListener;

public class TurtleGraphicsCollection implements KeyStrokeListener
{

    private ArrayList<TurtleGraphics> graphics;

    private TurtleGraphics currentGraphics;

    private int currentGraphicsIndex = -1;

    private Thread thread;

    public TurtleGraphicsCollection()
    {
        graphics = new ArrayList<>();
        graphics.add(new TriangleTurtle());
        graphics.add(new SquareTurtle());
        graphics.add(new SetDirectionTurtle());
        graphics.add(new PythagorasTreeTurtle());
        graphics.add(new SnowflakeTurtle());
        graphics.add(new HilbertCurveTurtle());
        graphics.add(new LevyCCurveTurtle());
        graphics.add(new SierpinskiCurveTurtle());
        clearAll();
    }

    public void registerStartNext()
    {
        for (TurtleGraphics graphic : graphics)
        {
            graphic.onFinished(() -> {
                startNextAlgorithm();
            });
        }
    }

    public void clearAll()
    {
        TurtleLauncher.scene().hide();
        TurtleLauncher.scene().clearBackground();
    }

    private void startAlgorithm(int index)
    {
        if (thread != null)
        {
            thread.interrupt();
        }
        currentGraphics = graphics.get(index);
        clearAll();
        TurtleLauncher.scene().show();
        TurtleLauncher.launch(currentGraphics);
    }

    private void startNextAlgorithm()
    {
        if (currentGraphicsIndex == graphics.size() - 1)
        {
            currentGraphicsIndex = 0;
        }
        else
        {
            currentGraphicsIndex++;
        }
        startAlgorithm(currentGraphicsIndex);
    }

    private void runAll()
    {
        registerStartNext();
        startAlgorithm(0);
        currentGraphicsIndex = 0;
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
            TurtleLauncher.scene().changeSpeed(+3);
            break;

        case KeyEvent.VK_2:
            TurtleLauncher.scene().changeSpeed(-3);
            break;

        case KeyEvent.VK_3:
            TurtleLauncher.scene().changeLineWidth(+1);
            break;

        case KeyEvent.VK_4:
            TurtleLauncher.scene().changeLineWidth(-1);
            break;

        case KeyEvent.VK_W:
            TurtleLauncher.scene().toggleWarpMode();
            break;

        case KeyEvent.VK_D:
            TurtleLauncher.scene().setNextDress();
            break;

        default:
            break;
        }

    }

    public static void main(String[] args)
    {
        new TurtleGraphicsCollection();
    }

}
