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

import java.util.function.Supplier;

import de.pirckheimer_gymnasium.engine_pi.annotations.Internal;
import de.pirckheimer_gymnasium.engine_pi.util.TimeUtil;

// Go to file: file:///home/jf/repos/school/monorepo/inf/java/engine-pi/modules/demos/src/main/java/de/pirckheimer_gymnasium/demos/classes/dsa/turtle/TurtleDemo.java

/**
 * Eine Hilfsklasse zum Formulieren eines <b>Turtle-Algorithmus</b>.
 *
 * <p>
 * Diese abstrakte Klasse kann geerbt werden, um einen <b>Algorithmus</b> zur
 * Zeichnung einer Turtle-Grafik zu formulieren. Folgendes Code-Beispiel
 * illustiert die Verwendung dieser Hilfsklasse:
 * </p>
 *
 * <pre>{@code
 * import de.pirckheimer_gymnasium.engine_pi.dsa.turtle.TurtleAlgorithm;
 *
 * public class TriangleTurtle extends TurtleAlgorithm
 * {
 *     @Override
 *     public void draw()
 *     {
 *         for (int i = 0; i < 3; i++)
 *         {
 *             turtle.move(4);
 *             turtle.rotate(120);
 *         }
 *     }
 *
 *     public static void main(String[] args)
 *     {
 *         new TriangleTurtle().show();
 *     }
 * }
 * }</pre>
 *
 * <p>
 * Da Turtle-Algorithmen oft rekursiv sind, reicht es nicht den Algorithmus in
 * eine Methode zu schreiben. Die Klasse implementiert auch die
 * {@link Runnable}-Schnittstelle, um lang laufende Algorithmen in einem eigenen
 * Thread laufen zu lassen und gegenfalls zu stoppen, wenn er zu lange läuft.
 * </p>
 *
 * @author Josef Friedrich
 *
 * @since 0.38.0
 */
public abstract class TurtleGraphics implements Runnable
{
    /**
     * Die <b>Schildkröte</b>, die eine Grafik zeichnet.
     *
     * @since 0.40.0
     */
    protected TurtleController turtle;

    /**
     * Eine Callback-Funktion, die aufgerufen wird, wenn die Turtle-Grafik
     * fertig gezeichnet wurde.
     *
     * @since 0.40.0
     */
    protected Runnable onFinished;

    /**
     * @since 0.40.0
     */
    protected final InitialTurtleState initalState = new InitialTurtleState();

    /**
     * @since 0.40.0
     */
    protected Supplier<Boolean> onRepeat;

    /**
     * @since 0.40.0
     */
    protected boolean clearBeforeRun = false;

    /**
     * Wartet, nachdem die draw-Methode fertig ausgeführt wurde, die angegeben
     * Anzahl an Sekunden bevor zur nächsten Wiederholung übergegangen wird.
     *
     * @since 0.40.0
     */
    protected double waitAfterFinish = 0.0;

    /**
     * In dieser Methode soll der Turtle-Algorithmus formuliert werden.
     *
     * @since 0.40.0
     */
    protected abstract void draw();

    /**
     * @hidden
     */
    @Internal
    public void run()
    {
        draw();
        if (waitAfterFinish > 0)
        {
            TimeUtil.sleep((int) (1000 * waitAfterFinish));
        }
        if (onFinished != null)
        {
            onFinished.run();
        }
    }

    /**
     * @since 0.40.0
     */
    public TurtleGraphics onFinished(Runnable onFinished)
    {
        this.onFinished = onFinished;
        return this;
    }

    /**
     * @since 0.40.0
     */
    public TurtleGraphics onRepeat(Supplier<Boolean> afterRepeat)
    {
        this.onRepeat = afterRepeat;
        return this;
    }

    /**
     * @since 0.40.0
     */
    public void start()
    {
        TurtleLauncher.launch(this);
    }
}
