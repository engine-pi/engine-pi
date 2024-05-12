/*
 * Source: https://github.com/gurkenlabs/litiengine/blob/main/litiengine/src/main/java/de/gurkenlabs/litiengine/util/io/StreamUtilities.java
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
package rocks.friedrich.engine_omega.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class StreamUtilities
{
    private static final Logger log = Logger
            .getLogger(StreamUtilities.class.getName());

    private StreamUtilities()
    {
        throw new UnsupportedOperationException();
    }

    public static void copy(final File file, final OutputStream out)
            throws IOException
    {
        try (InputStream in = new FileInputStream(file))
        {
            copy(in, out);
        }
    }

    public static void copy(final InputStream in, final File file)
            throws IOException
    {
        try (OutputStream out = new FileOutputStream(file))
        {
            copy(in, out);
        }
    }

    public static void copy(final InputStream in, final OutputStream out)
            throws IOException
    {
        final byte[] buffer = new byte[1024];
        if (in.markSupported())
        {
            in.mark(Integer.MAX_VALUE);
        }
        while (true)
        {
            final int readCount = in.read(buffer);
            if (readCount < 0)
            {
                break;
            }
            out.write(buffer, 0, readCount);
        }
        if (in.markSupported())
        {
            in.reset();
        }
    }

    public static byte[] getBytes(final InputStream in)
    {
        final ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        try
        {
            StreamUtilities.copy(in, buffer);
            return buffer.toByteArray();
        }
        catch (final IOException e)
        {
            log.log(Level.SEVERE, e.getMessage(), e);
        }
        return new byte[0];
    }
}
