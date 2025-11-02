/*
 * Source: https://github.com/gurkenlabs/litiengine/blob/main/litiengine/src/main/java/de/gurkenlabs/litiengine/resources/SoundResource.java
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
package de.pirckheimer_gymnasium.engine_pi.resources;

import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.UnsupportedAudioFileException;

import de.pirckheimer_gymnasium.engine_pi.sound.Sound;

/**
 * @author Steffen Wilke
 * @author Matthias Wilke
 */
public class SoundResource extends NamedResource
{
    private String data;

    private SoundFormat format = SoundFormat.UNSUPPORTED;

    public SoundResource()
    {
        // keep for xml serialization
    }

    public SoundResource(Sound sound, SoundFormat format)
    {
        this.setName(sound.getName());
        this.data = Codec.encode(sound.getRawData());
        this.format = format;
    }

    public SoundResource(InputStream data, String name, SoundFormat format)
            throws IOException, UnsupportedAudioFileException
    {
        this(new Sound(data, name), format);
    }

    public String getData()
    {
        return this.data;
    }

    public SoundFormat getFormat()
    {
        return this.format;
    }

    public void setData(String data)
    {
        this.data = data;
    }

    public void setFormat(SoundFormat format)
    {
        this.format = format;
    }
}
