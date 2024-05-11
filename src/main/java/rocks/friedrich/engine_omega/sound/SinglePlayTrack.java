/**
 * Source: https://github.com/gurkenlabs/litiengine/blob/main/litiengine/src/main/java/de/gurkenlabs/litiengine/sound/SinglePlayTrack.java
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

import java.util.Iterator;
import java.util.NoSuchElementException;

import javax.sound.sampled.AudioFormat;

/**
 * A {@code Track} that plays a sound once and then stops.
 */
public class SinglePlayTrack implements Track
{
    private Sound sound;

    private class Iter implements Iterator<Sound>
    {
        private boolean hasNext = true;

        @Override
        public boolean hasNext()
        {
            return this.hasNext;
        }

        @Override
        public Sound next()
        {
            if (!this.hasNext)
            {
                throw new NoSuchElementException();
            }
            this.hasNext = false;
            return SinglePlayTrack.this.sound;
        }
    }
    /**
     * Initializes a new {@code SinglePlayTrack} for the specified sound.
     *
     * @param soundName The name of the sound to be played by this track.
     */
    // public SinglePlayTrack(String soundName) {
    // this();
    // }

    /**
     * Initializes a new {@code SinglePlayTrack} for the specified sound.
     *
     * @param sound The sound to be played by this track.
     */
    public SinglePlayTrack(Sound sound)
    {
        this.sound = sound;
    }

    @Override
    public Iterator<Sound> iterator()
    {
        return new Iter();
    }

    @Override
    public AudioFormat getFormat()
    {
        return this.sound.getFormat();
    }

    @Override
    public boolean equals(Object obj)
    {
        return obj instanceof SinglePlayTrack spt && this.sound == spt.sound;
    }

    @Override
    public int hashCode()
    {
        // add a constant to avoid collisions with LoopedTrack
        return this.sound.hashCode() + 0xdb9857d0;
    }

    @Override
    public String toString()
    {
        return "track: " + this.sound.getName() + " (not looped)";
    }
}
