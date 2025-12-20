package pi.instant;

import java.awt.Color;

import pi.Camera;
import pi.Scene;
import pi.actor.Actor;

/**
 * Reichert alle Instant-Figuren mit zusätzlichen Methoden an.
 *
 * @since 0.33.0
 */
public interface InstantActor
{

    Actor getActor();

    Scene getMainScene();

    /**
     * Gibt die <b>Kamera</b> der Hauptszene zurück.
     *
     * @return Die Kamera der Hauptszene.
     *
     * @since 0.33.0
     */
    default Camera getCamera()
    {
        return getMainScene().getCamera();
    }

    /**
     * Setzt den Fokus der Kamera auf den aktuelle Figur und gibt diese zurück.
     *
     * @return Die Figur, auf die der Fokus gesetzt wurde.
     *
     * @since 0.33.0
     */
    default Actor focus()
    {
        Actor actor = getActor();
        getCamera().setFocus(actor);
        return actor;
    }

    /**
     * Setzt die <b>Hintergrundfarbe</b> der Hauptszene durch Angabe eines
     * {@link Color}-Objekts.
     *
     * @param color Die Hintergrundfarbe.
     *
     * @since 0.33.0
     */
    default Actor setMainSceneBackgroundColor(Color color)
    {
        Actor actor = getActor();
        getMainScene().setBackgroundColor(color);
        return actor;
    }

    /**
     * Setzt die <b>Hintergrundfarbe</b> der Hauptszene als <b>Zeichenkette</b>.
     *
     * @param color Ein Farbname, ein Farbalias
     *     ({@link pi.resources.ColorContainer siehe Auflistung}) oder eine
     *     Farbe in hexadezimaler Codierung (z. B. {@code #ff0000}).
     *
     * @see pi.resources.ColorContainer#get(String)
     *
     * @since 0.33.0
     */
    default Actor setMainSceneBackgroundColor(String color)
    {
        Actor actor = getActor();
        getMainScene().setBackgroundColor(color);
        return actor;
    }

}
