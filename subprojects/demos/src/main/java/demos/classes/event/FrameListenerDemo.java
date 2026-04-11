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
package demos.classes.event;

import pi.Controller;
import pi.Layer;
import pi.Scene;
import pi.Text;
import pi.event.FrameListener;

/**
 * Demonstriert die Methode {@link pi.event.FrameListener#onFrame(double)}.
 */
public class FrameListenerDemo extends Scene implements FrameListener
{
    public FrameListenerDemo()
    {
        add(new TextActor());
        addLayer(new MyLayer());
    }

    private class MyLayer extends Layer implements FrameListener
    {
        @Override
        public void onFrame(double pastTime)
        {
            System.out.println("Layer: " + pastTime);
        }
    }

    private class TextActor extends Text implements FrameListener
    {
        public TextActor()
        {
            super("Text Actor");
            height(2);
            center(0, 0);
        }

        @Override
        public void onFrame(double pastTime)
        {
            System.out.println("Actor: " + pastTime);
        }
    }

    @Override
    public void onFrame(double pastTime)
    {
        System.out.println("Scene: " + pastTime);
    }

    public static void main(String[] args)
    {
        Controller.debug();
        Controller.start(new FrameListenerDemo(), 700, 200);
    }
}
