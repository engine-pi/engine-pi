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

// Go to file: file:///home/jf/repos/school/monorepo/inf/java/engine-pi/modules/demos/src/main/java/de/pirckheimer_gymnasium/demos/classes/dsa/turtle/TurtleAnimationControllerDemo.java

/**
 * Steuert die <b>Animationen</b>, die während des Malprozesses der Schildkröte
 * zu sehen sind.
 *
 * <p>
 * Mit Hilfe dieser Klasse kann zum Beispiel die Geschwindigkeit eingestellt
 * oder der Warp-Modus eingeschaltet werden.
 * </p>
 *
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
    public TurtleAnimationController speed(double speed)
    {
        if (speed <= 0)
        {
            throw new RuntimeException(
                    "Die Geschwindigkeit der Schildkröte muss größer als 0 sein, nicht: "
                            + speed);
        }
        this.speed = speed;
        return this;
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
    public TurtleAnimationController changeSpeed(double speedChange)
    {
        if (speed + speedChange < 0)
        {
            return this;
        }
        speed += speedChange;
        return this;
    }

    /**
     * Schaltet in den sogenannten „<b>Warp-Modus</b>“.
     *
     * <b>Im Warp-Modus finden keine Animationen statt. Die Turtle-Grafik wird
     * so schnell wie möglich gezeichnet.</b>
     *
     * @since 0.38.0
     */
    public TurtleAnimationController warp()
    {
        warpMode = true;
        return this;
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
    public TurtleAnimationController warp(boolean warpMode)
    {
        this.warpMode = warpMode;
        return this;
    }

    /**
     * Schaltet den sogenannten „<b>Warp-Modus</b>“ <b>an oder aus</b>.
     *
     * <b>Im Warp-Modus finden keine Animationen statt. Die Turtle-Grafik wird
     * so schnell wie möglich gezeichnet.</b>
     *
     * @since 0.38.0
     */
    public TurtleAnimationController toggleWarpMode()
    {
        warpMode = !warpMode;
        return this;
    }
}
