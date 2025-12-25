/**
 * Eine <b>Ereignissteuerung</b>, die über das <b>Beobachter-Entwurfsmuster</b>
 * realisiert ist.
 *
 * <p>
 * In der Fachliteratur wird das <a href=
 * "https://de.wikipedia.org/wiki/Beobachter_(Entwurfsmuster)">Beobachter-Muster</a>
 * auch oft
 * <a href="https://en.wikipedia.org/wiki/Observer_pattern"><em>observer</em>
 * bzw. <em>listener pattern</em></a> genannt.
 * </p>
 *
 * <p>
 * Zu den Ereignissen gehören Maus- und Tastatureingaben und Ereignisse, wenn
 * ein neues Frame gerendert wurde.
 * </p>
 *
 * <h2>Ein Überblick über alle Beobachter</h2>
 *
 * <ul>
 * <li>Collision: {@link pi.event.CollisionListener}</li>
 * <li>FrameUpdate: {@link pi.event.FrameUpdateListener}</li>
 * <li>KeyStroke: {@link pi.event.KeyStrokeListener}</li>
 * <li>MouseClick: {@link pi.event.MouseClickListener}</li>
 * <li>MouseScroll: {@link pi.event.MouseScrollListener}</li>
 * <li>SceneLaunch: {@link pi.event.SceneLaunchListener}</li>
 * </ul>
 */
package pi.event;
