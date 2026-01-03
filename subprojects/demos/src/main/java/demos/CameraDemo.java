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
package demos;

import java.awt.event.KeyEvent;

import pi.Camera;
import pi.Game;
import pi.Scene;
import pi.Text;
import pi.Vector;
import pi.event.KeyStrokeListener;
import pi.event.MouseButton;
import pi.event.MouseClickListener;

/**
 * Demonstriert die Klasse {@link Camera}.
 */
public class CameraDemo extends Scene
        implements MouseClickListener, KeyStrokeListener
{
    Camera camera;

    Text focus;

    public CameraDemo()
    {
        camera = getCamera();
        add(new Text("Camera demo", 5).setColor("white"));
        focus = new Text("Focus");
        focus.setColor("yellow").setPosition(0, 4);
        add(focus);
        camera.focus(focus);
    }

    @Override
    public void onMouseDown(Vector position, MouseButton button)
    {
        camera.removeFocus();
        camera.focus(position);
        System.out.println(camera);
    }

    private void moveFocus(int dX, int dY)
    {
        focus.moveBy(dX, dY);
        camera.focus(focus);
    }

    @Override
    public void onKeyDown(KeyEvent event)
    {
        switch (event.getKeyCode())
        {
        case KeyEvent.VK_1 -> {
            camera.focus(new Vector(1, 1));
        }
        case KeyEvent.VK_UP -> moveFocus(0, 1);
        case KeyEvent.VK_LEFT -> moveFocus(-1, 0);
        case KeyEvent.VK_DOWN -> moveFocus(0, -1);
        case KeyEvent.VK_RIGHT -> moveFocus(1, 0);
        case KeyEvent.VK_R -> camera.rotateBy(-30);
        case KeyEvent.VK_L -> camera.rotateBy(30);
        case KeyEvent.VK_2 -> camera.rotateTo(0);
        case KeyEvent.VK_3 -> camera.rotateTo(90);
        case KeyEvent.VK_4 -> camera.rotateTo(180);
        case KeyEvent.VK_5 -> camera.rotateTo(270);
        }
        System.out.println(camera);
    }

    public static void main(String[] args)
    {
        Game.instantMode(false);
        Game.start(new CameraDemo());
    }
}
