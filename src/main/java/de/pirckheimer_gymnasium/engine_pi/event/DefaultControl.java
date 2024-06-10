package de.pirckheimer_gymnasium.engine_pi.event;

import java.awt.event.KeyEvent;

import de.pirckheimer_gymnasium.engine_pi.Camera;
import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Scene;

/**
 * Registriert einige grundlegende Kontrollmöglichkeiten, d. h. einige
 * Tastenkürzel, die standardmäßig mit der Engine mitgeliefert werden (z. B. ESC
 * zum Schließen des Fensters, STRG+D zum An- und Ausschalten des Debug-Modus).
 */
public class DefaultControl
        implements KeyListener, FrameUpdateListener, MouseWheelListener
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

    private boolean hasScene()
    {
        return Game.getActiveScene() != null;
    }

    @Override
    public void onKeyDown(KeyEvent e)
    {
        switch (e.getKeyCode())
        {
        case KeyEvent.VK_D ->
        {
            if (Game.isKeyPressed(KeyEvent.VK_CONTROL))
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
        if (!hasScene())
        {
            return;
        }
        if (Game.isKeyPressed(KeyEvent.VK_ALT))
        {
            // Smooth Camera Movement
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
                getCamera().moveBy(dX, dY);
            }
        }
    }

    @Override
    public void onMouseWheelMove(MouseWheelEvent event)
    {
        if (!hasScene())
        {
            return;
        }
        double rotation = event.getPreciseWheelRotation();
        double factor = rotation > 0 ? 1 + 0.3 * rotation
                : 1 / (1 - 0.3 * rotation);
        double newZoom = getCamera().getMeter() * factor;
        if (newZoom <= 0)
        {
            return;
        }
        getCamera().setMeter(newZoom);
    }
}
