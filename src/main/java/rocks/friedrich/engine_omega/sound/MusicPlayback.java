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
package rocks.friedrich.engine_omega.sound;

import javax.sound.sampled.LineUnavailableException;

/**
 * A {@code SoundPlayback} implementation for the playback music.
 */
public class MusicPlayback extends Playback
{
    private final Track track;

    private final VolumeControl musicVolume;

    MusicPlayback(Track track) throws LineUnavailableException
    {
        super(track.getFormat());
        this.track = track;
        this.musicVolume = this.createVolumeControl();
        this.musicVolume.set(1);
    }

    @Override
    public void run()
    {
        try
        {
            for (Sound sound : this.track)
            {
                if (this.play(sound))
                {
                    return;
                }
            }
        }
        catch (Throwable t)
        {
            t.printStackTrace();
        }
        finally
        {
            this.finish();
        }
    }

    public Track getTrack()
    {
        return this.track;
    }

    void setMusicVolume(float volume)
    {
        this.musicVolume.set(volume);
    }
}
