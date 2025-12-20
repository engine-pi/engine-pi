package pi.event;

/**
 * Bündelt alle Arten von Beobachtern.
 *
 * TODO Implementiere
 *
 * <p>
 * Diese Klasse ist gedacht für ein Attribut {@code listeners} der Klassen
 * {@link pi.Game}, {@link pi.Scene}, {@link pi.Layer}, {@link pi.actor.Actor}.
 * In dieser Klasse werden für alle möglichen Arten von Beobachter Instanzen zur
 * Verwaltung erstellt, auch wenn sie gar nicht genutzt werden. Dieser kleine
 * Overhead wird in Kauf genommen.
 * </p>
 */
public class EventListenerBundle
{
    public final EventListeners<CollisionListener<?>> collision;

    public final EventListeners<FrameUpdateListener> frameUpdate;

    public final EventListeners<KeyStrokeListener> keyStroke;

    public final EventListeners<Runnable> mount;

    public final EventListeners<MouseClickListener> mouseClick;

    public final EventListeners<MouseScrollListener> mouseScroll;

    public final EventListeners<SceneLaunchListener> sceneLaunch;

    public final EventListeners<Runnable> unmount;

    /**
     * Initialisiert die Speicher für alle Beobachter.
     */
    public EventListenerBundle()
    {
        collision = new EventListeners<>();
        frameUpdate = new EventListeners<>();
        keyStroke = new EventListeners<>();
        mount = new EventListeners<>();
        mouseClick = new EventListeners<>();
        mouseScroll = new EventListeners<>();
        sceneLaunch = new EventListeners<>();
        unmount = new EventListeners<>();
    }
}
