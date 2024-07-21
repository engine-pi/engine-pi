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
package de.pirckheimer_gymnasium.engine_pi_demos.event;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Layer;
import de.pirckheimer_gymnasium.engine_pi.Scene;
import de.pirckheimer_gymnasium.engine_pi.actor.Text;
import de.pirckheimer_gymnasium.engine_pi.event.FrameUpdateListener;

/**
 * Demonstriert die Methode
 * {@link de.pirckheimer_gymnasium.engine_pi.event.FrameUpdateListener#onFrameUpdate(double)}.
 */
public class FrameUpdateListenerDemo extends Scene
        implements FrameUpdateListener
{
    public FrameUpdateListenerDemo()
    {
        add(new TextActor());
        addLayer(new MyLayer());
    }

    private class MyLayer extends Layer implements FrameUpdateListener
    {
        @Override
        public void onFrameUpdate(double pastTime)
        {
            System.out.println("Layer: " + pastTime);
        }
    }

    private class TextActor extends Text implements FrameUpdateListener
    {
        public TextActor()
        {
            super("Text Actor", 2);
            setCenter(0, 0);
        }

        @Override
        public void onFrameUpdate(double pastTime)
        {
            System.out.println("Actor: " + pastTime);
        }
    }

    @Override
    public void onFrameUpdate(double pastTime)
    {
        System.out.println("Scene: " + pastTime);
    }

    public static void main(String[] args)
    {
        Game.setDebug(true);
        Game.start(new FrameUpdateListenerDemo(), 700, 200);
    }
}
