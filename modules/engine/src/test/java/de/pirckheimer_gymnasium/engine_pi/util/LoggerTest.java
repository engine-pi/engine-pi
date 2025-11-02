package de.pirckheimer_gymnasium.engine_pi.util;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LoggerTest
{
    private final PrintStream standardErr = System.err;

    private final ByteArrayOutputStream errStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp()
    {
        System.setErr(new PrintStream(errStreamCaptor));
    }

    @AfterEach
    public void tearDown()
    {
        System.setErr(standardErr);
    }

    @Test
    public void testFileExists()
    {
        Logger.error("LoggerTest", "lorem ipsum");
        assertTrue(errStreamCaptor.toString().contains("lorem ipsum"));
        assertTrue(Files.exists(Paths.get("engine-pi.log")));
    }
}
