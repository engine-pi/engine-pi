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
package pi.graphics.boxes_ng;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.awt.Color;
import java.awt.Graphics2D;

import org.junit.jupiter.api.Test;

/**
 * @since 0.53.0
 */
class BorderBoxTest
{
    @Test
    void constructorSetsChildAndParent()
    {
        DimensionBox child = new DimensionBox(10, 20);
        BorderBox border = new BorderBox(child);

        assertEquals(1, border.numberOfChilds());
        assertSame(child, border.iterator().next());
        assertSame(border, child.parent);
    }

    @Test
    void settersAreChainableAndApplyDefaults()
    {
        BorderBox border = new BorderBox(new DimensionBox());

        assertSame(border, border.thickness(3));
        assertEquals(3, border.thickness);
        assertEquals(Color.BLACK, border.color);

        assertSame(border, border.color(Color.BLUE));
        assertEquals(Color.BLUE, border.color);
    }

    @Test
    void colorSetsDefaultThickness()
    {
        BorderBox border = new BorderBox(new DimensionBox());

        border.color(Color.RED);

        assertEquals(1, border.thickness);
        assertEquals(Color.RED, border.color);
    }

    @Test
    void measureCalculatesBorderDimensionAndChildAnchor()
    {
        DimensionBox child = new DimensionBox(10, 20);
        BorderBox border = new BorderBox(child).thickness(2);
        border.x(100).y(200);

        border.measure();

        assertEquals(14, border.width());
        assertEquals(24, border.height());
        assertEquals(100, border.x());
        assertEquals(200, border.y());
        assertEquals(176, border.yTop());
        assertEquals(102, child.x());
        assertEquals(198, child.y());
    }

    @Test
    void renderDrawsFourBorderRectanglesAndRestoresColor()
    {
        DimensionBox child = new DimensionBox(10, 20);
        BorderBox border = new BorderBox(child).thickness(2).color(Color.RED);
        Graphics2D graphics = mock(Graphics2D.class);
        Color originalColor = Color.GREEN;
        when(graphics.getColor()).thenReturn(originalColor);

        border.render(graphics);

        verify(graphics).setColor(Color.RED);
        verify(graphics).fillRect(0, -24, 14, 2);
        verify(graphics).fillRect(12, -22, 2, 20);
        verify(graphics).fillRect(0, -2, 14, 2);
        verify(graphics).fillRect(0, -22, 2, 20);
        verify(graphics).setColor(originalColor);
    }

    @Test
    void renderSkipsBorderWhenThicknessIsNotPositive()
    {
        BorderBox border = new BorderBox(new DimensionBox(10, 20))
            .color(Color.RED)
            .thickness(-1);
        Graphics2D graphics = mock(Graphics2D.class);

        border.render(graphics);

        verifyNoInteractions(graphics);
    }
}
