package de.pirckheimer_gymnasium.engine_pi.event;

import de.pirckheimer_gymnasium.engine_pi.Scene;
import de.pirckheimer_gymnasium.engine_pi.annotations.API;

/**
 * Ein Beobachter, der auf Szenenwechsel reagiert.
 *
 * @author Josef Friedrich
 */
public interface SceneLaunchListener
{
    /**
     * @param next     Die nächste Szene, die gestartet werden soll.
     * @param previous Die vorhergehende Szene, die durch die nächste Szene
     *                 ersetzt werden soll. Wird das Spiel gestartet, so ist
     *                 dieser Parameter {@code null}.
     */
    @API
    void onSceneLaunch(Scene next, Scene previous);
}
