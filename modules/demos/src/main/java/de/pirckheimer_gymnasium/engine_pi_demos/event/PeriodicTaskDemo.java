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

import java.awt.event.KeyEvent;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Scene;
import de.pirckheimer_gymnasium.engine_pi.actor.Text;
import de.pirckheimer_gymnasium.engine_pi.event.PeriodicTaskExecutor;

/**
 * Demonstriert die Klasse
 * {@link de.pirckheimer_gymnasium.engine_pi.event.PeriodicTaskExecutor}.
 *
 * <p>
 * Im Spielfenster wird eine Zahl hochgezählt. Über die Taste <b>P</b> kann die
 * periodische Aufgabe pausiert oder fortgesetzt werden. Über die
 * <b>Leertaste</b> kann die periodische Aufgabe gestoppt, was jedoch mit einer
 * <b>Fehlermeldung</b> (so gewollt) fehlschlägt.
 * </p>
 */
public class PeriodicTaskDemo extends Scene
{
    private PeriodicTaskExecutor task;

    public PeriodicTaskDemo()
    {
        setBackgroundColor("white");
        add(new CounterText());
    }

    private class CounterText extends Text
    {
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
                else if (e.getKeyCode() == KeyEvent.VK_P)
                {
                    if (task.isPaused)
                    {
                        task.resume();
                    }
                    else
                    {
                        task.pause();
                    }
                }
            });
        }

        public void start()
        {
            task = new PeriodicTaskExecutor(0.1, (counter) -> {
                setContent(counter);
            });
            addFrameUpdateListener(task);
        }

        public void stop()
        {
            task.unregister();
            task = null;
        }
    }

    public static void main(String[] args)
    {
        Game.start(new PeriodicTaskDemo(), 400, 200);
    }
}
