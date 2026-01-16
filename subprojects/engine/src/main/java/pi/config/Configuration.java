/*
 * Source: https://github.com/gurkenlabs/litiengine/blob/main/litiengine/src/main/java/de/gurkenlabs/litiengine/configuration/GameConfiguration.java
 *
 * MIT License
 *
 * Copyright (c) 2016 - 2025 Gurkenlabs
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package pi.config;

/**
 * Diese Klasse enthält alle Standard-{@link ConfigurationGroup
 * Konfigurationsgruppen}, die von der <b>Engine</b> Pi bereitgestellt werden.
 *
 * <p>
 * Darüber hinaus kann diese Klasse zum Registrieren und Verwalten von
 * benutzerdefinierten {@link ConfigurationGroup Konfigurationsgruppen}
 * verwendet werden.
 * </p>
 *
 * @see ConfigurationGroup
 * @see ConfigurationLoader#add(ConfigurationGroup...)
 * @see ConfigurationLoader#getConfigurationGroup(Class)
 *
 * @author Steffen Wilke
 * @author Matthias Wilke
 * @author Josef Friedrich
 *
 * @since 0.42.0
 */
public class Configuration extends ConfigurationLoader
{
    /**
     * Verwaltet die Einstellungsmöglichkeiten mit Bezug zu einem <b>Spiel</b>
     * oder Projekt.
     */
    public final GameConfiguration game;

    /**
     * Verwaltet die <b>grafischen</b> Einstellungsmöglichkeiten.
     */
    public final GraphicsConfiguration graphics;

    /**
     * Verwaltet die <b>Audio</b>-Einstellungsmöglichkeiten.
     */
    public final SoundConfiguration sound;

    /**
     * Verwaltet die Einstellmöglichkeiten mit Bezug zum
     * <b>Entwicklungs</b>modus.
     */
    public final DebugConfiguration debug;

    /**
     * Verwaltet die Einstellungsmöglichkeiten, wie das <b>Koordinatensystem</b>
     * im Entwicklungsmodus gezeichnet werden soll.
     */
    public final CoordinatesystemConfiguration coordinatesystem;

    private static Configuration configuration;

    /**
     * Der Konstruktor ist auf privat gesetzt, damit nach dem
     * Singleton/Einzelner-Entwurfsmuster nur eine Instanz erzeugt werden kann.
     */
    private Configuration()
    {
        super();
        game = new GameConfiguration();
        graphics = new GraphicsConfiguration();
        sound = new SoundConfiguration();
        debug = new DebugConfiguration();
        coordinatesystem = new CoordinatesystemConfiguration();
        add(game, graphics, sound, debug, coordinatesystem);
    }

    /**
     * <b>Gibt</b> die Singleton/Einzelner-Instanz der Konfiguration zurück.
     *
     * <p>
     * Falls noch keine Instanz existiert, wird diese erstellt und geladen.
     * </p>
     *
     * @return Das global Konfigurationsobjekt.
     *
     * @since 0.42.0
     */
    public static Configuration getInstance()
    {
        if (configuration == null)
        {
            configuration = new Configuration();
            configuration.load();
        }
        return configuration;
    }
}
