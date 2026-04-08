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
package pi.debug;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static pi.debug.AnsiColor.BLACK;
import static pi.debug.AnsiColor.BLUE;
import static pi.debug.AnsiColor.CYAN;
import static pi.debug.AnsiColor.GREEN;
import static pi.debug.AnsiColor.MAGENTA;
import static pi.debug.AnsiColor.RED;
import static pi.debug.AnsiColor.RESET;
import static pi.debug.AnsiColor.WHITE;
import static pi.debug.AnsiColor.YELLOW;
import static pi.debug.AnsiColor.black;
import static pi.debug.AnsiColor.blue;
import static pi.debug.AnsiColor.cyan;
import static pi.debug.AnsiColor.green;
import static pi.debug.AnsiColor.magenta;
import static pi.debug.AnsiColor.red;
import static pi.debug.AnsiColor.remove;
import static pi.debug.AnsiColor.white;
import static pi.debug.AnsiColor.yellow;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class AnsiColorTest
{
    @Test
    void blackColor()
    {
        assertEquals(BLACK + "abc" + RESET, black("abc"));
    }

    @Test
    void redColor()
    {
        assertEquals(RED + "abc" + RESET, red("abc"));
    }

    @Test
    void greenColor()
    {
        assertEquals(GREEN + "abc" + RESET, green("abc"));
    }

    @Test
    void yellowColor()
    {
        assertEquals(YELLOW + "abc" + RESET, yellow("abc"));
    }

    @Test
    void blueColor()
    {
        assertEquals(BLUE + "abc" + RESET, blue("abc"));
    }

    @Test
    void magentaColor()
    {
        assertEquals(MAGENTA + "abc" + RESET, magenta("abc"));
    }

    @Test
    void cyanColor()
    {
        assertEquals(CYAN + "abc" + RESET, cyan("abc"));
    }

    @Test
    void whiteColor()
    {
        assertEquals(WHITE + "abc" + RESET, white("abc"));
    }

    @Nested
    class RemoveTest
    {
        @Test
        void single()
        {
            String colored = RED + "Hallo" + RESET;
            assertEquals("Hallo", remove(colored));
        }

        @Test
        void multipleSequences()
        {
            String colored = BLUE + "A" + RESET + GREEN + "B" + RESET;
            assertEquals("AB", remove(colored));
        }
    }

    @Nested
    class PrimitiveTypeInputTest
    {
        @Test
        void booleanInput()
        {
            assertEquals(CYAN + "true" + RESET, cyan(true));
        }

        @Test
        void byteInput()
        {
            assertEquals(CYAN + "7" + RESET, cyan((byte) 7));
        }

        @Test
        void shortInput()
        {
            assertEquals(CYAN + "12" + RESET, cyan((short) 12));
        }

        @Test
        void intInput()
        {
            assertEquals(CYAN + "42" + RESET, cyan(42));
        }

        @Test
        void longInput()
        {
            assertEquals(CYAN + "123456789" + RESET, cyan(123456789L));
        }

        @Test
        void floatInput()
        {
            assertEquals(CYAN + "1.25" + RESET, cyan(1.25f));
        }

        @Test
        void doubleInput()
        {
            assertEquals(CYAN + "3.5" + RESET, cyan(3.5d));
        }

        @Test
        void charInput()
        {
            assertEquals(CYAN + "x" + RESET, cyan('x'));
        }
    }
}
