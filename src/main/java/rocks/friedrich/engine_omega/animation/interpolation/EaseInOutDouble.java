/*
 * Source: https://github.com/engine-alpha/engine-alpha/blob/4.x/engine-alpha/src/main/java/ea/animation/interpolation/EaseInOutFloat.java
 *
 * Engine Omega ist eine anfängerorientierte 2D-Gaming Engine.
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
package rocks.friedrich.engine_omega.animation.interpolation;

import rocks.friedrich.engine_omega.animation.Interpolator;
import rocks.friedrich.engine_omega.annotations.API;
import rocks.friedrich.engine_omega.annotations.Internal;

public class EaseInOutDouble implements Interpolator<Double>
{
    /**
     * Startpunkt. Interpolationswert bei t=0
     */
    private final double start;

    /**
     * Endpunkt. Interpolationswert bei t=1
     */
    private final double end;

    /**
     * Erstellt einen EaseInOut-Interpolator. Interpoliert "smooth" zwischen den
     * beiden Werten, beginnt also langsam (erste Ableitung = 0) und endet
     * langsam (erste Ableitung = 0). Dazwischen wächst und schrumpft die
     * Änderungsrate dynamisch.
     *
     * @param start Der Startpunkt der Interpolation.
     * @param end   Der Endpunkt der Interpolation.
     */
    @API
    public EaseInOutDouble(double start, double end)
    {
        this.start = start;
        this.end = end;
    }

    @Internal
    @Override
    public Double interpolate(double progress)
    {
        return (double) ((Math.sin((double) progress * Math.PI - Math.PI / 2)
                + 1) / 2) * (this.end - this.start) + this.start;
    }
}
