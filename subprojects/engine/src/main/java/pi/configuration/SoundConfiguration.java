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
package pi.configuration;

/**
 * @author Steffen Wilke
 * @author Matthias Wilke
 *
 * @since 0.42.0
 */
@ConfigurationGroupInfo(prefix = "sound_")
public class SoundConfiguration extends ConfigurationGroup
{

    private float musicVolume;

    private float soundVolume;

    /**
     * Constructs a new SoundConfiguration with default volume settings.
     */
    SoundConfiguration()
    {
        this.setSoundVolume(0.5f);
        this.setMusicVolume(0.5f);
    }

    /**
     * Gets the current music volume.
     *
     * @return the music volume.
     */
    public float getMusicVolume()
    {
        return this.musicVolume;
    }

    /**
     * Gets the current sound volume.
     *
     * @return the sound volume.
     */
    public float getSoundVolume()
    {
        return this.soundVolume;
    }

    /**
     * Sets the music volume.
     *
     * @param musicVolume the new music volume.
     */
    public void setMusicVolume(final float musicVolume)
    {
        this.set("musicVolume", musicVolume);
    }

    /**
     * Sets the sound volume.
     *
     * @param soundVolume the new sound volume.
     */
    public void setSoundVolume(final float soundVolume)
    {
        this.set("soundVolume", soundVolume);
    }
}
