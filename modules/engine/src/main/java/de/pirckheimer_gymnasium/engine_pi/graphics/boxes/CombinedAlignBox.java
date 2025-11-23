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
package de.pirckheimer_gymnasium.engine_pi.graphics.boxes;

/**
 * @author Josef Friedrich
 *
 * @since 0.41.0
 */
public abstract class CombinedAlignBox extends CombinedChildBox
{
    public abstract int innerWidth();

    public abstract int innerHeight();

    public abstract CombinedAlignBox width(int width);

    public abstract CombinedAlignBox height(int height);

    public abstract CombinedAlignBox hAlign(HAlign hAlign);

    public abstract CombinedAlignBox vAlign(VAlign vAlign);
}
