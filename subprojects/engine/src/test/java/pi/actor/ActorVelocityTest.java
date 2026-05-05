/*
 * Engine Pi ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2024 Josef Friedrich and contributors.
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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import pi.Controller;
import pi.graphics.geom.Vector;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for velocity-related getter and setter methods in the
 * {@link Actor} class.
 *
 * @author Josef Friedrich
 *
 * @since 0.48.0
 */
class ActorVelocityTest
{
    private Actor actor;

    @BeforeEach
    void setUp()
    {
        Controller.instantMode(false);
        actor = new Rectangle(1.0, 1.0);
    }

    @Nested
    class VelocityGetterTest
    {
        @Test
        void initiallyZero()
        {
            Vector velocity = actor.velocity();
            assertNotNull(velocity);
            assertEquals(0.0, velocity.x());
            assertEquals(0.0, velocity.y());
        }

        @Test
        void returnsVector()
        {
            Vector velocity = actor.velocity();
            assertNotNull(velocity);
            assertTrue(velocity instanceof Vector);
        }

        @Test
        void afterSetting()
        {
            actor.velocity(3.0, 4.0);
            Vector velocity = actor.velocity();
            assertEquals(3.0, velocity.x(), 0.0001);
            assertEquals(4.0, velocity.y(), 0.0001);
        }
    }

    @Nested
    class VelocityXGetterTest
    {
        @Test
        void initiallyZero()
        {
            assertEquals(0.0, actor.velocityX());
        }

        @Test
        void afterSetting()
        {
            actor.velocity(5.0, 2.0);
            double velocityX = actor.velocityX();
            assertEquals(5.0, velocityX, 0.0001);
        }

        @Test
        void consistentWithFullVelocity()
        {
            actor.velocity(7.5, 3.2);
            assertEquals(actor.velocity().x(), actor.velocityX());
        }
    }

    @Nested
    class VelocityYGetterTest
    {
        @Test
        void initiallyZero()
        {
            double velocityY = actor.velocityY();
            assertEquals(0.0, velocityY);
        }

        @Test
        void afterSetting()
        {
            actor.velocity(5.0, 2.0);
            double velocityY = actor.velocityY();
            assertEquals(2.0, velocityY, 0.0001);
        }

        @Test
        void consistentWithFullVelocity()
        {
            actor.velocity(7.5, 3.2);
            assertEquals(actor.velocity().y(), actor.velocityY());
        }
    }

    @Nested
    class VelocitySetterWithVectorTest
    {
        @Test
        void withVector()
        {
            actor.velocity(new Vector(10.0, 20.0));
            assertEquals(10.0, actor.velocityX(), 0.0001);
            assertEquals(20.0, actor.velocityY(), 0.0001);
        }

        @Test
        void returnsActor()
        {
            Actor result = actor.velocity(new Vector(1.0, 2.0));
            assertSame(actor, result);
        }

        @Test
        void overwritesPreviousVelocity()
        {
            actor.velocity(5.0, 5.0);
            actor.velocity(new Vector(15.0, 25.0));
            assertEquals(15.0, actor.velocityX(), 0.0001);
            assertEquals(25.0, actor.velocityY(), 0.0001);
        }

        @Test
        void withPositiveValues()
        {
            actor.velocity(new Vector(100.0, 200.0));
            assertEquals(100.0, actor.velocityX(), 0.0001);
            assertEquals(200.0, actor.velocityY(), 0.0001);
        }

        @Test
        void withNegativeValues()
        {
            actor.velocity(new Vector(-50.0, -75.0));
            assertEquals(-50.0, actor.velocityX(), 0.0001);
            assertEquals(-75.0, actor.velocityY(), 0.0001);
        }

        @Test
        void withMixedSignValues()
        {
            actor.velocity(new Vector(30.0, -40.0));
            assertEquals(30.0, actor.velocityX(), 0.0001);
            assertEquals(-40.0, actor.velocityY(), 0.0001);
        }

