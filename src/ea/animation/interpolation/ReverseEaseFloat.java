/*
 * Engine Alpha ist eine anfängerorientierte 2D-Gaming Engine.
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

package ea.animation.interpolation;

import ea.animation.Interpolator;

public class ReverseEaseFloat implements Interpolator<Float> {
    private float startAndEnd;
    private float middle;

    public ReverseEaseFloat(float startAndEnd, float middle) {
        this.startAndEnd = startAndEnd;
        this.middle = middle;
    }

    @Override
    public Float interpolate(float progress) {
        return this.startAndEnd + (float) Math.sin(progress * Math.PI) * (this.middle - this.startAndEnd);
    }
}