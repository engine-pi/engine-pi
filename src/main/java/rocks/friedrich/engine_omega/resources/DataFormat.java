/*
 * Source: https://github.com/gurkenlabs/litiengine/blob/main/litiengine/src/main/java/de/gurkenlabs/litiengine/resources/DataFormat.java
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
package rocks.friedrich.engine_omega.resources;

import java.util.ArrayList;

import rocks.friedrich.engine_omega.util.FileUtil;

/**
 * Some common implementations that are used by different kinds of file classes
 * (e.g. {@code SoundFormat}, {@code ImageFormat}.
 */
final class DataFormat
{
    private DataFormat()
    {
    }

    protected static <T extends Enum<T>> T get(String format, T[] values,
            T defaultValue)
    {
        if (format == null || format.isEmpty())
        {
            return defaultValue;
        }
        String stripedImageFormat = format;
        if (stripedImageFormat.startsWith("."))
        {
            stripedImageFormat = format.substring(1);
        }
        for (T val : values)
        {
            if (stripedImageFormat.equalsIgnoreCase(val.toString()))
            {
                return val;
            }
        }
        return defaultValue;
    }

    protected static <T extends Enum<T>> boolean isSupported(String fileName,
            T[] values, T defaultValue)
    {
        String extension = FileUtil.getExtension(fileName);
        if (extension == null || extension.isEmpty())
        {
            return false;
        }
        for (String supported : getAllExtensions(values, defaultValue))
        {
            if (extension.equalsIgnoreCase(supported))
            {
                return true;
            }
        }
        return false;
    }

    protected static <T extends Enum<T>> String[] getAllExtensions(T[] values,
            T defaultValue)
    {
        ArrayList<String> arrList = new ArrayList<>();
        for (T format : values)
        {
            if (format != defaultValue)
            {
                arrList.add(format.toString());
            }
        }
        return arrList.toArray(new String[arrList.size()]);
    }
}
