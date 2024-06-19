package de.pirckheimer_gymnasium.engine_pi.event;

import de.pirckheimer_gymnasium.engine_pi.Vector;

import java.awt.event.KeyEvent;

/**
 * Bietet Standard-Methoden für einige Beobachter-Schnittstellen an.
 *
 * <p>
 * Wird von der Klasse {@link DefaultControl} implementiert.
 * </p>
 *
 * @see DefaultControl
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
