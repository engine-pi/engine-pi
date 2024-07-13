package de.pirckheimer_gymnasium.engine_pi.actor;

import java.awt.image.BufferedImage;

/**
 * Eine animierte Figur mit mehreren Zuständen, deren Animationen durch Angabe
 * der <b>Einzelbilder</b> erzeugt wird.
 *
 * @param width         Die Breite in Meter der animierten Figur.
 * @param height        Die Höhe in Meter der animierten Figur.
 * @param frameDuration Die Dauer in Sekunden, die die Einzelbilder aktiv
 *                      bleiben.
 *
 * @param <State>       Typ der Zustände, zwischen denen in der Animation
 *                      gewechselt werden soll.
 *
 * @author Josef Friedrich
 *
 * @since 0.26.0
 */
public class StatefulImagesAnimation<State> extends StatefulAnimation<State>
{
    public StatefulImagesAnimation(double width, double height,
            double frameDuration)
    {
        super(width, height, frameDuration);
    }

    /**
     * Fügt der Animation einen neuen Zustand hinzu.
     *
     * @param state         Der Zustand, unter dem die Animation gespeichert
     *                      wird.
     * @param frameDuration Die Dauer in Sekunden, die die Einzelbilder aktiv
     *                      bleiben.
     * @param images        Die bereits in den Speicher geladenen Bilder, die
     *                      als Einzelbilder verwendet werden sollen.
     */
    public void addState(State state, double frameDuration,
            BufferedImage... images)
    {
        addState(state, Animation.createFromImages(frameDuration, width, height,
                images));
    }

    /**
     * Fügt der Animation einen neuen Zustand hinzu.
     *
     * @param state  Der Zustand, unter dem die Animation gespeichert wird.
     * @param images Die bereits in den Speicher geladenen Bilder, die als
     *               Einzelbilder verwendet werden sollen.
     */
    public void addState(State state, BufferedImage... images)
    {
        addState(state, frameDuration, images);
    }

    /**
     * Fügt der Animation einen neuen Zustand hinzu.
     *
     * @param state         Der Zustand, unter dem die Animation gespeichert
     *                      wird.
     * @param frameDuration Die Dauer in Sekunden, die die Einzelbilder aktiv
     *                      bleiben.
     * @param filePaths     Die einzelnen Dateipfade der zu verwendenden
     *                      Einzelbilder.
     */
    public void addState(State state, double frameDuration, String... filePaths)
    {
        addState(state, Animation.createFromImages(frameDuration, width, height,
                filePaths));
    }

    /**
     * Fügt der Animation einen neuen Zustand hinzu.
     *
     * @param state     Der Zustand, unter dem die Animation gespeichert wird.
     * @param filePaths Die einzelnen Dateipfade der zu verwendenden
     *                  Einzelbilder.
     */
    public void addState(State state, String... filePaths)
    {
        addState(state, frameDuration, filePaths);
    }
}
