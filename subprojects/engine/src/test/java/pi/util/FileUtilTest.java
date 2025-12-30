package pi.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class FileUtilTest
{
    @Nested
    class GetFileNameTest
    {
        @Test
        void testUrl() throws MalformedURLException
        {
            assertEquals(
                    FileUtil.getFileName(new URL("file:/home/pi/test.png")),
                    "test");
        }

        @Test
        void testString()
        {
            assertEquals(FileUtil.getFileName("/home/pi/test.png"), "test");
        }

        @Test
        void testExtension()
        {
            String path = "/home/pi/test.png";
            assertEquals(FileUtil.getFileName(path, false), "test");
            assertEquals(FileUtil.getFileName(path, true), "test.png");
        }
    }

    @Nested
    class HumanReadableByteCountTest
    {
        @Test
        void testBytesLessThanUnit()
        {
            assertEquals("0 bytes", FileUtil.humanReadableByteCount(0));
            assertEquals("1 bytes", FileUtil.humanReadableByteCount(1));
            assertEquals("512 bytes", FileUtil.humanReadableByteCount(512));
            assertEquals("1023 bytes", FileUtil.humanReadableByteCount(1023));
        }

        @Test
        void testKilobytesDecimal()
        {
            assertEquals("1.0 KB", FileUtil.humanReadableByteCount(1000, true));
            assertEquals("1.5 KB", FileUtil.humanReadableByteCount(1500, true));
            assertEquals("999.9 KB",
                    FileUtil.humanReadableByteCount(999900, true));
        }

        @Test
        void testKilobytesBinary()
        {
            assertEquals("1.0 KiB",
                    FileUtil.humanReadableByteCount(1024, false));
            assertEquals("1.5 KiB",
                    FileUtil.humanReadableByteCount(1536, false));
            assertEquals("1023.0 KiB",
                    FileUtil.humanReadableByteCount(1047552, false));
        }

        @Test
        void testMegabytesDecimal()
        {
            assertEquals("1.0 MB",
                    FileUtil.humanReadableByteCount(1000000, true));
            assertEquals("2.3 MB",
                    FileUtil.humanReadableByteCount(2300000, true));
        }

        @Test
        void testMegabytesBinary()
        {
            assertEquals("1.0 MiB",
                    FileUtil.humanReadableByteCount(1048576, false));
            assertEquals("2.0 MiB",
                    FileUtil.humanReadableByteCount(2097152, false));
        }

        @Test
        void testGigabytesDecimal()
        {
            assertEquals("1.0 GB",
                    FileUtil.humanReadableByteCount(1000000000L, true));
            assertEquals("5.5 GB",
                    FileUtil.humanReadableByteCount(5500000000L, true));
        }

        @Test
        void testGigabytesBinary()
        {
            assertEquals("1.0 GiB",
                    FileUtil.humanReadableByteCount(1073741824L, false));
            assertEquals("4.0 GiB",
                    FileUtil.humanReadableByteCount(4294967296L, false));
        }

        @Test
        void testDefaultDecimalFalse()
        {
            assertEquals("1.0 KiB", FileUtil.humanReadableByteCount(1024));
            assertEquals("1.0 MiB", FileUtil.humanReadableByteCount(1048576));
        }

        @Test
        void testLargeValues()
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

    @Test
    void testGetHomeDir()
    {
        assertEquals(FileUtil.getHomeDir(), System.getProperty("user.home"));
    }

    @Test
    void testGetTmpDir()
    {
        assertEquals(FileUtil.getTmpDir(),
                System.getProperty("java.io.tmpdir"));
    }
}
