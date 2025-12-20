package pi.instant;

import pi.Camera;
import pi.Game;
import pi.Scene;
import pi.actor.Actor;

/**
 * <b>Steuert</b> die Engine Pi im <b>Instant-Modus</b>.
 *
 * <p>
 * Die Klasse verwaltet eine statische Instanz der <b>Haupt-Szene</b>, die
 * automatisch gestartet wird und bietet einige statische Hilfsmethoden an, um
 * das Spiel im Instant-Modus zu steuern.
 * </p>
 *
 * @since 0.33.0
 */
public final class Controller
{
    private static Scene scene;

    /**
     * Gibt die aktuelle <b>Hauptszene</b> zurück.
     *
     * <p>
     * Falls noch keine Szene existiert, wird eine neue erstellt und startet das
     * Spiel, falls es noch nicht läuft.
     * </p>
     *
     * @return Die aktuelle <b>Hauptszene</b>.
     *
     * @since 0.33.0
     */
    public static Scene getMainScene()
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
     * Gibt die <b>Kamera</b> der Hauptszene zurück.
     *
     * @return Die Kamera der Hauptszene.
     *
     * @since 0.33.0
     */
    public static Camera getCamera()
    {
        return getMainScene().getCamera();
    }

    /**
     * Fügt der aktuellen Hauptszene <b>mehrere Figur auf einmal hinzu.</b>
     *
     * @param actors Der hinzuzufügende Figuren.
     *
     * @since 0.33.0
     */
    public static void addActors(Actor... actors)
    {
        getMainScene().add(actors);
    }

    /**
     * Fügt der aktuellen Hauptszene eine <b>einzelne Figur hinzu.</b>
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
