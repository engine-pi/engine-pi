package de.pirckheimer_gymnasium.engine_pi.dsa.turtle;

// Go to file: file:///home/jf/repos/school/monorepo/inf/java/engine-pi/modules/demos/src/main/java/de/pirckheimer_gymnasium/demos/classes/dsa/turtle/TurtleAnimationControllerDemo.java

/**
 * @author Josef Friedrich
 *
 * @since 0.40.0
 */
public class TurtleAnimationController
{
    /**
     * Die <b>Geschwindigkeit</b>, mit der sich die Schildkröte bewegt (in Meter
     * pro Sekunde).
     */
    double speed = 6;

    /**
     * Im sogenannte Warp-Modus finden keine Animationen statt. Die
     * Turtle-Grafik wird so schnell wie möglich gezeichnet.
     */
    boolean warpMode = false;

    /**
     * Setzt die <b>Geschwindigkeit</b>, mit der sich die Schildkröte bewegt (in
     * Meter pro Sekunde).
     *
     * @param speed Die <b>Geschwindigkeit</b>, mit der sich die Schildkröte
     *     bewegt (in Meter pro Sekunde).
     *
     * @since 0.38.0
     */
    public void speed(double speed)
    {
        if (speed <= 0)
        {
            throw new RuntimeException(
                    "Die Geschwindigkeit der Schildkröte muss größer als 0 sein, nicht: "
                            + speed);
        }
        this.speed = speed;
    }

    /**
     * <b>Ändert</b> die aktuelle <b>Geschwindigkeit</b> um den angegebenen
     * Wert.
     *
     * <p>
     * Positive Werte erhöhen die Geschwindigkeit, negative Werte verringern
     * sie. Führt die geplante Änderung dazu, dass die Geschwindigkeit negativ
     * würde, so wird die Änderung verworfen und die Geschwindigkeit bleibt
     * unverändert.
     * </p>
     *
     * @param speedChange Der Betrag, um den die Geschwindigkeit erhöht
     *     (positiv) oder verringert (negativ) werden soll.
     *
     * @since 0.38.0
     */
    public void changeSpeed(double speedChange)
    {
        if (speed + speedChange < 0)
        {
            return;
        }
        speed += speedChange;
    }

    /**
     * Schaltet in den sogenannten „<b>Warp-Modus</b>“.
     *
     * <b>Im Warp-Modus finden keine Animationen statt. Die Turtle-Grafik wird
     * so schnell wie möglich gezeichnet.</b>
     *
     * @since 0.38.0
     */
    public void warp()
    {
        warpMode = true;
    }

    /**
     * Schaltet den sogenannten „<b>Warp-Modus</b>“ <b>an oder aus</b>.
     *
     * <b>Im Warp-Modus finden keine Animationen statt. Die Turtle-Grafik wird
     * so schnell wie möglich gezeichnet.</b>
     *
     * @since 0.38.0
     */
    public void toggleWarpMode()
    {
        warpMode = !warpMode;
    }

    /**
     * Setzt den Zustand des sogenannten „<b>Warp-Modus</b>“.
     *
     * <b>Im Warp-Modus finden keine Animationen statt. Die Turtle-Grafik wird
     * so schnell wie möglich gezeichnet.</b>
     *
     * @param warpMode Der Warp-Modulus wird angeschaltet, falls der Wert wahr
     *     ist. Er wird ausgeschaltet, falls der Wert falsch ist.
     *
     * @since 0.40.0
     */
    public void warp(boolean warpMode)
    {
        this.warpMode = warpMode;
    }
}
