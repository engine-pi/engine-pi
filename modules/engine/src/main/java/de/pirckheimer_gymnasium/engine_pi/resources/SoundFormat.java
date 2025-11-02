/*
 * Source: https://github.com/gurkenlabs/litiengine/blob/main/litiengine/src/main/java/de/gurkenlabs/litiengine/resources/SoundFormat.java
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

import java.io.File;

/**
 * Contains all known audio file-formats supported by the engine.
 *
 * @see ImageFormat
 *
 * @author Steffen Wilke
 * @author Matthias Wilke
 */
public enum SoundFormat
{
    UNSUPPORTED, OGG, MP3, WAV;

    /**
     * Gets the {@code SoundFormat} of the specified format string.
     *
     * @param format The format string from which to extract the format.
     *
     * @return The format of the specified string or {@code UNDEFINED} if not
     *     supported.
     */
    public static SoundFormat get(String format)
    {
        return DataFormat.get(format, values(), UNSUPPORTED);
    }

    /**
     * Determines whether the extension of the specified file is supported by
     * the engine.
     *
     * @param file The file to check for.
     *
     * @return True if the extension is part of this enum; otherwise false.
     */
    public static boolean isSupported(File file)
    {
        return isSupported(file.getName());
    }

    /**
     * Determines whether the extension of the specified file is supported by
     * the engine.
     *
     * @param fileName The file name to check for.
     *
     * @return True if the extension is part of this enum; otherwise false.
     */
    public static boolean isSupported(String fileName)
    {
        return DataFormat.isSupported(fileName, values(), UNSUPPORTED);
    }

    public static String[] getAllExtensions()
    {
        return DataFormat.getAllExtensions(values(), UNSUPPORTED);
    }

    /**
     * Converts this format instance to a file format string that can be used as
     * an extension (e.g. .ogg).<br>
     * It adds a leading '.' to the lower-case string representation of this
     * instance.
     *
     * @return The file extension string for this instance.
     */
    public String toFileExtension()
    {
        return "." + this.name().toLowerCase();
    }

    @Override
    public String toString()
    {
        return this.name().toLowerCase();
    }
}
