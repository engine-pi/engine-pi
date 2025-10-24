/*
 * Source: https://github.com/engine-alpha/engine-alpha/blob/4.x/engine-alpha/src/main/java/ea/animation/interpolation/SinusFloat.java
 *
 * Engine Pi ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2011 - 2024 Michael Andonie and contributors.
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
package de.pirckheimer_gymnasium.engine_pi.animation.interpolation;

import de.pirckheimer_gymnasium.engine_pi.animation.Interpolator;
import de.pirckheimer_gymnasium.engine_pi.annotations.API;
import de.pirckheimer_gymnasium.engine_pi.annotations.Internal;

/**
 * Interpoliert auf einer kompletten Sinuskurve.
 *
 * @author Michael Andonie
 */
public class SinusDouble implements Interpolator<Double>
{
    /**
     * Der Startwert (und Endwert)
     */
    private final double start;

    /**
     * Die Amplitude der Sinuskurve
     */
    private final double amplitude;

    /**
     * Erstellt einen <code>SinusDouble</code>-Interpolator.
     *
     * @param start Der Startpunkt der Sinuskurve, die dieser Interpolator
     *     interpoliert. Dieser Punkt wird also erreicht zu Beginn, bei Ablauf
     *     der halben Zeit sowie zum Ende der Interpolation.
     * @param amplitude Die Amplitude der Sinuskurve. Bei 1/4 der Zeit ist der
     *     Wert der Interpolation also <code>start+amplitude</code> und bei 3/4
     *     ist der Wert <code>start-amplitude</code>.<br>
     *     Negative Werte sind natürlich auch möglich.
     */
    @API
    public SinusDouble(double start, double amplitude)
    {
        this.start = start;
        this.amplitude = amplitude;
    }

    /**
     * @hidden
     */
    @Internal
    @Override
    public Double interpolate(double progress)
    {
        return (double) Math.sin(Math.PI * progress * 2) * amplitude + start;
    }
}
