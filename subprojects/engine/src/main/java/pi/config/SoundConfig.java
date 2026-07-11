/*
 * Source: https://github.com/gurkenlabs/litiengine/blob/main/litiengine/src/main/java/de/gurkenlabs/litiengine/configuration/SoundConfiguration.java
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

import pi.annotations.API;
import pi.annotations.ChainableMethod;
import pi.annotations.Getter;
import pi.annotations.Setter;

// Go to file:///data/school/repos/inf/java/engine-pi/docs/manual/resources/config.md

/**
 * Verwaltet die <b>Audio</b>-Einstellungsmöglichkeiten.
 *
 * @author Steffen Wilke
 * @author Matthias Wilke
 * @author Josef Friedrich
 *
 * @since 0.42.0
 */
@ConfigGroupInfo(prefix = "sound_")
public class SoundConfig extends ConfigGroup
{
    /**
     * Erzeugt eine neue {@code SoundConfig} mit den Standardwerten für die
     * Lautstärke.
     */
    SoundConfig()
    {
        super();
        // Der Konstruktor sollte nicht auf „public“ gesetzt werden, sondern
        // „package private“ bleiben, damit die Konfigurationsgruppe nur in
        // diesem Paket instanziert werden kann.
        soundVolume(0.5);
        musicVolume(0.5);
    }

    /* soundVolume */

    private double soundVolume;

    /**
     * Gibt die aktuelle <b>Lautstärke für Soundeffekte</b> zurück.
     *
     * @return Die <b>Lautstärke für Soundeffekte</b>.
     */
    @API
    @Getter
    public double soundVolume()
    {
        return soundVolume;
    }

    /**
     * Setzt die <b>Lautstärke für Soundeffekte</b>.
     *
     * @param soundVolume Die neue <b>Lautstärke für Soundeffekte</b>.
     */
    @API
    @Setter
    @ChainableMethod
    public SoundConfig soundVolume(final double soundVolume)
    {
        set("soundVolume", soundVolume);
        return this;
    }

    /* musicVolume */

    private double musicVolume;

    /**
     * Gibt die aktuelle <b>Musiklautstärke</b> zurück.
     *
     * @return Die <b>Musiklautstärke</b>.
     */
    @API
    @Getter
    public double musicVolume()
    {
        return musicVolume;
    }

    /**
     * Setzt die <b>Musiklautstärke</b>.
     *
     * @param musicVolume Die neue <b>Musiklautstärke</b>.
     */
    @API
    @Setter
    @ChainableMethod
    public SoundConfig musicVolume(final double musicVolume)
    {
        set("musicVolume", musicVolume);
        return this;
    }
}
