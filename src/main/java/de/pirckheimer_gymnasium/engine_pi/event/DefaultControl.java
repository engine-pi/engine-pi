package de.pirckheimer_gymnasium.engine_pi.event;

import java.awt.event.KeyEvent;

import de.pirckheimer_gymnasium.engine_pi.Camera;
import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Scene;

/**
 * Registriert wenige grundlegenden Maus- und Tastatur-Steuermöglichkeiten.
 *
 * <ul>
 * <li>{@code ESCAPE} zum Schließen des Fensters.</li>
 * <li>{@code ALT + a} zum An- und Ausschalten der Figuren-Zeichenroutine.</li>
 * <li>{@code ALT + d} zum An- und Ausschalten des Debug-Modus.</li>
 * <li>{@code ALT + s} zum Speichern eines Bildschirmfotos.</li>
 * <li>{@code ALT + Pfeiltasten} zum Bewegen der Kamera.</li>
 * <li>{@code ALT + Mausrad} zum Einstellen des Zoomfaktors.</li>
 * </ul>
 *
 * @see Game#getDefaultControl()
 * @see Game#setDefaultControl(DefaultListener)
 * @see Game#removeDefaultControl()
 */
public class DefaultControl implements DefaultListener
{
    private static final double CAMERA_SPEED = 7.0;

    private Camera getCamera()
    {
        Scene scene = Game.getActiveScene();
        if (scene != null)
        {
            return scene.getCamera();
        }
        return null;
    }

    private boolean hasNoScene()
    {
        return Game.getActiveScene() == null;
    }

    /**
     * Registriert <b>Standard-Tastenkürzel</b>.
     *
     * <ul>
     * <li>{@code ALT + a} zum An- und Ausschalten der
     * Figuren-Zeichenroutine.</li>
     * <li>{@code ALT + d} zum An- und Ausschalten des Debug-Modus.</li>
     * <li>{@code ALT + s} zum Speichern eines Bildschirmfotos.</li>
     * <li>{@code ESCAPE} zum Schließen des Fensters.</li>
     * </ul>
     *
     * @param event Das KeyEvent von AWT.
     */
    @Override
    public void onKeyDown(KeyEvent event)
    {
        if (Game.isKeyPressed(KeyEvent.VK_ALT))
        {
            switch (event.getKeyCode())
            {
            case KeyEvent.VK_D ->
            {
                Game.toggleDebug();
            }
            case KeyEvent.VK_A ->
            {
                Game.toggleRenderActors();
            }
            case KeyEvent.VK_S ->
            {
                Game.takeScreenshot();
            }
            }
        }
        if (event.getKeyCode() == KeyEvent.VK_ESCAPE)
        {
            Game.exit();
        }
    }

    /**
     * Bewegt die Kamera, wenn {@code ALT} und die {@code Pfeiltasten} gedrückt
     * werden.
     *
     * @param delta Die Zeit <b>in Sekunden</b>, die seit der letzten
     *              Aktualisierung vergangen ist.
     */
    @Override
    public void onFrameUpdate(double delta)
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
        if (Game.isKeyPressed(KeyEvent.VK_ALT))
        {
            double dX = 0, dY = 0;
            if (Game.isKeyPressed(KeyEvent.VK_UP))
            {
                dY = CAMERA_SPEED * delta;
            }
            else if (Game.isKeyPressed(KeyEvent.VK_DOWN))
            {
                dY = -CAMERA_SPEED * delta;
            }
            if (Game.isKeyPressed(KeyEvent.VK_LEFT))
            {
                dX = -CAMERA_SPEED * delta;
            }
            else if (Game.isKeyPressed(KeyEvent.VK_RIGHT))
            {
                dX = CAMERA_SPEED * delta;
            }
            if (dX != 0 || dY != 0)
            {
                camera.moveBy(dX, dY);
            }
        }
    }

    /**
     * Verändert den Zoomfaktor der Kamera, wenn gleichzeitig {@code ALT} und
     * das Mausrad benutzt wird.
     *
     * @param event Das {@link MouseScrollEvent}-Objekt beschreibt, wie das
     *              Mausrad gedreht wurde.
     */
    @Override
    public void onMouseScrollMove(MouseScrollEvent event)
    {
        if (!Game.isKeyPressed(KeyEvent.VK_ALT))
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
        double rotation = event.getPreciseWheelRotation();
        double factor = rotation > 0 ? 1 + 0.3 * rotation
                : 1 / (1 - 0.3 * rotation);
        double newZoom = camera.getMeter() * factor;
        if (newZoom <= 0)
        {
            return;
        }
        camera.setMeter(newZoom);
    }
}
