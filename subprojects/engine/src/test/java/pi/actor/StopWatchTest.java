/*
 * Engine Pi ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2026 Josef Friedrich and contributors.
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
package pi.actor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pi.CustomAssertions.assertToStringClassName;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import pi.Controller;

public class StopWatchTest
{
    private StopWatch watch;

    @BeforeEach
    public void setUp()
    {
        Controller.instantMode(false);
        watch = new StopWatch();
    }

    @Test
    public void initialState()
    {
        assertEquals(0, watch.time());
        assertFalse(watch.isRunning());
        assertEquals("%02d:%02d:%02d.%03d", watch.format());
    }

    @Test
    public void start()
    {
        watch.start();
        assertTrue(watch.isRunning());
    }

    @Test
    public void stop()
    {
        watch.start();
        watch.stop();
        assertFalse(watch.isRunning());
    }

    @Test
    public void toggle()
    {
        assertFalse(watch.isRunning());
        watch.toggle();
        assertTrue(watch.isRunning());
        watch.toggle();
        assertFalse(watch.isRunning());
    }

    @Test
    public void reset()
    {
        watch.time(5000);
        watch.reset();
        assertEquals(0, watch.time());
        assertFalse(watch.isRunning());
    }

    @Test
    public void timeSetter()
    {
        StopWatch result = watch.time(3000);
        assertEquals(3000, watch.time());
        assertSame(watch, result);
    }

    @Test
    public void formatSetter()
    {
        String newFormat = "%d ms";
        StopWatch result = watch.format(newFormat);
        assertEquals(newFormat, watch.format());
        assertSame(watch, result);
    }

    @Test
    public void chaining()
    {
        StopWatch result = watch.format("%02d:%02d:%02d").time(1000).start();
        assertSame(watch, result);
        assertTrue(watch.isRunning());
        assertTrue(1000 <= watch.time());
    }

    @Test
    public void testTimeNotUpdatedWhenStopped()
    {
        watch.time(1000);
        long initialTime = watch.time();
        assertEquals(1000, initialTime);
    }

    @Test
    public void testCannotSetTimeWhileRunning()
    {
        watch.start();
        watch.time(5000);
        assertTrue(watch.time() > 0);
    }

    @Test
    void toStringFormatter()
    {
        assertToStringClassName(watch);
    }
}
