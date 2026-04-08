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
package demos.classes.actor;

import java.awt.event.KeyEvent;

import pi.Controller;
import pi.Scene;
import pi.actor.StopWatch;
import pi.event.FrameUpdateListener;
import pi.event.KeyStrokeListener;

// Go to file:///data/school/repos/inf/java/engine-pi/subprojects/engine/src/main/java/pi/actor/StopWatch.java

/**
 * @author Josef Friedrich
 *
 * @since 0.45.0
 */
public class StopWatchDemo extends Scene
        implements KeyStrokeListener, FrameUpdateListener
{
    StopWatch watch;

    public StopWatchDemo()
    {
        info().title("Demonstriert die Figur Stoppuhr (StopWatch)")
            .help("Tastenkürzel:",
                "o: stop()",
                "r: reset()",
                "s: start()",
                "t: toggle()");

        watch = new StopWatch();
        watch.height(3).center(0, 0);
        add(watch);
    }

    @Override
    public void onKeyDown(KeyEvent event)
    {
        switch (event.getKeyCode())
        {
        case KeyEvent.VK_O -> watch.stop();
        case KeyEvent.VK_R -> watch.reset();
        case KeyEvent.VK_S -> watch.start();
        case KeyEvent.VK_T -> watch.toggle();
        case KeyEvent.VK_I -> watch.time(3723456);
        }
    }

    @Override
    public void onFrameUpdate(double pastTime)
    {
        System.out.println(watch);
    }

    public static void main(String[] args)
    {
        Controller.instantMode(false);
        Controller.start(new StopWatchDemo());
    }
}
