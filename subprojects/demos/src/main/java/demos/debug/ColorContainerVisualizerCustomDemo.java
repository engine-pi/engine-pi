/*
 * Engine Pi ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2025, 2026 Josef Friedrich and contributors.
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
package demos.debug;

import static pi.Controller.colors;

import pi.Controller;
import pi.Scene;
import pi.debug.ColorContainerVisualizer;

/**
 * Demonstriert die Klasse {@link ColorContainerVisualizer} mit einem
 * <b>eigenen</b> Farben-Speicher.
 *
 * <p>
 * <img alt="ColorContainerVisualizerCustom" src=
 * "https://raw.githubusercontent.com/engine-pi/engine-pi/main/misc/images/debug/ColorContainerVisualizerCustom.png">
 * </p>
 */
public class ColorContainerVisualizerCustomDemo
{
    public static void main(String[] args)
    {
        Controller.instantMode(false);
        colors.clear();
        colors.add("custom", 1, 200, 3, "alias");
        colors.add("favourite", 117, 4, 36, "alias1", "alias2", "Alias 3");
        Controller.start(new Scene()
        {
            {
                new ColorContainerVisualizer(this);
            }
        });
    }
}
