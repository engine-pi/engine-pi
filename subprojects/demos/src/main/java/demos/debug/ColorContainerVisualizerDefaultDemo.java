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

import pi.Controller;
import pi.Scene;
import pi.debug.ColorContainerVisualizer;

/**
 * Demonstriert die Klasse {@link ColorContainerVisualizer} mit dem
 * <b>Standard-Farben-Speicher</b>.
 *
 * <p>
 * <img alt="ColorContainerVisualizerDefault" src=
 * "https://raw.githubusercontent.com/engine-pi/engine-pi/main/misc/images/debug/ColorContainerVisualizerDefault.png">
 * </p>
 *
 * @author Josef Friedrich
 */
public class ColorContainerVisualizerDefaultDemo
{
    public static void main(String[] args)
    {
        Controller.instantMode(false);
        Controller.start(new Scene()
        {
            {
                new ColorContainerVisualizer(this);
            }
        });
    }
}
