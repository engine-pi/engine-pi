/*
 * Source: https://github.com/engine-alpha/engine-alpha/blob/4.x/engine-alpha/src/main/java/ea/animation/interpolation/CosinusFloat.java
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
 * Interpoliert auf einer kompletten Cosinuskurve.
 *
 * @author Michael Andonie
 */
public class CosinusDouble implements Interpolator<Double>
{
    /**
     * Der Startwert der Cosinuskurve.
     */
    private final double start;

    /**
     * Die Amplitude der Cosinuskurve
     */
    private final double amplitude;

    /**
     * Erstellt einen neuen Cosinuskurven-Interpolator.
     *
     * @param start Der Startpunkt der Animation (also die Spitze der
     *     Cosinuskurve)
     * @param amplitude Die Amplitude der Cosinuskurve. Damit ist die
     *     Interpolation
     *     <ul>
     *     <li>Nach 0/4 der Zeit <code>start</code></li>
     *     <li>Nach 1/4 der Zeit <code>start-amplitude</code></li>
     *     <li>Nach 1/2 der Zeit <code>start- 2*amplitude</code></li>
     *     <li>Nach 3/4 der Zeit <code>start-amplitude</code></li>
     *     <li>Nach 4/4 der Zeit <code>start</code></li>
     *     </ul>
     */
    @API
    public CosinusDouble(double start, double amplitude)
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
        return (double) Math.cos(Math.PI * progress * 2) * amplitude + start
                - amplitude;
    }
}
