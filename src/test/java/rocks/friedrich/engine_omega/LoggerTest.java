package rocks.friedrich.engine_omega;

import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import rocks.friedrich.engine_omega.util.Logger;

public class LoggerTest
{
    private final PrintStream standardErr = System.err;

    private final ByteArrayOutputStream errStreamCaptor = new ByteArrayOutputStream();

    @Before
    public void setUp()
    {
        System.setErr(new PrintStream(errStreamCaptor));
    }

    @After
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
