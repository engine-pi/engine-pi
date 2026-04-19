/*
 * Source: https://github.com/gurkenlabs/litiengine/blob/main/litiengine/src/main/java/de/gurkenlabs/litiengine/sound/IntroTrack.java
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

import pi.annotations.Getter;

/**
 * Eine Audiospur, die einmalig eine Eingangsmusik und die darauf folgende Musik
 * in einer Endlosschleife abspielt.
 */
public class IntroMusic implements Music
{
    private final Sound intro;

    private final Sound loop;

    /**
     * Initializes a new {@code IntroTrack} for the specified sound.
     *
     * @param intro Die einmalig abgespielte Eingangsmusik.
     * @param loop Die auf die Eingangsmusik folgende in einer Endlosschleife
     *     wiedergegeben Anschlussmusik.
     */
    public IntroMusic(Sound intro, Sound loop)
    {
        Objects.requireNonNull(intro);
        Objects.requireNonNull(loop);
        if (!intro.format().matches(loop.format()))
        {
            throw new IllegalArgumentException(
                    intro.format() + " does not match " + loop.format());
        }
        this.intro = intro;
        this.loop = loop;
    }

    private class Iter implements Iterator<Sound>
    {
        private boolean first = true;

        @Override
        public boolean hasNext()
        {
            return true;
        }

        @Override
        public Sound next()
        {
            if (first)
            {
                first = false;
                return IntroMusic.this.intro;
            }
            return IntroMusic.this.loop;
        }
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
        return loop.format();
    }

    @Getter
    public Sound intro()
    {
        return intro;
    }

    @Getter
    public Sound loop()
    {
        return loop;
    }

    /**
     * @hidden
     */
    @Override
    public boolean equals(Object anObject)
    {
        if (this == anObject)
        {
            return true;
        }
        if (anObject instanceof IntroMusic it)
        {
            return intro == it.intro && loop == it.loop;
        }
        return false;
    }

    /**
     * @hidden
     */
    @Override
    public int hashCode()
    {
        return loop.hashCode() * 31 + intro.hashCode();
    }

    /**
     * @hidden
     */
    @Override
    public String toString()
    {
        return "looped track: " + loop + ", with intro: " + intro;
    }
}
