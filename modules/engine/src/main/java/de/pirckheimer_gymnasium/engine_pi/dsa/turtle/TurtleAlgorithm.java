package de.pirckheimer_gymnasium.engine_pi.dsa.turtle;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Scene;

/**
 * Diese abstrakte Klasse kann geerbt werden, um einen Algorithmus zur Zeichnung
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

    protected Runnable onFinished;

    public TurtleAlgorithm(Scene scene)
    {
        turtle = new Turtle(scene);
        this.scene = scene;
    }

    public TurtleAlgorithm()
    {
        this(new Scene());
    }

    public TurtleAlgorithm onFinished(Runnable onFinished)
    {
        this.onFinished = onFinished;
        return this;
    }

    protected abstract void draw();

    public void run()
    {
        draw();
        if (onFinished != null)
        {
            onFinished.run();
        }
    }

    private void trigger(boolean openWindow)
    {
        if (openWindow)
        {
            Game.start(scene);
        }
        run();

    }

    /**
     * Startet den Algorithmus und öffnet ein Fenster.
     */
    public void show()
    {
        trigger(true);
    }

    /**
     * Startet den Algorithmus und öffnet kein Fenster.
     */
    public void start()
    {
        trigger(false);
    }

    /**
     * Löscht den Hintergrund, d.h. alle bisher eingezeichneten Malspuren.
     *
     * @see Turtle#clearBackground()
     */
    public void clearBackground()
    {
        turtle.clearBackground();
    }

    /**
     * Blenden die Schildkröte aus.
     *
     * @see Turtle#hide()
     */
    public void hideTurtle()
    {
        turtle.hide();
    }

    /**
     * Blendet die Schildkröte ein.
     *
     * @see Turtle#show()
     */
    public void showTurtle()
    {
        turtle.show();
    }
}
