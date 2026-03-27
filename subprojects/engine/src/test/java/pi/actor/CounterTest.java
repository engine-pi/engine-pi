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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import pi.Controller;

import static org.junit.jupiter.api.Assertions.*;

public class CounterTest
{
    private Counter counter;

    @BeforeEach
    public void setUp()
    {
        Controller.instantMode(false);
        counter = new Counter();
    }

    @Test
    public void initialization()
    {
        assertEquals(0, counter.counter());
        assertEquals("0", counter.content());
    }

    @Test
    public void constructorWithParameters()
    {
        Counter c = new Counter(5, "prefix_", "{counter}_template", "_suffix");
        assertEquals(5, c.counter());
        assertEquals("prefix_5_template_suffix", c.content());
    }

    @Test
    public void counter()
    {
        counter.counter(10);
        assertEquals(10, counter.counter());
        assertEquals("10", counter.content());
    }

    @Test
    public void increase()
    {
        assertEquals(1, counter.increase());
        assertEquals(1, counter.counter());
    }

    @Test
    public void increaseWithCustomAmount()
    {
        counter.amount(5);
        assertEquals(5, counter.increase());
        assertEquals(5, counter.counter());
    }

    @Test
    public void decrease()
    {
        counter.counter(10);
        assertEquals(9, counter.decrease());
        assertEquals(9, counter.counter());
    }

    @Test
    public void decreaseWithCustomAmount()
    {
        counter.counter(20);
        counter.amount(3);
        assertEquals(17, counter.decrease());
        assertEquals(17, counter.counter());
    }

    @Test
    public void reset()
    {
        counter.counter(100);
        counter.reset();
        assertEquals(0, counter.counter());
        assertEquals("0", counter.content());
    }

    @Test
    public void prefix()
    {
        counter.prefix("Zähler: ");
        counter.update();
        assertEquals("Zähler: 0", counter.content());
    }

    @Test
    public void suffix()
    {
        counter.suffix(" Punkte");
        counter.update();
        assertEquals("0 Punkte", counter.content());
    }

    @Test
    public void template()
    {
        counter.template("Score: {counter}");
        counter.update();
        assertEquals("Score: 0", counter.content());
    }

    @Test
    public void templateWithPrefixAndSuffix()
    {
        counter.prefix("[").template("{counter}/10").suffix("]");
        counter.counter(5);
        counter.update();
        assertEquals("[5/10]", counter.content());
    }

    @Test
    public void templateWithoutPlaceholder()
    {
        assertThrows(RuntimeException.class, () -> counter.template("Invalid"));
    }

    @Test
    public void amount()
    {
        counter.amount(7);
        assertEquals(7, counter.amount());
    }

    @Test
    public void gettersAndSetters()
    {
        counter.prefix("pre_");
        counter.suffix("_suf");
        counter.template("{counter}");
        counter.amount(3);

        assertEquals("pre_", counter.prefix());
        assertEquals("_suf", counter.suffix());
        assertEquals("{counter}", counter.template());
        assertEquals(3, counter.amount());
        counter.increase();
        assertEquals("pre_3_suf", counter.content());
    }
}
