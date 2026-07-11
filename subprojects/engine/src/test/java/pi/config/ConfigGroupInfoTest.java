/*
 * Engine Pi ist eine anfaengerorientierte 2D-Gaming Engine.
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
package pi.config;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.junit.jupiter.api.Test;

/**
 * @author Josef Friedrich
 */
class ConfigGroupInfoTest
{
    @Test
    void targetIsType()
    {
        Target target = ConfigGroupInfo.class.getAnnotation(Target.class);

        assertNotNull(target);
        assertArrayEquals(new ElementType[] { ElementType.TYPE },
            target.value());
    }

    @Test
    void retentionIsRuntime()
    {
        Retention retention = ConfigGroupInfo.class
            .getAnnotation(Retention.class);

        assertNotNull(retention);
        assertEquals(RetentionPolicy.RUNTIME, retention.value());
    }

    @Test
    void annotationIsInherited()
    {
        ConfigGroupInfo annotation = ChildConfig.class
            .getAnnotation(ConfigGroupInfo.class);

        assertNotNull(annotation);
        assertEquals("base_", annotation.prefix());
    }

    @Test
    void explicitPrefixValue()
    {
        ConfigGroupInfo annotation = ExplicitPrefixConfig.class
            .getAnnotation(ConfigGroupInfo.class);

        assertNotNull(annotation);
        assertEquals("game_", annotation.prefix());
    }

    @Test
    void defaultPrefixValue()
    {
        ConfigGroupInfo annotation = DefaultPrefixConfig.class
            .getAnnotation(ConfigGroupInfo.class);

        assertNotNull(annotation);
        assertEquals("", annotation.prefix());
    }

    @ConfigGroupInfo(prefix = "base_")
    static class BaseConfig
    {
    }

    static class ChildConfig extends BaseConfig
    {
    }

    @ConfigGroupInfo(prefix = "game_")
    static class ExplicitPrefixConfig
    {
    }

    @ConfigGroupInfo
    static class DefaultPrefixConfig
    {
    }
}
