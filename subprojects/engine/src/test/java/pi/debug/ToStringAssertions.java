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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Color;

/**
 * Custom assertion utilities for testing strings created by
 * {@link ToStringFormatter}.
 *
 * <p>
 * This utility class provides helper methods to assert on the output of
 * {@code toString()} methods that use {@link ToStringFormatter}. It handles the
 * removal of ANSI color codes and hash codes for easier testing.
 * </p>
 *
 * <p>
 * Example usage:
 * </p>
 *
 * <pre>
 * {@code
 * String toStringOutput = obj.toString();
 * ToStringAssertions.assertContainsField(toStringOutput, "name", "John");
 * ToStringAssertions.assertContainsField(toStringOutput, "age", 25);
 * ToStringAssertions.assertContainsField(toStringOutput, "active", true);
 * }
 * </pre>
 *
 * @author Josef Friedrich
 *
 * @since 0.45.0
 */
public class ToStringAssertions
{
    private ToStringAssertions()
    {
        throw new UnsupportedOperationException();
    }

    /**
     * Cleans the ANSI color codes and hash codes from a {@code toString()}
     * output using {@link ToStringFormatter#clean(String)}.
     *
     * @param toString The {@code toString()} output to clean.
     *
     * @return The cleaned string without ANSI codes and hash codes.
     */
    public static String clean(String toString)
    {
        return ToStringFormatter.clean(toString);
    }

    /**
     * Asserts that the given {@code toString()} output contains a field with
     * the specified name and string value.
     *
     * <p>
     * The assertion automatically cleans ANSI color codes from the output
     * before checking.
     * </p>
     *
     * @param toString The {@code toString()} output to check.
     * @param fieldName The name of the field to look for (e.g., "name").
     * @param value The expected string value.
     *
     * @throws AssertionError if the field with the expected value is not found.
     */
    public static void assertContainsField(String toString, String fieldName,
            String value)
    {
        String cleaned = clean(toString);
        String pattern = fieldName + "=\"" + value + "\"";
        assertTrue(cleaned.contains(pattern),
            String.format(
                "Expected toString() to contain field '%s' with value '%s', but got: %s",
                fieldName,
                value,
                cleaned));
    }

    /**
     * Asserts that the given {@code toString()} output contains a field with
     * the specified name and integer value.
     *
     * @param toString The {@code toString()} output to check.
     * @param fieldName The name of the field to look for.
     * @param value The expected integer value.
     *
     * @throws AssertionError if the field with the expected value is not found.
     */
    public static void assertContainsField(String toString, String fieldName,
            int value)
    {
        String cleaned = clean(toString);
        String pattern = fieldName + "=" + value;
        assertTrue(cleaned.contains(pattern),
            String.format(
                "Expected toString() to contain field '%s' with value '%d', but got: %s",
                fieldName,
                value,
                cleaned));
    }

    /**
     * Asserts that the given {@code toString()} output contains a field with
     * the specified name and double value.
     *
     * @param toString The {@code toString()} output to check.
     * @param fieldName The name of the field to look for.
     * @param value The expected double value.
     *
     * @throws AssertionError if the field with the expected value is not found.
     */
    public static void assertContainsField(String toString, String fieldName,
            double value)
    {
        String cleaned = clean(toString);
        // Allow for slight variations in double representation and rounding
        String pattern = fieldName + "=" + value;
        assertTrue(
            cleaned.contains(pattern) || cleaned.matches(
                ".*" + fieldName + "=" + String.format("%.1f", value) + ".*"),
            String.format(
                "Expected toString() to contain field '%s' with value '%.1f', but got: %s",
                fieldName,
                value,
                cleaned));
    }

    /**
     * Asserts that the given {@code toString()} output contains a field with
     * the specified name and boolean value.
     *
     * @param toString The {@code toString()} output to check.
     * @param fieldName The name of the field to look for.
     * @param value The expected boolean value.
     *
     * @throws AssertionError if the field with the expected value is not found.
     */
    public static void assertContainsField(String toString, String fieldName,
            boolean value)
    {
        String cleaned = clean(toString);
        String pattern = fieldName + "=" + value;
        assertTrue(cleaned.contains(pattern),
            String.format(
                "Expected toString() to contain field '%s' with value '%s', but got: %s",
                fieldName,
                value,
                cleaned));
    }

