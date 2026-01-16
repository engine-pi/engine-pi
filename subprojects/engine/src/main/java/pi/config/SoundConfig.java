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

import pi.annotations.Getter;
import pi.annotations.Setter;

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
     * Constructs a new SoundConfiguration with default volume settings.
     */
    SoundConfig()
    {
        this.soundVolume(0.5);
        this.musicVolume(0.5);
    }

    /* soundVolume */

    private double soundVolume;

    /**
     * Gets the current sound volume.
     *
     * @return the sound volume.
     */
    @Getter
    public double soundVolume()
    {
        return soundVolume;
    }

    /**
     * Sets the sound volume.
     *
     * @param soundVolume the new sound volume.
     */
    @Setter
    public void soundVolume(final double soundVolume)
    {
        set("soundVolume", soundVolume);
    }

    /* musicVolume */

    private double musicVolume;

    /**
     * Gets the current music volume.
     *
     * @return the music volume.
     */
    @Getter
    public double musicVolume()
    {
        return musicVolume;
    }

    /**
     * Sets the music volume.
     *
     * @param musicVolume the new music volume.
     */
    @Setter
    public void musicVolume(final double musicVolume)
    {
        set("musicVolume", musicVolume);
    }
}
