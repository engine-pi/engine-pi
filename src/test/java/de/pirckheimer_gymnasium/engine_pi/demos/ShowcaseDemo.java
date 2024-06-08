/*
 * Source: https://github.com/engine-alpha/engine-alpha/blob/4.x/engine-alpha-examples/src/main/java/ea/example/showcase/ShowcaseDemo.java
 *
 * Engine Alpha ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2011 - 2024 Michael Andonie and contributors.
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
package de.pirckheimer_gymnasium.engine_pi.demos;

import java.awt.event.KeyEvent;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Scene;

public abstract class ShowcaseDemo extends Scene
{
    /**
     * Geschwindigkeit der Camerabewegung pro Sekunde
     */
    private static final float CAMERA_SPEED = 7f;

    // Ob Mausrad-Zoom erlaubt ist
    private boolean zoomEnabled = true;

    // Ob Kamerasteuerung per Pfeiltasten aktiviert ist.
    private boolean cameraControlEnabled = true;

    // Ob Debug-Toggling enabled ist
    private boolean debuggingEnabled = true;

    public ShowcaseDemo(Scene parent)
    {
        addKeyListener(e -> {
            switch (e.getKeyCode())
            {
            case KeyEvent.VK_ESCAPE:
                Game.setDebug(false);
                Game.transitionToScene(parent);
                break;

            case KeyEvent.VK_D: // Toggle Debug
                if (debuggingEnabled)
                {
                    toggleDebug();
                }
                break;
            }
        });
        addFrameUpdateListener((i) -> {
            if (!cameraControlEnabled)
            {
                return;
            }
            // Smooth Camera Movement
            double dX = 0, dY = 0;
            if (Game.isKeyPressed(KeyEvent.VK_UP))
            {
                dY = CAMERA_SPEED * i;
            }
            else if (Game.isKeyPressed(KeyEvent.VK_DOWN))
            {
                dY = -CAMERA_SPEED * i;
            }
            if (Game.isKeyPressed(KeyEvent.VK_LEFT))
            {
                dX = -CAMERA_SPEED * i;
            }
            else if (Game.isKeyPressed(KeyEvent.VK_RIGHT))
            {
                dX = CAMERA_SPEED * i;
            }
            if (dX != 0 || dY != 0)
            {
                getCamera().moveBy(dX, dY);
            }
        });
        addMouseWheelListener(event -> {
            if (!zoomEnabled)
            {
                return;
            }
            double factor = event.getPreciseWheelRotation() > 0
                    ? 1 + .3 * event.getPreciseWheelRotation()
                    : 1 / (1 - .3 * event.getPreciseWheelRotation());
            double newzoom = getCamera().getMeter() * factor;
            if (newzoom <= 0)
            {
                return;
            }
            getCamera().setMeter(newzoom);
        });
    }

    public void setZoomEnabled(boolean zoomEnabled)
    {
        this.zoomEnabled = zoomEnabled;
    }

    public void setCameraControlEnabled(boolean cameraControlEnabled)
    {
        this.cameraControlEnabled = cameraControlEnabled;
    }

    protected void toggleDebug()
    {
        Game.setDebug(!Game.isDebug());
    }

    protected void setDebuggingEnabled(boolean debuggingEnabled)
    {
        this.debuggingEnabled = debuggingEnabled;
    }
}