    /**
     * Asserts that the given {@code toString()} output contains a field with
     * the specified name and a Color value.
     *
     * <p>
     * The Color is matched by its string representation (without the
     * "java.awt." prefix, as ToStringFormatter removes it).
     * </p>
     *
     * @param toString The {@code toString()} output to check.
     * @param fieldName The name of the field to look for.
     * @param expectedColor The expected Color value.
     *
     * @throws AssertionError if the field with the expected color is not found.
     */
    public static void assertContainsField(String toString, String fieldName,
            Color expectedColor)
    {
        String cleaned = clean(toString);
        String colorStr = expectedColor.toString().replace("java.awt.", "");
        String pattern = fieldName + "=" + colorStr;
        assertTrue(cleaned.contains(pattern),
            String.format(
                "Expected toString() to contain field '%s' with color '%s', but got: %s",
                fieldName,
                colorStr,
                cleaned));
    }

    /**
     * Asserts that the given {@code toString()} output contains the specified
     * class name.
     *
     * @param toString The {@code toString()} output to check.
     * @param className The expected class name (e.g., "SceneInfoOverlay").
     *
     * @throws AssertionError if the class name is not found.
     */
    public static void assertContainsClassName(String toString,
            String className)
    {
        String cleaned = clean(toString);
        assertTrue(cleaned.startsWith(className),
            String.format(
                "Expected toString() to start with class name '%s', but got: %s",
                className,
                cleaned));
    }

    /**
     * Asserts that the given {@code toString()} output does NOT contain a field
     * with the specified name.
     *
     * <p>
     * This is useful for testing that optional fields are not included when
     * they have not been set.
     * </p>
     *
     * @param toString The {@code toString()} output to check.
     * @param fieldName The name of the field to look for.
     *
     * @throws AssertionError if the field is found in the output.
     */
    public static void assertDoesNotContainField(String toString,
            String fieldName)
    {
        String cleaned = clean(toString);
        assertFalse(cleaned.contains(fieldName + "="),
            String.format(
                "Expected toString() to NOT contain field '%s', but got: %s",
                fieldName,
                cleaned));
    }

    /**
     * Asserts that the given {@code toString()} output contains all the
     * specified field names.
     *
     * @param toString The {@code toString()} output to check.
     * @param fieldNames The names of the fields that should be present.
     *
     * @throws AssertionError if any of the field names is not found.
     */
    public static void assertContainsFields(String toString,
            String... fieldNames)
    {
        String cleaned = clean(toString);
        for (String fieldName : fieldNames)
        {
            assertTrue(cleaned.contains(fieldName + "="),
                String.format(
                    "Expected toString() to contain field '%s', but got: %s",
                    fieldName,
                    cleaned));
        }
    }

    /**
     * Asserts that the given {@code toString()} output contains any of the
     * specified field names.
     *
     * <p>
     * This is useful when testing conditional fields that may or may not be
     * included.
     * </p>
     *
     * @param toString The {@code toString()} output to check.
     * @param fieldNames The names of the fields, at least one should be
     *     present.
     *
     * @throws AssertionError if none of the field names are found.
     */
    public static void assertContainsAnyField(String toString,
            String... fieldNames)
    {
        String cleaned = clean(toString);
        for (String fieldName : fieldNames)
        {
            if (cleaned.contains(fieldName + "="))
            {
                return;
            }
        }
        StringBuilder message = new StringBuilder(
                "Expected toString() to contain at least one of the fields: [");
        for (int i = 0; i < fieldNames.length; i++)
        {
            if (i > 0)
                message.append(", ");
            message.append(fieldNames[i]);
        }
        message.append("], but got: ").append(cleaned);
        throw new AssertionError(message.toString());
    }
}
