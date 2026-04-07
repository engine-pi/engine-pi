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
package pi;

import static org.junit.jupiter.api.AssertionFailureBuilder.assertionFailure;

import pi.debug.ToStringFormatter;

/**
 * @author Josef Friedrich
 *
 * @since 0.45.0
 */
public class CustomAssertions
{
    private CustomAssertions()
    {
        throw new UnsupportedOperationException();
    }

    public static void assertToStringClassName(Object actual)
    {
        String className = actual.getClass().getSimpleName();
        String toStringActual = getToString(actual);
        {
            if (!toStringActual.startsWith(className + " ["))
                fail(className,
                    toStringActual,
                    "ToStringFormatter output should start with class name: "
                            + className);
        }
    }

    public static void assertToStringContains(String expected, Object actual)
    {
        String toStringActual = getToString(actual);
        if (toStringActual.indexOf(expected) == -1)
        {
            fail(expected,
                toStringActual,
                "ToStringFormatter output doesn’t contain: " + expected);
        }
    }

    public static void assertToStringFieldValue(String expectedFieldName,
            Object expectedValue, Object actual)
    {
        String toStringActual = getToString(actual);
        var field = new ToStringFormatter.Field(expectedFieldName,
                expectedValue, null);
        String fieldValuePair = ToStringFormatter.clean(field.format());
        if (toStringActual.indexOf(fieldValuePair) == -1)
        {
            fail(fieldValuePair,
                toStringActual,
                "ToStringFormatter output doesn’t contain field value pair: "
                        + fieldValuePair);
        }
    }

    private static String getToString(Object actual)
    {
        return ToStringFormatter.clean(String.valueOf(actual));
    }

    private static void fail(Object expected, Object actual,
            Object messageOrSupplier)
    {
        assertionFailure().message(messageOrSupplier)
            .expected(expected)
            .actual(actual)
            .buildAndThrow();
    }
}
