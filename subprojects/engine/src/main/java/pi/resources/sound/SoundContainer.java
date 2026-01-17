/*
 * Source: https://github.com/gurkenlabs/litiengine/blob/main/litiengine/src/main/java/de/gurkenlabs/litiengine/resources/Sounds.java
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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sound.sampled.UnsupportedAudioFileException;

import pi.resources.Codec;
import pi.resources.ResourceLoader;
import pi.resources.ResourcesContainer;
import pi.util.FileUtil;

/**
 * Ein Speicher für <b>Klänge</b> des Datentyps {@link Sound}.
 *
 * @author Steffen Wilke
 * @author Matthias Wilke
 */
public final class SoundContainer extends ResourcesContainer<Sound>
{
    private static final Logger log = Logger
        .getLogger(SoundContainer.class.getName());

    public SoundContainer()
    {
    }

    /**
     * Loads a sound from the specified XML resource.
     *
     * @param resource The XML resource that contains the sound as Base64
     *     string.
     *
     * @return The {@code Sound} instance loaded from the specified resource.
     *
     * @see Codec#decode(String)
     */
    public Sound load(final SoundResource resource)
    {
        byte[] data = Codec.decode(resource.getData());
        ByteArrayInputStream input = new ByteArrayInputStream(data);
        Sound sound;
        try
        {
            sound = new Sound(input, resource.getName());
            this.add(resource.getName(), sound);
            return sound;
        }
        catch (IOException | UnsupportedAudioFileException e)
        {
            log.log(Level.SEVERE,
                "The audio file {0} could not be loaded.",
                new Object[]
                { resource.getName() });
        }
        return null;
    }

    /**
     * Loads the sound from the specified path and returns it.
     *
     * @param name The path of the file to be loaded.(Can be relative or
     *     absolute)
     *
     * @return The loaded Sound from the specified path.
     */
    @Override
    protected Sound load(URL name) throws Exception
    {
        try (final InputStream is = ResourceLoader.get(name))
        {
            if (is == null)
            {
                log.log(Level.SEVERE,
                    "The audio file {0} could not be loaded.",
                    new Object[]
                    { name });
                return null;
            }
            return new Sound(is, FileUtil.getFileName(name));
        }
    }
}
