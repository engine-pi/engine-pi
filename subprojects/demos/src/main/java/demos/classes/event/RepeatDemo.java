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

import java.awt.event.KeyEvent;

import pi.Game;
import pi.Scene;
import pi.Text;
import pi.event.PeriodicTaskExecutor;

/**
 * Demonstriert die Methode
 * {@link pi.event.FrameUpdateListenerRegistration#repeat(double, pi.event.PeriodicTask)}.
 *
 * <p>
 * Im Spielfenster wird eine Zahl hochgezählt. Über die <b>Leertaste</b> kann
 * die periodische Aufgabe gestoppt oder erneut gestartet werden.
 * </p>
 */
public class RepeatDemo extends Scene
{
    public RepeatDemo()
    {
        backgroundColor("white");
        add(new CounterText());
    }

    private class CounterText extends Text
    {
        PeriodicTaskExecutor task;

        public CounterText()
        {
            super("0", 2);
            setCenter(0, 0);
            start();
            addKeyStrokeListener((e) -> {
                if (e.getKeyCode() == KeyEvent.VK_SPACE)
                {
                    if (task == null)
                    {
                        start();
                    }
                    else
                    {
                        stop();
                    }
                }
            });
        }

        public void start()
        {
            task = repeat(1, (counter) -> {
                counter++;
                setContent(counter);
            });
        }

        public void stop()
        {
            task.unregister();
            task = null;
        }
    }

    public static void main(String[] args)
    {
        Game.start(new RepeatDemo());
    }
}
