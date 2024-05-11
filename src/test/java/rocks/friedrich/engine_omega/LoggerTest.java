package rocks.friedrich.engine_omega;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import rocks.friedrich.engine_omega.util.Logger;

public class LoggerTest
{
    private final PrintStream standardErr = System.err;

    private final ByteArrayOutputStream errStreamCaptor = new ByteArrayOutputStream();

    @BeforeAll
    public void setUp()
    {
        System.setErr(new PrintStream(errStreamCaptor));
    }

    @AfterAll
    public void tearDown()
    {
        System.setErr(standardErr);
    }

    @Test
    public void fileExists()
    {
        Logger.error("LoggerTest", "lorem ipsum");
        assertTrue(errStreamCaptor.toString().indexOf("lorem ipsum") > -1);
        assertTrue(Files.exists(Paths.get("engine-omega.log")));
    }
}
