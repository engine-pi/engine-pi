/**
 * Eine Ereignissteuerung, die über das Beobachter-Muster realisiert ist.
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
 * <li>Collision:
 * {@link de.pirckheimer_gymnasium.engine_pi.event.CollisionListener}</li>
 * <li>FrameUpdate:
 * {@link de.pirckheimer_gymnasium.engine_pi.event.FrameUpdateListener}</li>
 * <li>KeyStroke:
 * {@link de.pirckheimer_gymnasium.engine_pi.event.KeyStrokeListener}</li>
 * <li>MouseClick:
 * {@link de.pirckheimer_gymnasium.engine_pi.event.MouseClickListener}</li>
 * <li>MouseWheel:
 * {@link de.pirckheimer_gymnasium.engine_pi.event.MouseWheelListener}</li>
 * <li>SceneLaunch:
 * {@link de.pirckheimer_gymnasium.engine_pi.event.SceneLaunchListener}</li>
 * </ul>
 */
package de.pirckheimer_gymnasium.engine_pi.event;
