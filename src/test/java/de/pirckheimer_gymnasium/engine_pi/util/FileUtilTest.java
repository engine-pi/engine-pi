package de.pirckheimer_gymnasium.engine_pi.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class FileUtilTest
{
    @Test
    void testGetHome()
    {
        assertEquals(FileUtil.getHome(), System.getProperty("user.home"));
    }
}
