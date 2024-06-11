package de.pirckheimer_gymnasium.engine_pi.event;

import java.awt.event.KeyEvent;

import de.pirckheimer_gymnasium.engine_pi.Camera;
import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Scene;

/**
 * Registriert einige grundlegenden Kontrollmöglichkeiten.
 *
 * <p>
 * Beispielsweise werden Tastenkürzel registriert, die standardmäßig mit der
 * Engine mitgeliefert werden (z. B. ESC zum Schließen des Fensters, Alt+d zum
 * An- und Ausschalten des Debug-Modus, Alt+Pfeiltasten zum Bewegen der Kamera,
 * Alt+Mausrad zum Einstellen des Zoomfaktors.).
 * </p>
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

    @Override
    public void onKeyDown(KeyEvent e)
    {
        switch (e.getKeyCode())
        {
        case KeyEvent.VK_D ->
        {
            if (Game.isKeyPressed(KeyEvent.VK_ALT))
            {
                Game.toggleDebug();
            }
        }
        case KeyEvent.VK_ESCAPE ->
        {
            Game.exit();
        }
        }
    }

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

    @Override
    public void onMouseWheelMove(MouseWheelEvent event)
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
