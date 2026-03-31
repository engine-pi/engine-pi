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
package pi.graphics.boxes;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * @author Josef Friedrich
 *
 * @since 0.45.0
 */
public class TextBlockBoxTest
{
    @Test
    void calculateDimension()
    {
        TextBlockBox box = new TextBlockBox("Hello World");
        box.width(200);
        box.calculateDimension();
        assertTrue(box.width() > 0);
        assertTrue(box.height() > 0);
        assertFalse(box.lines().isEmpty());
    }

    @Test
    void draw()
    {
        Graphics2D graphics = mock(Graphics2D.class);
        Color testColor = Color.BLACK;

        TextBlockBox box = new TextBlockBox("Test");
        box.width(100);
        box.color(testColor);
        box.draw(graphics);

        verify(graphics, atLeastOnce()).setColor(any(Color.class));
    }

    @Test
    void hAlign()
    {
        TextBlockBox box = new TextBlockBox("Test");
        TextBlockBox result = box.hAlign(HAlign.CENTER);
        assertSame(result, box);
    }

    @Test
    void lines()
    {
        TextBlockBox box = new TextBlockBox("Line 1\nLine 2\nLine 3");
        box.width(100);
        box.calculateDimension();

        List<TextBlockBox.TextLayoutLine> lines = box.lines();

        assertEquals(3, lines.size());

        TextBlockBox.TextLayoutLine line1 = lines.get(0);

        assertEquals("Line 1", line1.lineContent());
        assertEquals(0, line1.startIndex());
        assertEquals(6, line1.endIndex());
        assertEquals("Line 1\nLine 2\nLine 3", line1.parentContent());
        assertEquals("Line 2", lines.get(1).lineContent());
        assertEquals("Line 3", lines.get(2).lineContent());
    }

    @Test
    void automaticLineWrapping()
    {
        TextBlockBox box = new TextBlockBox(
                "This is a long line that should wrap into multiple lines when the width is set to a small value.");
        box.width(100);
        box.calculateDimension();

        assertArrayEquals(
            new String[]
            { "This is a long ", "line that ", "should wrap ", "into multiple ",
                    "lines when ", "the width is ", "set to a small ",
                    "value." },
            box.linesText());
    }

    @Test
    void toStringMethod()
    {
        TextBlockBox box = new TextBlockBox("Test\nMulti\nLine");
        box.width(100);
        box.calculateDimension();

        String result = box.toString();

        assertNotNull(result);
        assertTrue(result.contains("lines"));
    }

    @Test
    void width()
    {
        TextBlockBox box = new TextBlockBox("Test");
        TextBlockBox result = box.width(150);
        assertSame(result, box);
    }
}
