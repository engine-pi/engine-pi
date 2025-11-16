package de.pirckheimer_gymnasium.engine_pi;

import de.pirckheimer_gymnasium.engine_pi.dsa.turtle.AutoStartTurtleController;

// Go to file: file:///home/jf/repos/school/monorepo/inf/java/engine-pi/modules/demos/src/main/java/de/pirckheimer_gymnasium/demos/classes/dsa/turtle/TurtleDemo.java

/**
 * Eine <b>Schildkröte</b> um Turtle-Grafiken zu zeichnen.
 *
 * <h2>Die Hauptmethoden zum Zeichnen sind:</h2>
 *
 * <ul>
 * <li>{@link #forward(double) forward(double)}: Bewegt die Schildkröte in
 * Blickrichtung nach vorne.</li>
 * <li>{@link #forward() forward()}: Bewegt die Schildkröte in Blickrichtung 3
 * Meter nach vorne.</li>
 * <li>{@link #backward(double) backward(double)}: Bewegt die Schildkröte in
 * Blickrichtung rückwärts.</li>
 * <li>{@link #backward() backward()}: Bewegt die Schildkröte in Blickrichtung 3
 * Meter rückwärts.</li>
 * <li>{@link #left(double) left(double)}: Dreht die Schildkröte nach
 * links.</li>
 * <li>{@link #right(double) right(double)}: Dreht die Schildkröte nach
 * richts.</li>
 * <li>{@link #lowerPen() lowerPen()}: Wechselt in den Modus „zeichnen“.</li>
 * <li>{@link #liftPen() liftPen()}: Wechselt in den Modus „nicht
 * zeichnen“.</li>
 * </ul>
 *
 * <p>
 * Folgendes Code-Beispiel demonstiert, wie mit <b>minimalen</b>
 * Programmieraufwand eine <b>Turtle</b>-Grafik (hier ein Dreieck) gezeichnet
 * werden kann:
 * </p>
 *
 * <pre>{@code
 * import de.pirckheimer_gymnasium.engine_pi.Turtle;
 *
 * public class MinimalTurtleDemo
 * {
 *     public static void main(String[] args)
 *     {
 *         Turtle turtle = new Turtle();
 *         turtle.forward();
 *         turtle.left(120);
 *         turtle.forward();
 *         turtle.left(120);
 *         turtle.forward();
 *     }
 * }
 * }</pre>
 *
 * <p>
 * Diese Klasse öffnet automatisch eine neues Fenster.
 * </p>
 *
 * <p>
 * Diese Klasse hat animierte Methoden, die künstlich verlangsamt werden, damit
 * der Malprozess nachvollzogen werden kann. Diese animierte Methoden blockieren
 * den aktuellen Thread solange, bis die Animation abgeschlossen ist. Das
 * Zeichnen einer Turtle-Grafik kann unter Umständen sehr lange dauern. Deshalb
 * sollten keine animierten Methodenaufrufen in Konstruktoren geschrieben
 * werden, da das von diesem Konstruktor erzeugte Objekt unter Umständen lange
 * braucht, um erzeugt zu werden.
 * </p>
 *
 * @see de.pirckheimer_gymnasium.engine_pi.dsa.turtle.TurtleController
 */
public class Turtle extends AutoStartTurtleController
{
    public static void main(String[] args)
    {
        Turtle turtle = new Turtle();
        for (int i = 0; i < 3; i++)
        {
            turtle.lowerPen();
            turtle.forward(3);
            turtle.left(120);
        }
    }
}
