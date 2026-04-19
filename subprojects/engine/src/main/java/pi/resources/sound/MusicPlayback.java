/*
 * Source: https://github.com/gurkenlabs/litiengine/blob/main/litiengine/src/main/java/de/gurkenlabs/litiengine/sound/MusicPlayback.java
 *
 * MIT License
 *
 * Copyright (c) 2016 - 2024 Gurkenlabs
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
package pi.resources.sound;

import javax.sound.sampled.LineUnavailableException;

import pi.annotations.Getter;

/**
 * Ermöglicht die Steuerung der Musikwiedergabe.
 */
public class MusicPlayback extends Playback
{

    private final VolumeControl musicVolume;

    public MusicPlayback(Track track) throws LineUnavailableException
    {
        super(track.format());
        this.track = track;
        musicVolume = createVolumeControl();
        musicVolume.set(1);
    }

    @Override
    public void run()
    {
        try
        {
            for (Sound sound : track)
            {
                if (play(sound))
                {
                    return;
                }
            }
        }
        catch (Exception e)
        {
            throw new IllegalArgumentException(e);
        }
        finally
        {
            this.finish();
        }
    }

    private final Track track;

    @Getter
    public Track track()
    {
        return track;
    }

    void setMusicVolume(float volume)
    {
        musicVolume.set(volume);
    }

    /**
     * @hidden
     */
    @Override
    public String toString()
    {
        var formatter = super.toStringFormatter(this);
        formatter.prepend("track", track);
        return formatter.format();
    }

}
