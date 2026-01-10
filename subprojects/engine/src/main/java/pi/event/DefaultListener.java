package pi.event;

import java.awt.event.KeyEvent;

import pi.graphics.geom.Vector;

/**
 * Bietet Standard-Methoden für einige Beobachter-Schnittstellen an.
 *
 * <p>
 * Wird von der Klasse {@link DefaultControl} implementiert.
 * </p>
 *
 * @see DefaultControl
 *
 * @author Josef Friedrich
 *
 * @since 0.15.0
 */
public interface DefaultListener extends FrameUpdateListener, KeyStrokeListener,
        MouseClickListener, MouseScrollListener
{
    default void onFrameUpdate(double pastTime)
    {
        // Standardmäßig leer.
    }

    default void onKeyDown(KeyEvent event)
    {
        // Standardmäßig leer.
    }

    default void onMouseDown(Vector position, MouseButton button)
    {
        // Standardmäßig leer.
    }

    default void onMouseScrollMove(MouseScrollEvent event)
    {
        // Standardmäßig leer.
    }
}
