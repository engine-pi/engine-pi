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
package pi.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static pi.util.TimeUtil.formatInterval;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class TimeUtilTest
{
    @Nested
    class FormatIntervalTest
    {
        @Test
        void zero()
        {
            assertEquals("00:00:00.000", formatInterval(0));
        }

        @Test
        void milliseconds()
        {
            assertEquals("00:00:00.500", formatInterval(500));
        }

        @Test
        void seconds()
        {
            assertEquals("00:00:05.000", formatInterval(5000));
        }

        @Test
        void minutes()
        {
            assertEquals("00:02:00.000", formatInterval(120000));
        }

        @Test
        void hours()
        {
            assertEquals("01:00:00.000", formatInterval(3600000));
        }

        @Test
        void complex()
        {
            // 1 hour + 2 minutes + 3 seconds + 456 milliseconds
            long interval = 3600000 + 120000 + 3000 + 456;
            assertEquals("01:02:03.456", formatInterval(interval));
        }

        @Test
        void multipleHours()
        {
            // 5 hours + 30 minutes + 45 seconds + 123 milliseconds
            long interval = 5 * 3600000 + 30 * 60000 + 45 * 1000 + 123;
            assertEquals("05:30:45.123", formatInterval(interval));
        }
    }

    @Nested
    class FormatIntervalWithFormatTest
    {
        @Test
        void customFormat()
        {
            assertEquals("01-02-03-456",
                formatInterval(3723456, "%02d-%02d-%02d-%03d"));
        }

        @Test
        void zero()
        {
            assertEquals("00-00-00-000",
                formatInterval(0, "%02d-%02d-%02d-%03d"));
        }

        @Test
        void withoutPadding()
        {
            assertEquals("1:2:3:456", formatInterval(3723456, "%d:%d:%d:%03d"));
        }

        @Test
        void omitLastArgument()
        {
            assertEquals("01:02:03", formatInterval(3723456, "%02d:%02d:%02d"));
        }

        @Test
        void indexedArguments()
        {
            assertEquals("2 Minuten 3 Sekunden",
                formatInterval(3723456, "%2$d Minuten %3$d Sekunden"));
        }
    }
}
