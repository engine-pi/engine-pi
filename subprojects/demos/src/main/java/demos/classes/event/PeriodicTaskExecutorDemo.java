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

import pi.Controller;
import pi.Scene;
import pi.Text;
import pi.event.PeriodicTaskExecutor;

// Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/subprojects/engine/src/main/java/pi/event/PeriodicTaskExecutor.java

/**
 * Demonstriert die Klasse {@link pi.event.PeriodicTaskExecutor}.
 *
 * <p>
 * Im Spielfenster wird eine Zahl hochgezählt. Über die Taste <b>P</b> kann die
 * periodische Aufgabe pausiert oder fortgesetzt werden. Über die
 * <b>Leertaste</b> kann die periodische Aufgabe gestoppt, was jedoch mit einer
 * <b>Fehlermeldung</b> (so gewollt) fehlschlägt.
 * </p>
 */
public class PeriodicTaskExecutorDemo extends Scene
{
    private PeriodicTaskExecutor task;

    public PeriodicTaskExecutorDemo()
    {
        add(new CounterText());
    }

    private class CounterText extends Text
    {
        public CounterText()
        {
            super("0");
            height(2);
            center(0, 0);
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
                content(counter);
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
        Controller.instantMode(false);
        Controller.start(new PeriodicTaskExecutorDemo(), 400, 200);
    }
}
