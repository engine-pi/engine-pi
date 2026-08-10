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
package pi.physics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Settings;
import org.junit.jupiter.api.Test;

class FixtureBuilderTest
{
    @Test
    void ellipseCreatesPolygonWithRequestedPointCount()
    {
        FixtureData fixtureData = FixtureBuilder.ellipse(4, 2, 5);

        PolygonShape shape = assertInstanceOf(PolygonShape.class,
            fixtureData.getShape());
        assertEquals(5, shape.getVertexCount());

        float minX = Float.POSITIVE_INFINITY;
        float maxX = Float.NEGATIVE_INFINITY;
        float minY = Float.POSITIVE_INFINITY;
        float maxY = Float.NEGATIVE_INFINITY;
        for (int i = 0; i < shape.getVertexCount(); i++)
        {
            float x = shape.getVertex(i).x;
            float y = shape.getVertex(i).y;
            minX = Math.min(minX, x);
            maxX = Math.max(maxX, x);
            minY = Math.min(minY, y);
            maxY = Math.max(maxY, y);
        }

        assertEquals(0.0f, minX, 0.2f);
        assertEquals(4.0f, maxX, 0.2f);
        assertEquals(0.0f, minY, 0.2f);
        assertEquals(2.0f, maxY, 0.2f);
    }

    @Test
    void ellipseRejectsTooFewPoints()
    {
        assertThrows(IllegalArgumentException.class,
            () -> FixtureBuilder.ellipse(4, 2, 2));
    }

    @Test
    void ellipseRejectsTooManyPoints()
    {
        assertThrows(IllegalArgumentException.class,
            () -> FixtureBuilder
                .ellipse(4, 2, Settings.maxPolygonVertices + 1));
    }
}
