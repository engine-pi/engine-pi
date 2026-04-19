/*
 * Engine Pi ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2026 Josef Friedrich and contributors.
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
package demos.classes.resources.sound;

import java.awt.Graphics2D;

import pi.Scene;
import pi.resources.sound.AudioDebugBox;

/**
 * @author Josef Friedrich
 *
 * @since 0.47.0
 */
public class AudioDebugScene extends Scene
{
    AudioDebugBox box;

    public AudioDebugScene()
    {
        box = new AudioDebugBox();
        box.anchor(50, 50);
    }

    @Override
    public void renderOverlay(Graphics2D g, int width, int height)
    {
        box.remeasure();
        box.render(g);
    }
}
