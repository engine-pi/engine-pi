/*
 * Source: https://github.com/engine-alpha/engine-alpha/blob/4.x/engine-alpha/src/main/java/ea/animation/interpolation/ReverseEaseFloat.java
 *
 * Engine Pi ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2011 - 2018 Michael Andonie and contributors.
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

public class ReverseEaseDouble implements Interpolator<Double>
{
    private final double startAndEnd;

    private final double middle;

    @API
    public ReverseEaseDouble(double startAndEnd, double middle)
    {
        this.startAndEnd = startAndEnd;
        this.middle = middle;
    }

    @Internal
    @Override
    public Double interpolate(double progress)
    {
        return this.startAndEnd + (double) Math.sin(progress * Math.PI)
                * (this.middle - this.startAndEnd);
    }
}
