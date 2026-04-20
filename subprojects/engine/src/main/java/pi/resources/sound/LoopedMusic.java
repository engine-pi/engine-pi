/*
 * Source: https://github.com/gurkenlabs/litiengine/blob/main/litiengine/src/main/java/de/gurkenlabs/litiengine/sound/LoopedTrack.java
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
import java.util.Objects;
import javax.sound.sampled.AudioFormat;

import pi.debug.ToStringFormatter;

/**
 * @author Steffen Wilke
 * @author Matthias Wilke
 * @author Josef Friedrich
 */
public class LoopedMusic implements Music, Iterator<Sound>
{
    private final Sound sound;

    /**
     * Initializes a new {@link LoopedMusic} for the specified sound.
     *
     * @param sound The sound to be played by this music.
     */
    public LoopedMusic(Sound sound)
    {
        this.sound = Objects.requireNonNull(sound);
    }

    @Override
    public Iterator<Sound> iterator()
    {
        return this;
    }

    @Override
    public AudioFormat format()
    {
        return this.sound.format();
    }

    // implement the iterator here to avoid allocating new objects
    // they don't have any state data anyway
    @Override
    public boolean hasNext()
    {
        return true;
    }

    @Override
    public Sound next()
    {
        return sound;
    }

    /**
     * @hidden
     */
    @Override
    public boolean equals(Object other)
    {
        return this == other || other instanceof LoopedMusic music
                && music.sound.equals(sound);
    }

    /**
     * @hidden
     */
    @Override
    public int hashCode()
    {
        return sound.hashCode();
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
