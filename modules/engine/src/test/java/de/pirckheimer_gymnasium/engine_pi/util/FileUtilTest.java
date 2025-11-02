package de.pirckheimer_gymnasium.engine_pi.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;

public class FileUtilTest
{
    @Test
    void testGetHome()
    {
        assertEquals(FileUtil.getHome(), System.getProperty("user.home"));
    }

    @Test
    void testGetFileNameUrl() throws MalformedURLException
    {
        assertEquals(FileUtil.getFileName(new URL("file:/home/pi/test.png")),
                "test");
    }

    @Test
    void testGetFileNameString()
    {
        assertEquals(FileUtil.getFileName("/home/pi/test.png"), "test");
    }

    @Test
    void testGetFileNameExtension()
    {
        String path = "/home/pi/test.png";
        assertEquals(FileUtil.getFileName(path, false), "test");
        assertEquals(FileUtil.getFileName(path, true), "test.png");
    }
}
