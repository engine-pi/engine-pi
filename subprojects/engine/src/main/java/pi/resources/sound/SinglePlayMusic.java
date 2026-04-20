/*
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
package pi.resources.sound;

import java.util.Iterator;
import java.util.NoSuchElementException;

import javax.sound.sampled.AudioFormat;

import pi.Controller;
import pi.annotations.Getter;
import pi.debug.ToStringFormatter;

/**
 * A {@link Music} that plays a sound once and then stops.
 *
 * @author Steffen Wilke
 * @author Matthias Wilke
 * @author Josef Friedrich
 */
public class SinglePlayMusic implements Music
{
    private Sound sound;

    private class Iter implements Iterator<Sound>
    {
        private boolean hasNext = true;

        @Override
        public boolean hasNext()
        {
            return hasNext;
        }

        @Override
        public Sound next()
        {
            if (!hasNext)
            {
                throw new NoSuchElementException();
            }
            hasNext = false;
            return SinglePlayMusic.this.sound;
        }
    }

    /**
     * Initializes a new {@link SinglePlayMusic} for the specified sound.
     *
     * @param filePath The name of the sound to be played by this music.
     */
    public SinglePlayMusic(String filePath)
    {
        this(Controller.sounds.get(filePath));
    }

    /**
     * Initializes a new {@link SinglePlayMusic} for the specified sound.
     *
     * @param sound The sound to be played by this music.
     */
    public SinglePlayMusic(Sound sound)
    {
        this.sound = sound;
    }

    @Override
    public Iterator<Sound> iterator()
    {
        return new Iter();
    }

    @Getter
    @Override
    public AudioFormat format()
    {
        return sound.format();
    }

    /**
     * @hidden
     */
    @Override
    public boolean equals(Object other)
    {
        return other instanceof SinglePlayMusic music && sound == music.sound;
    }

    /**
     * @hidden
     */
    @Override
    public int hashCode()
    {
        // add a constant to avoid collisions with LoopedMusic
        return sound.hashCode() + 0xdb9857d0;
    }

    /**
     * @hidden
     */
    @Override
    public String toString()
    {
        var formatter = new ToStringFormatter(this);
        formatter.append("sound", sound);
        return formatter.format();
    }
}
