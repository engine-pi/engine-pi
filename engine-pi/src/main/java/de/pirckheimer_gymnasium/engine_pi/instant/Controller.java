package de.pirckheimer_gymnasium.engine_pi.instant;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Scene;
import de.pirckheimer_gymnasium.engine_pi.actor.Actor;

/**
 * Verwaltet eine statische Instanz einer
 * <p>
 * Haupt-Szene
 * </p>
 * , die automatisch gestartet wird und bietet einige statische Hilfsmethoden
 * an, um das Spiel zu steuern.
 *
 * @since 0.33.0
 */
public final class Controller
{
    private static Scene scene;

    /**
     * Gibt die aktuelle <b>Haupt-Szene</b> zurück.
     *
     * <p>
     * Falls noch keine Szene existiert, wird eine neue erstellt und startet das
     * Spiel, falls es noch nicht läuft.
     * </p>
     *
     * @return Die aktuelle <b>Haupt-Szene</b>.
     *
     * @since 0.33.0
     */
    public static Scene getScene()
    {
        if (scene == null)
        {
            scene = new Scene();
        }
        if (!Game.isRunning())
        {
            Game.start(scene);
        }
        return scene;
    }

    /**
     * Fügt der aktuellen Haupt-Szene <b>mehrere Figur auf einmal hinzu.</b>
     *
     * @param actors Der hinzuzufügende Figuren.
     *
     * @since 0.33.0
     */
    public static void addActors(Actor... actors)
    {
        getScene().add(actors);
    }

    /**
     * Fügt der aktuellen Haupt-Szene eine <b>einzelne Figur hinzu.</b>
     *
     * @param actor Der hinzuzufügende Figur.
     *
     * @since 0.33.0
     */
    public static void addActor(Actor actor)
    {
        addActors(actor);
    }
}
