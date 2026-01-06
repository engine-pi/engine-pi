/*
 * Engine Pi ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2025 Josef Friedrich and contributors.
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
package pi.event;

import static pi.Controller.config;

import java.awt.event.KeyEvent;

import pi.Camera;
import pi.Controller;
import pi.Scene;

/**
 * Registriert im Auslieferungszustand einige wenige <b>grundlegenden Maus- und
 * Tastatur-Steuermöglichkeiten</b>.
 *
 * <p>
 * Diese sind hoffentlich beim Entwickeln hilfreich. Mit den statischen Methoden
 * {@link Controller#removeDefaultControl()} können diese Kürzel entfernt oder
 * mit {@link Controller#defaultControl(DefaultListener)} neue Kürzel gesetzt
 * werden.
 * </p>
 *
 * <ul>
 * <li>{@code ESCAPE} zum Schließen des Fensters.</li>
 * <li>{@code ALT + a} zum An- und Abschalten der Figuren-Zeichenroutine (Es
 * werden nur die Umrisse gezeichnet, nicht die Füllung).</li>
 * <li>{@code ALT + d} zum An- und Abschalten des Debug-Modus.</li>
 * <li>{@code ALT + p} zum Ein- und Ausblenden der Figuren-Positionen (sehr
 * ressourcenintensiv).</li>
 * <li>{@code ALT + s} zum Speichern eines Bildschirmfotos (unter
 * {@code ~/engine-pi}).</li>
 * <li>{@code ALT + PLUS} Hineinzoomen.</li>
 * <li>{@code ALT + MINUS} Herauszoomen.</li>
 * <li>{@code ALT + SHIFT + PLUS} schnelles Hineinzoomen.</li>
 * <li>{@code ALT + SHIFT + MINUS} schnelles Herauszoomen.</li>
 * <li>{@code ALT + Pfeiltasten} zum Bewegen der Kamera.</li>
 * <li>{@code ALT + Mausrad} zum Einstellen des Zoomfaktors.</li>
 * </ul>
 *
 * @see Controller#defaultControl()
 * @see Controller#defaultControl(DefaultListener)
 * @see Controller#removeDefaultControl()
 * @see DefaultListener
 *
 * @since 0.15.0
 *
 * @author Josef Friedrich
 */
public class DefaultControl implements DefaultListener
{
    private static final double CAMERA_SPEED = 7.0;

    private Camera getCamera()
    {
        Scene scene = Controller.activeScene();
        if (scene != null)
        {
            return scene.camera();
        }
        return null;
    }

    private boolean hasNoScene()
    {
        return Controller.activeScene() == null;
    }

    // Got to
    // file:///home/jf/repos/school/monorepo/inf/java/engine-pi/docs/reference/ereignissteuerung.md

    /**
     * Registriert <b>Standard-Tastenkürzel</b>.
     *
     * <ul>
     * <li>{@code ESCAPE} zum Schließen des Fensters.</li>
     * <li>{@code ALT + a} zum An- und Abschalten der Figuren-Zeichenroutine (Es
     * werden nur die Umrisse gezeichnet, nicht die Füllung).</li>
     * <li>{@code ALT + d} zum An- und Abschalten des Debug-Modus.</li>
     * <li>{@code ALT + p} zum Ein- und Ausblenden der Figuren-Positionen (sehr
     * ressourcenintensiv).</li>
     * <li>{@code ALT + r} zum Ein- oder Ausschalten der Bildschirmaufnahme (in
     * Form von Einzelbildern).</li>
     * <li>{@code ALT + s} zum Speichern eines Bildschirmfotos (unter
     * ~/engine-pi).</li>
     * <li>{@code ALT + PLUS} Hineinzoomen.</li>
     * <li>{@code ALT + MINUS} Herauszoomen.</li>
     * <li>{@code ALT + SHIFT + PLUS} schnelles Hineinzoomen.</li>
     * <li>{@code ALT + SHIFT + MINUS} schnelles Herauszoomen.</li>
     * </ul>
     *
     * @param event Das KeyEvent von AWT.
     */
    @Override
    public void onKeyDown(KeyEvent event)
    {
        if (Controller.isKeyPressed(KeyEvent.VK_ALT))
        {
            double zoomFactor = 0.05;
            if (Controller.isKeyPressed(KeyEvent.VK_SHIFT))
            {
                zoomFactor = 0.2;
            }
            switch (event.getKeyCode())
            {
            case KeyEvent.VK_A -> config.debug.toggleRenderActors();
            case KeyEvent.VK_D -> Controller.toggleDebug();
            case KeyEvent.VK_P -> config.debug.toogleShowPositions();
            case KeyEvent.VK_S -> Controller.takeScreenshot();
            case KeyEvent.VK_R -> Controller.recordScreen();
            case KeyEvent.VK_PLUS -> getCamera().zoomIn(zoomFactor);
            case KeyEvent.VK_MINUS -> getCamera().zoomOut(zoomFactor);
            }
        }
        if (event.getKeyCode() == KeyEvent.VK_ESCAPE)
        {
            Controller.exit();
        }
    }

    /**
     * Bewegt die Kamera, wenn {@code ALT} und die {@code Pfeiltasten} gedrückt
     * werden.
     *
     * @param pastTime Die Zeit <b>in Sekunden</b>, die seit der letzten
     *     Aktualisierung vergangen ist.
     */
    @Override
    public void onFrameUpdate(double pastTime)
    {
        if (hasNoScene())
        {
            return;
        }
        Camera camera = getCamera();
        if (camera == null)
        {
            return;
        }
        if (Controller.isKeyPressed(KeyEvent.VK_ALT))
        {
            double dX = 0, dY = 0;
            if (Controller.isKeyPressed(KeyEvent.VK_UP))
            {
                dY = CAMERA_SPEED * pastTime;
            }
            else if (Controller.isKeyPressed(KeyEvent.VK_DOWN))
            {
                dY = -CAMERA_SPEED * pastTime;
            }
            if (Controller.isKeyPressed(KeyEvent.VK_LEFT))
            {
                dX = -CAMERA_SPEED * pastTime;
            }
            else if (Controller.isKeyPressed(KeyEvent.VK_RIGHT))
            {
                dX = CAMERA_SPEED * pastTime;
            }
            if (dX != 0 || dY != 0)
            {
                camera.moveFocus(dX, dY);
            }
        }
    }

    /**
     * Verändert den Zoomfaktor der Kamera, wenn gleichzeitig {@code ALT} und
     * das Mausrad benutzt wird.
     *
     * @param event Das {@link MouseScrollEvent}-Objekt beschreibt, wie das
     *     Mausrad gedreht wurde.
     */
    @Override
    public void onMouseScrollMove(MouseScrollEvent event)
    {
        if (!Controller.isKeyPressed(KeyEvent.VK_ALT))
        {
            return;
        }
        if (hasNoScene())
        {
            return;
        }
        Camera camera = getCamera();
        if (camera == null)
        {
            return;
        }
        double rotation = event.preciseWheelRotation();
        double factor = rotation > 0 ? 1 + 0.3 * rotation
                : 1 / (1 - 0.3 * rotation);
        double newZoom = camera.meter() * factor;
        if (newZoom <= 0)
        {
            return;
        }
        camera.meter(newZoom);
    }

    public static void main(String[] args)
    {
        Controller.start();
    }
}
