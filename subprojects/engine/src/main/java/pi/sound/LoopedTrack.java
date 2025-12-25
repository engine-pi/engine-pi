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
package pi.sound;

import java.util.Iterator;
import java.util.Objects;
import javax.sound.sampled.AudioFormat;

public class LoopedTrack implements Track, Iterator<Sound>
{
    private final Sound track;

    /**
     * Initializes a new {@code LoopedTrack} for the specified sound.
     *
     * @param sound The sound to be played by this track.
     */
    public LoopedTrack(Sound sound)
    {
        this.track = Objects.requireNonNull(sound);
    }

    @Override
    public Iterator<Sound> iterator()
    {
        return this;
    }

    @Override
    public AudioFormat getFormat()
    {
        return this.track.getFormat();
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
        return this.track;
    }

    @Override
    public boolean equals(Object anObject)
    {
        return this == anObject || anObject instanceof LoopedTrack lt
                && lt.track.equals(this.track);
    }

    @Override
    public int hashCode()
    {
        return this.track.hashCode();
    }

    @Override
    public String toString()
    {
        return "looped track: " + this.track;
    }
}
