/* https://github.com/gurkenlabs/litiengine/blob/main/litiengine/src/main/java/de/gurkenlabs/litiengine/GameLog.java
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
package pi.debug;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * The {@link LogSetup} class provides a general purpose logger for games. It
 * prevents the necessity to create custom logger instances and provides a
 * simplified way to quickly log events.
 *
 * @see pi.Controller#log
 *
 * @since 0.45.0
 */
public final class LogSetup
{
    private static final String LOGGING_CONFIG_FILE = "logging.properties";

    private static final Logger log = Logger
        .getLogger(LogSetup.class.getName());

    LogSetup()
    {
    }

    public static Logger log()
    {
        return log;
    }

    public static void init()
    {
        LogManager.getLogManager().reset();
        if (Files.exists(Path.of(LOGGING_CONFIG_FILE)))
        {
            System.setProperty("java.util.logging.config.file",
                LOGGING_CONFIG_FILE);

            try
            {
                LogManager.getLogManager().readConfiguration();
            }
            catch (final Exception e)
            {
                log.log(Level.SEVERE, e.getMessage(), e);
            }
        }
        else
        {
            try
            {
                final ConsoleHandler consoleHandler = new ConsoleHandler();
                consoleHandler.setLevel(Level.ALL);
                consoleHandler.setFormatter(new SimpleFormatter());

                final FileHandler fileHandler = new FileHandler("engine-pi.log",
                        50000, 1, true);
                fileHandler.setLevel(Level.WARNING);
                fileHandler.setFormatter(new SimpleFormatter());

                final Logger logger = Logger.getLogger("");
                logger.addHandler(consoleHandler);
                logger.addHandler(fileHandler);
            }
            catch (final Exception e)
            {
                log.log(Level.SEVERE, e.getMessage(), e);
            }
        }
    }
}
