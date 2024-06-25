package de.pirckheimer_gymnasium.engine_pi.debug;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class VersionTest
{
    @Test
    void testGetJarName()
    {
        assertEquals(Version.getJarName(), null);
    }

    @Test
    void testGetVersion()
    {
        assertTrue(Version.getVersion().length() > 0);
    }

    @Test
    void testGetGitCommitIdAbbrev()
    {
        assertEquals(Version.getGitCommitIdAbbrev().length(), 7);
    }
}
