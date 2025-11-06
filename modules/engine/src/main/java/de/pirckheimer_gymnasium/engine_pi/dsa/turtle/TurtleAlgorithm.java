package de.pirckheimer_gymnasium.engine_pi.dsa.turtle;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Scene;

/**
 * Diese abstrakte kann von geerbt werden, um einen Algorithmus zur Zeichnung
 * einer Turtle-Grafik zu formulieren. Da Turtle-Algorithmen oft rekursiv sind,
 * reicht es nicht den Algorithmus in eine Methode zu schreiben. Die Klasse
 * implementiert auch die {@link Runnable}-Schnittstelle, um lang laufende
 * Algorithmen zu stoppen zu können.
 *
 * @author Josef Friedrich
 *
 * @since 0.38.0
 */
public abstract class TurtleAlgorithm implements Runnable
{
    protected Turtle turtle;

    protected Scene scene;

    public TurtleAlgorithm(Scene scene)
    {
        turtle = new Turtle(scene);
        this.scene = scene;
    }

    public TurtleAlgorithm()
    {
        this(new Scene());
    }

    public abstract void run();

    public void start()
    {
        Game.start(scene);
        run();
    }

    /**
     * Löscht den Hintergrund, d.h. alle bisher eingezeichneten Malspuren.
     */
    public void clearBackground()
    {
        turtle.clearBackground();
    }

    /**
     * Blenden die Schildkröte aus.
     */
    public void hideTurtle()
    {
        turtle.hide();
    }

    /**
     * Blendet die Schildkröte ein.
     */
    public void showTurtle()
    {
        turtle.show();
    }
}
