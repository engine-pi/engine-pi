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
package de.pirckheimer_gymnasium.engine_pi.dsa.turtle;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.dsa.turtle.graphics.HilbertCurveTurtleGraphics;
import de.pirckheimer_gymnasium.engine_pi.dsa.turtle.graphics.KochSnowflakeTurtleGraphics;
import de.pirckheimer_gymnasium.engine_pi.dsa.turtle.graphics.LevyCCurveTurtleGraphics;
import de.pirckheimer_gymnasium.engine_pi.dsa.turtle.graphics.PythagorasTreeTurtleGraphics;
import de.pirckheimer_gymnasium.engine_pi.dsa.turtle.graphics.SetDirectionTurtleGraphics;
import de.pirckheimer_gymnasium.engine_pi.dsa.turtle.graphics.SierpinskiCurveTurtleGraphics;
import de.pirckheimer_gymnasium.engine_pi.dsa.turtle.graphics.SquareTurtleGraphics;
import de.pirckheimer_gymnasium.engine_pi.dsa.turtle.graphics.TriangleTurtleGraphics;
import de.pirckheimer_gymnasium.engine_pi.event.KeyStrokeListener;

/**
 * <b>Bündelt</b> alle in der Engine Pi mitgelieferten Turtle-Grafiken und
 * spielt sie nacheinander ab.
 *
 * @author Josef Friedrich
 *
 * @since 0.40.0
 */
public class TurtleGraphicsCollection implements KeyStrokeListener
{
    /**
     * @since 0.40.0
     */
    private ArrayList<TurtleGraphics> graphics;

    /**
     * @since 0.40.0
     */
    private TurtleGraphics currentGraphics;

    /**
     * @since 0.40.0
     */
    private int currentGraphicsIndex = -1;

    /**
     * @since 0.40.0
     */
    private Thread thread;

    /**
     * @since 0.40.0
     */
    public TurtleGraphicsCollection()
    {
        graphics = new ArrayList<>();
        graphics.add(new TriangleTurtleGraphics());
        graphics.add(new SquareTurtleGraphics());
        graphics.add(new SetDirectionTurtleGraphics());
        graphics.add(new PythagorasTreeTurtleGraphics());
        graphics.add(new KochSnowflakeTurtleGraphics());
        graphics.add(new HilbertCurveTurtleGraphics());
        graphics.add(new LevyCCurveTurtleGraphics());
        graphics.add(new SierpinskiCurveTurtleGraphics());
        clearAll();

        Game.addKeyStrokeListener(this);
    }

    /**
     * @since 0.40.0
     */
    public void registerStartNext()
    {
        for (TurtleGraphics graphic : graphics)
        {
            graphic.onFinished(() -> {
                startNextAlgorithm();
            });
        }
    }

    /**
     * @since 0.40.0
     */
    public void clearAll()
    {
        TurtleLauncher.scene().dress.hide();
        TurtleLauncher.scene().clearBackground();
    }

    /**
     * @since 0.40.0
     */
    private void startAlgorithm(int index)
    {
        if (thread != null)
        {
            thread.interrupt();
        }
        currentGraphics = graphics.get(index);
        clearAll();
        TurtleLauncher.scene().dress.show();
        TurtleLauncher.launch(currentGraphics);
    }

    /**
     * @since 0.40.0
     */
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

    /**
     * @since 0.40.0
     */
    private void runAll()
    {
        registerStartNext();
        startAlgorithm(0);
        currentGraphicsIndex = 0;
    }

    /**
     * @since 0.40.0
     */
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
            TurtleLauncher.scene().animation.changeSpeed(+3);
            break;

        case KeyEvent.VK_2:
            TurtleLauncher.scene().animation.changeSpeed(-3);
            break;

        case KeyEvent.VK_3:
            TurtleLauncher.scene().pen.changeThickness(+1);
            break;

        case KeyEvent.VK_4:
            TurtleLauncher.scene().pen.changeThickness(-1);
            break;

        case KeyEvent.VK_W:
            TurtleLauncher.scene().animation.toggleWarpMode();
            break;

        case KeyEvent.VK_D:
            TurtleLauncher.scene().dress.show();
            break;

        default:
            break;
        }

    }

    public static void main(String[] args)
    {
        new TurtleGraphicsCollection().runAll();
    }
}
