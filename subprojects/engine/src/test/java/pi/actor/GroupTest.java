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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import pi.Controller;

class GroupTest
{
    private Circle circle;

    private Rectangle rectangle;

    private Group<Actor> group;

    @BeforeEach
    void setUp()
    {
        Controller.instantMode(false);
        circle = new Circle();
        rectangle = new Rectangle(1, 1);
        group = new Group<>(circle);
    }

    @Test
    void addReturnsSameInstanceAndAppendsActor()
    {
        Group<Actor> returned = group.add(rectangle);

        assertSame(group, returned);
        assertEquals(2, group.actors().size());
        assertSame(circle, group.actors().get(0));
        assertSame(rectangle, group.actors().get(1));
    }

    @Test
    void removeReturnsSameInstanceAndRemovesActor()
    {
        group.add(rectangle);

        Group<Actor> returned = group.remove(circle);

        assertSame(group, returned);
        assertEquals(1, group.actors().size());
        assertSame(rectangle, group.actors().get(0));
    }

    @Test
    void removeUnknownActorLeavesGroupUnchanged()
    {
        Group<Actor> returned = group.remove(rectangle);
        assertSame(group, returned);
        assertEquals(1, group.actors().size());
        assertSame(circle, group.actors().get(0));
    }

    @Test
    void addThrowsForNullActor()
    {
        assertThrows(NullPointerException.class, () -> group.add(null));
    }

    @Test
    void removeThrowsForNullActor()
    {
        assertThrows(NullPointerException.class, () -> group.remove(null));
    }

    @Test
    void sizeReturnsNumberOfActors()
    {
        assertEquals(1, group.size());

        group.add(rectangle);
        assertEquals(2, group.size());

        group.remove(circle);
        assertEquals(1, group.size());
    }

    @Test
    void isEmptyReflectsCurrentState()
    {
        assertFalse(group.isEmpty());

        group.remove(circle);

        assertTrue(group.isEmpty());
    }
}
