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
package demos.classes.class_scene;

import static pi.Controller.colors;

import java.awt.Color;
import java.awt.event.KeyEvent;

import pi.Controller;
import pi.dsa.turtle.PaintingSurfaceScene;
import pi.event.KeyStrokeListener;
import pi.event.MouseButton;
import pi.event.MouseClickListener;
import pi.graphics.geom.Vector;

public class PaintingSurfaceDemo extends PaintingSurfaceScene
        implements MouseClickListener, KeyStrokeListener
{

    @Override
    public void onKeyDown(KeyEvent event)
    {
        switch (event.getKeyCode())
        {
        case KeyEvent.VK_C:
            getPaintingSurface().clear();
            break;

        case KeyEvent.VK_R:
            getPaintingSurface().fill("red");
            break;

        default:
            break;
        }
    }

    @Override
    public void onMouseDown(Vector position, MouseButton button)
    {

        // Der linke Mausklick zeichnet einen blauen Kreis, der rechte einen
        // grünen.
        Color color;
        if (button == MouseButton.LEFT)
        {
            color = colors.get("blue");
        }
        else
        {
            color = colors.get("green");
        }

        getPaintingSurface().drawCircle(position, 20, color);
    }

    public static void main(String[] args)
    {
        Controller.start(new PaintingSurfaceDemo());
    }

}
