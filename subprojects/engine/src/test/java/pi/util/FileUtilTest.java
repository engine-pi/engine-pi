/*
 * Engine Pi ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2025 Josef Friedrich and contributors.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package pi.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class FileUtilTest
{
    @Nested
    class GetFileNameTest
    {
        @Test
        void url() throws MalformedURLException
        {
            assertEquals("test",
                FileUtil.getFileName(new URL("file:/home/pi/test.png")));
        }

        @Test
        void string()
        {
            assertEquals("test", FileUtil.getFileName("/home/pi/test.png"));
        }

        @Test
        void extension()
        {
            String path = "/home/pi/test.png";
            assertEquals("test", FileUtil.getFileName(path, false));
            assertEquals("test.png", FileUtil.getFileName(path, true));
        }
    }

    @Nested
    class HumanReadableByteCountTest
    {
        @Test
        void bytesLessThanUnit()
        {
            assertEquals("0 bytes", FileUtil.humanReadableByteCount(0));
            assertEquals("1 bytes", FileUtil.humanReadableByteCount(1));
            assertEquals("512 bytes", FileUtil.humanReadableByteCount(512));
            assertEquals("1023 bytes", FileUtil.humanReadableByteCount(1023));
        }

        @Test
        void kilobytesDecimal()
        {
            assertEquals("1.0 KB", FileUtil.humanReadableByteCount(1000, true));
            assertEquals("1.5 KB", FileUtil.humanReadableByteCount(1500, true));
            assertEquals("999.9 KB",
                FileUtil.humanReadableByteCount(999900, true));
        }

        @Test
        void kilobytesBinary()
        {
            assertEquals("1.0 KiB",
                FileUtil.humanReadableByteCount(1024, false));
            assertEquals("1.5 KiB",
                FileUtil.humanReadableByteCount(1536, false));
            assertEquals("1023.0 KiB",
                FileUtil.humanReadableByteCount(1047552, false));
        }

        @Test
        void megabytesDecimal()
        {
            assertEquals("1.0 MB",
                FileUtil.humanReadableByteCount(1000000, true));
            assertEquals("2.3 MB",
                FileUtil.humanReadableByteCount(2300000, true));
        }

        @Test
        void megabytesBinary()
        {
            assertEquals("1.0 MiB",
                FileUtil.humanReadableByteCount(1048576, false));
            assertEquals("2.0 MiB",
                FileUtil.humanReadableByteCount(2097152, false));
        }

        @Test
        void gigabytesDecimal()
        {
            assertEquals("1.0 GB",
                FileUtil.humanReadableByteCount(1000000000L, true));
            assertEquals("5.5 GB",
                FileUtil.humanReadableByteCount(5500000000L, true));
        }

        @Test
        void gigabytesBinary()
        {
            assertEquals("1.0 GiB",
                FileUtil.humanReadableByteCount(1073741824L, false));
            assertEquals("4.0 GiB",
                FileUtil.humanReadableByteCount(4294967296L, false));
        }

        @Test
        void defaultDecimalFalse()
        {
            assertEquals("1.0 KiB", FileUtil.humanReadableByteCount(1024));
            assertEquals("1.0 MiB", FileUtil.humanReadableByteCount(1048576));
        }

        @Test
        void largeValues()
        {
            assertEquals("1.0 TB",
                FileUtil.humanReadableByteCount(1000000000000L, true));
            assertEquals("1.0 TiB",
                FileUtil.humanReadableByteCount(1099511627776L, false));
            assertEquals("1.0 PB",
                FileUtil.humanReadableByteCount(1000000000000000L, true));
            assertEquals("1.0 PiB",
                FileUtil.humanReadableByteCount(1125899906842624L, false));
        }
    }

    @Nested
    class ExistsTest
    {
        @Test
        void existingFolder()
        {
            assertTrue(FileUtil.exists(FileUtil.getHomeDir()));
        }

        @Test
        void existingFile()
        {
            assertTrue(FileUtil.exists(FileUtil.createTmpFile()));
        }

        @Test
        void nonExistingFile()
        {
            assertFalse(FileUtil.exists("non-existing-file.txt"));
        }

        @Test
        void emptyPath()
        {
            assertFalse(FileUtil.exists(""));
        }

        @Test
        void nullPath()
        {
            assertFalse(FileUtil.exists(null));
        }

        @Test
        void invalidPath()
        {
            assertFalse(FileUtil.exists("invalid://path/to/file"));
        }

        @Test
        void pathWithSpaces()
        {
            assertFalse(FileUtil.exists("file with spaces.txt"));
        }
    }

    @Test
    void getHomeDir()
    {
        assertEquals(FileUtil.getHomeDir(), System.getProperty("user.home"));
    }

    @Test
    void getTmpDir()
    {
        assertEquals(FileUtil.getTmpDir(),
            System.getProperty("java.io.tmpdir"));
    }
}
