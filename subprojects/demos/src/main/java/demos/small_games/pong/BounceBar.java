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

import pi.Controller;
import pi.Rectangle;
import pi.physics.BodyType;

/**
 * Eine Abprallbalken als unsichtbares Rechteck am oberen und unteren
 * Spielfeldrand, an dem der Ball abprallen kann.
 *
 * @author Josef Friedrich
 */
public class BounceBar extends Rectangle
{
    static
    {
        Controller.instantMode(false);
    }

    public BounceBar(double width)
    {
        super(width, 1);
        bodyType(BodyType.STATIC);
        restitution(1);
        visible(false);
    }

    /**
     * Macht den Abprallbalken sichtbar, um überprüfen zu können, ob er richtig
     * platziert ist.
     */
    public BounceBar debug()
    {
        visible(true);
        color("gray");
        return this;
    }

    public static void main(String[] args)
    {
        Controller.start(new PongTable());
    }
}