        @Test
        void withZeroValues()
        {
            actor.velocity(10.0, 10.0);
            actor.velocity(new Vector(0.0, 0.0));
            assertEquals(0.0, actor.velocityX());
            assertEquals(0.0, actor.velocityY());
        }

        @Test
        void withNaNReturnsWithoutChange()
        {
            actor.velocity(5.0, 10.0);
            Vector nanVelocity = new Vector(Double.NaN, 15.0);
            actor.velocity(nanVelocity);
            // Velocity should remain unchanged
            assertEquals(5.0, actor.velocityX(), 0.0001);
            assertEquals(10.0, actor.velocityY(), 0.0001);
        }

        @Test
        void supportsChaining()
        {
            Actor result = actor.velocity(new Vector(3.0, 4.0))
                .velocity(new Vector(6.0, 8.0));
            assertSame(actor, result);
            assertEquals(6.0, actor.velocityX(), 0.0001);
            assertEquals(8.0, actor.velocityY(), 0.0001);
        }
    }

    @Nested
    class VelocitySetterWithComponentsTest
    {
        @Test
        void setVelocityWithXY()
        {
            actor.velocity(7.0, 11.0);
            assertEquals(7.0, actor.velocityX(), 0.0001);
            assertEquals(11.0, actor.velocityY(), 0.0001);
        }

        @Test
        void returnsActor()
        {
            Actor result = actor.velocity(2.0, 3.0);
            assertSame(actor, result);
        }

        @Test
        void overwritesPrevious()
        {
            actor.velocity(10.0, 20.0);
            actor.velocity(30.0, 40.0);
            assertEquals(30.0, actor.velocityX(), 0.0001);
            assertEquals(40.0, actor.velocityY(), 0.0001);
        }

        @Test
        void positiveValues()
        {
            actor.velocity(123.45, 678.90);
            assertEquals(123.45, actor.velocityX(), 0.0001);
            assertEquals(678.90, actor.velocityY(), 0.0001);
        }

        @Test
        void negativeValues()
        {
            actor.velocity(-55.0, -88.0);
            assertEquals(-55.0, actor.velocityX(), 0.0001);
            assertEquals(-88.0, actor.velocityY(), 0.0001);
        }

        @Test
        void mixedSigns()
        {
            actor.velocity(25.0, -75.0);
            assertEquals(25.0, actor.velocityX(), 0.0001);
            assertEquals(-75.0, actor.velocityY(), 0.0001);
        }

        @Test
        void zeroValues()
        {
            actor.velocity(0.0, 0.0);
            assertEquals(0.0, actor.velocityX());
            assertEquals(0.0, actor.velocityY());
        }

        @Test
        void supportsChaining()
        {
            Actor result = actor.velocity(1.0, 2.0).velocity(3.0, 4.0);
            assertSame(actor, result);
            assertEquals(3.0, actor.velocityX(), 0.0001);
            assertEquals(4.0, actor.velocityY(), 0.0001);
        }
    }

    @Nested
    class VelocityXSetterTest
    {
        @Test
        void set()
        {
            actor.velocity(1, 2);
            actor.velocityX(2);
            assertEquals(2.0, actor.velocityX(), 0.0001);
            assertEquals(2.0, actor.velocityY(), 0.0001);
        }

        @Test
        void returnsActor()
        {
            Actor result = actor.velocityX(1);
            assertSame(actor, result);
        }
    }

    @Nested
    class VelocityYSetterTest
    {
        @Test
        void set()
        {
            actor.velocity(1, 2);
            actor.velocityY(1);
            assertEquals(1.0, actor.velocityX(), 0.0001);
            assertEquals(1.0, actor.velocityY(), 0.0001);
        }

        @Test
        void returnsActor()
        {
            Actor result = actor.velocityY(1);
            assertSame(actor, result);
        }
    }
}
