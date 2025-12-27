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
package demos.small_games.pong;

import pi.actor.BodyType;
import pi.actor.Rectangle;

/**
 * Eine Abprallbalken als unsichtbares Rechteck am oberen und unteren
 * Spielfeldrand, an denen der Ball abprallen kann.
 *
 * @author Josef Friedrich
 */
public class BounceBar extends Rectangle
{
    public BounceBar()
    {
        super(30, 1);
        setBodyType(BodyType.STATIC);
        setElasticity(1);
        setVisible(false);
    }
}
