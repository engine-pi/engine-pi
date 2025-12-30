package pi.event;

import java.awt.event.KeyEvent;

import pi.Camera;
import pi.Game;
import pi.Scene;
import pi.debug.DebugConfiguration;

/**
 * Registriert im Auslieferungszustand einige wenige <b>grundlegenden Maus- und
 * Tastatur-Steuermöglichkeiten</b>.
 *
 * <p>
 * Diese sind hoffentlich beim Entwickeln hilfreich. Mit den statischen Methoden
 * {@link Game#removeDefaultControl()} können diese Kürzel entfernt oder mit
 * {@link Game#setDefaultControl(DefaultListener)} neue Kürzel gesetzt werden.
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
 * @see Game#getDefaultControl()
 * @see Game#setDefaultControl(DefaultListener)
 * @see Game#removeDefaultControl()
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
        if (Game.isKeyPressed(KeyEvent.VK_ALT))
        {
            double zoomFactor = 0.05;
            if (Game.isKeyPressed(KeyEvent.VK_SHIFT))
            {
                zoomFactor = 0.2;
            }
            switch (event.getKeyCode())
            {
            case KeyEvent.VK_A -> Game.toggleRenderActors();
            case KeyEvent.VK_D -> Game.toggleDebug();
            case KeyEvent.VK_P -> DebugConfiguration.toogleShowPositions();
            case KeyEvent.VK_S -> Game.takeScreenshot();
            case KeyEvent.VK_R -> Game.recordScreen();
            case KeyEvent.VK_PLUS -> getCamera().zoomIn(zoomFactor);
            case KeyEvent.VK_MINUS -> getCamera().zoomOut(zoomFactor);
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
        if (Game.isKeyPressed(KeyEvent.VK_ALT))
        {
            double dX = 0, dY = 0;
            if (Game.isKeyPressed(KeyEvent.VK_UP))
            {
                dY = CAMERA_SPEED * pastTime;
            }
            else if (Game.isKeyPressed(KeyEvent.VK_DOWN))
            {
                dY = -CAMERA_SPEED * pastTime;
            }
            if (Game.isKeyPressed(KeyEvent.VK_LEFT))
            {
                dX = -CAMERA_SPEED * pastTime;
            }
            else if (Game.isKeyPressed(KeyEvent.VK_RIGHT))
            {
                dX = CAMERA_SPEED * pastTime;
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
     *     Mausrad gedreht wurde.
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
