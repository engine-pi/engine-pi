package de.pirckheimer_gymnasium.engine_pi.dsa.turtle;

import de.pirckheimer_gymnasium.engine_pi.Vector;

/**
 * Alle Einstellungen, die die Schildkröte braucht, um einen Algorithmus
 * sinnvoll zeichnen zu können.
 */
public class TurtleStartConfig
{
    /**
     * Die Startposition der Schildkröte.
     */
    public Vector position = new Vector(0, 0);

    /**
     * Die Richtung, in die die Schildkröte schaut.
     */
    public double rotation = 0;

    /**
     * Zeigt an, ob die Schildkröte momentan den <b>Stift gesenkt</b> hat und
     * zeichnet oder nicht.
     */
    public boolean drawLine = true;

    /**
     * Die <b>Geschwindigkeit</b>, mit der sich die Schildkröte bewegt (in Meter
     * pro Sekunde).
     */
    public double speed = 6;

    /**
     * Im sogenannte Warp-Modus finden keine Animationen statt. Die
     * Turtle-Grafik wird so schnell wie möglich gezeichnet.
     */
    public boolean warpMode = false;

    public TurtleStartConfig()
    {

    }

    public TurtleStartConfig(Vector position, double rotation, boolean drawLine,
            double speed, boolean warpMode)
    {
        this.position = position;
        this.rotation = rotation;
        this.drawLine = drawLine;
        this.speed = speed;
        this.warpMode = warpMode;
    }

    public void position(Vector position)
    {
        this.position = position;
    }

    public void rotation(double rotation)
    {
        this.rotation = rotation;
    }

    public void drawLine(boolean drawLine)
    {
        this.drawLine = drawLine;
    }

    public void speed(double speed)
    {
        this.speed = speed;
    }

    public void warpMode(boolean warpMode)
    {
        this.warpMode = warpMode;
    }

}
