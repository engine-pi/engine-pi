/*
 * Source: https://github.com/gurkenlabs/litiengine/blob/main/litiengine/src/main/java/de/gurkenlabs/litiengine/sound/SoundEvent.java
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

import java.util.EventObject;

/**
 * This implementation is used for all events that need to pass a {@code Sound}
 * object to their listeners.
 *
 * @see Playback#cancel()
 * @see Playback#finish()
 */
public class SoundEvent extends EventObject
{
    private static final long serialVersionUID = -2070316328855430839L;

    private final transient Sound sound;

    SoundEvent(Object source, Sound sound)
    {
        super(source);
        this.sound = sound;
    }

    /**
     * Gets the related {@code Sound} instance.
     *
     * @return The sound object.
     */
    public Sound getSound()
    {
        return this.sound;
    }

    @Override
    public String toString()
    {
        return super.toString() + "[sound=" + this.sound.getName() + "]";
    }
}
