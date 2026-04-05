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
package pi.resources.sound;

import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.UnsupportedAudioFileException;

import pi.annotations.API;
import pi.annotations.ChainableMethod;
import pi.annotations.Getter;
import pi.annotations.Setter;
import pi.resources.Codec;
import pi.resources.NamedResource;

/**
 * @author Steffen Wilke
 * @author Matthias Wilke
 */
public class SoundResource extends NamedResource
{
    public SoundResource(Sound sound, SoundFormat format)
    {
        name(sound.name());
        data = Codec.encode(sound.rawData());
        this.format = format;
    }

    public SoundResource(InputStream data, String name, SoundFormat format)
            throws IOException, UnsupportedAudioFileException
    {
        this(new Sound(data, name), format);
    }

    private String data;

    @API
    @Getter
    public String data()
    {
        return data;
    }

    @API
    @Setter
    @ChainableMethod
    public void data(String data)
    {
        this.data = data;
    }

    private SoundFormat format = SoundFormat.UNSUPPORTED;

    @API
    @Getter
    public SoundFormat format()
    {
        return format;
    }

    @API
    @Setter
    @ChainableMethod
    public void format(SoundFormat format)
    {
        this.format = format;
    }
}
