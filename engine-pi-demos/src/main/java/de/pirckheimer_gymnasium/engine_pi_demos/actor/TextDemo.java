/*
 * Engine Pi ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2024 Josef Friedrich and contributors.
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
package de.pirckheimer_gymnasium.engine_pi_demos.actor;

import java.awt.Font;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Resources;
import de.pirckheimer_gymnasium.engine_pi.actor.Text;

/**
 * Demonstiert die Figur <b>Text</b>.
 *
 * @author Josef Friedrich
 *
 * @since 0.37.0
 */
public class TextDemo
{

    public static void main(String[] args)
    {
        Game.setTitle("Cantarell");
        Game.start(s -> {
            s.add(new Text("Das ist die mitgelieferte Schrift Can\ntarell", 1,
                    "fonts/Cantarell-Regular.ttf").setPosition(-7, 0));
            Font cantarell = Resources.FONTS.get("fonts/Cantarell-Regular.ttf");
            s.add(new Text("Mit Unterlängen", 2).setFont(cantarell)
                    .setPosition(-7, -2));
            s.add(new Text("... ohne", 2).setFont(cantarell).setPosition(4,
                    -2));
            s.setBackgroundColor("green");
        });
    }
}
