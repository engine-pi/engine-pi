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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Color;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * @since 0.53.0
 */
class ToStringFormatterTest
{

    @Nested
    class ConstructorTests
    {
        @Test
        void constructorWithClassName()
        {
            ToStringFormatter formatter = new ToStringFormatter("TestClass");
            String result = formatter.format();

            assertTrue(result.contains("TestClass"));
        }

        @Test
        void constructorWithObject()
        {
            String testObject = "TestString";
            ToStringFormatter formatter = new ToStringFormatter(testObject);
            String result = formatter.format();

            assertTrue(result.contains("String"));
            // Der Hash-Code sollte vorhanden sein (in Hexadezimalform)
            String cleaned = ToStringFormatter.clean(result);
            assertTrue(cleaned.contains("String"));
        }

        @Test
        void classNameMethod()
        {
            Object testObject = new Object();
            ToStringFormatter formatter = new ToStringFormatter("Initial");
            formatter.className(testObject);

            String result = formatter.format();
            assertTrue(result.contains("Object"));
            assertTrue(result.contains("@"));
        }

        @Test
        void classNameMethodChaining()
        {
            ToStringFormatter formatter = new ToStringFormatter("Initial");
            ToStringFormatter result = formatter.className("NewClass");

            assertEquals(formatter, result);
        }
    }

    @Nested
    class AppendTests
    {
        private ToStringFormatter formatter;

        @BeforeEach
        void setUp()
        {
            formatter = new ToStringFormatter("TestClass");
        }

        @Test
        void appendSingleField()
        {
            formatter.append("name", "John");
            String result = formatter.format();

            assertTrue(result.contains("name=\"John\""));
        }

        @Test
        void appendFieldWithUnit()
        {
            formatter.append("width", 5.5, "m");
            String result = formatter.format();

            assertTrue(result.contains("width="));
            assertTrue(result.contains("m"));
        }

        @Test
        void appendMultipleFields()
        {
            formatter.append("first", "A");
            formatter.append("second", "B");
            formatter.append("third", "C");
            String result = formatter.format();

            int firstPos = result.indexOf("first");
            int secondPos = result.indexOf("second");
            int thirdPos = result.indexOf("third");

            assertTrue(firstPos < secondPos && secondPos < thirdPos);
        }

        @Test
        void appendDouble()
        {
            formatter.append("height", 3.14159);
            String result = formatter.format();

            assertTrue(result.contains("height="));
        }

        @Test
        void appendInteger()
        {
            formatter.append("count", 42);
            String result = formatter.format();

            assertTrue(result.contains("count=42"));
        }

        @Test
        void appendBoolean()
        {
            formatter.append("active", true);
            String result = formatter.format();

            assertTrue(result.contains("active=true"));
        }

        @Test
        void appendNull()
        {
            formatter.append("nullable", null);
            String result = formatter.format();

            assertTrue(result.contains("nullable="));
            assertTrue(result.contains("null"));
        }

        @Test
        void appendDuplicateFieldName()
        {
            formatter.append("name", "First");
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> formatter.append("name", "Second"));
            assertTrue(exception.getMessage().contains("name"));
        }
    }

    @Nested
    class PrependTests
    {
        private ToStringFormatter formatter;

        @BeforeEach
        void setUp()
        {
            formatter = new ToStringFormatter("TestClass");
        }

        @Test
        void prependSingleField()
        {
            formatter.prepend("id", 123);
            String result = formatter.format();

            assertTrue(result.contains("id=123"));
        }

        @Test
        void prependFieldWithUnit()
        {
            formatter.prepend("weight", 75.0, "kg");
            String result = formatter.format();

            assertTrue(result.contains("weight="));
            assertTrue(result.contains("kg"));
        }

        @Test
        void prependMultipleFields()
        {
            formatter.append("last", "Z");
            formatter.prepend("first", "A");
            formatter.prepend("second", "B");
            String result = formatter.format();

            int secondPos = result.indexOf("second");
            int firstPos = result.indexOf("first");
            int lastPos = result.indexOf("last");

            // Prepend sollte umgekehrte Reihenfolge ergeben
            assertTrue(secondPos < firstPos && firstPos < lastPos);
        }

        @Test
        void prependDuplicateFieldName()
        {
            formatter.prepend("name", "First");
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> formatter.prepend("name", "Second"));
            assertTrue(exception.getMessage().contains("name"));
        }

        @Test
        void prependAndAppendCombined()
        {
            formatter.append("middle", "M");
            formatter.prepend("first", "F");
            formatter.append("last", "L");
            String result = formatter.format();

            int firstPos = result.indexOf("first");
            int middlePos = result.indexOf("middle");
            int lastPos = result.indexOf("last");

            assertTrue(firstPos < middlePos && middlePos < lastPos);
        }
    }

    @Nested
    class SpecialDataTypeTests
    {
        private ToStringFormatter formatter;

        @BeforeEach
        void setUp()
        {
            formatter = new ToStringFormatter("DataTypeTest");
        }

        @Test
        void stringFormatting()
        {
            formatter.append("text", "Hello World");
            String result = formatter.format();

            assertTrue(result.contains("text=\"Hello World\""));
        }

        @Test
        void stringWithNewlines()
        {
            formatter.append("multiline", "Line1\nLine2");
            String result = formatter.format();

            assertTrue(result.contains("Line1\\n"));
        }

        @Test
        void characterFormatting()
        {
            formatter.append("letter", 'A');
            String result = formatter.format();

            assertTrue(result.contains("letter='A'"));
        }

        @Test
        void colorFormatting()
        {
            Color color = new Color(255, 0, 0);
            formatter.append("backgroundColor", color);
            String result = formatter.format();

            assertTrue(result.contains("backgroundColor=#ff0000"));
            assertNotNull(result);
        }

        @Test
        void doubleRounding()
        {
            formatter.append("pi", 3.141592653589793);
            String result = formatter.format();

            assertTrue(result.contains("pi=3.1"));
        }
    }

    @Nested
    class FormatTests
    {
        private ToStringFormatter formatter;

        @BeforeEach
        void setUp()
        {
            formatter = new ToStringFormatter("MyClass");
        }

        @Test
        void withoutNewLine()
        {
            formatter.append("value", 42);
            String result = formatter.format(false);

            assertTrue(result.contains("MyClass"));
            assertTrue(!result.startsWith("\n"));
        }

        @Test
        void withNewLineAtBeginning()
        {
            formatter.append("value", 42);
            String result = formatter.format(true);

            assertTrue(result.startsWith("\n") || result.startsWith("\r"));
        }

        @Test
        void formatDefault()
        {
            formatter.append("x", 10);
            String result1 = formatter.format();
            String result2 = formatter.format(false);

            assertEquals(result1, result2);
        }

        @Test
        void empty()
        {
            String result = formatter.format();

            assertTrue(result.contains("MyClass"));
            assertTrue(result.contains("[]"));
        }

        @Test
        void multipleFields()
        {
            formatter.append("name", "Test");
            formatter.append("value", 123);
            formatter.append("active", true);
            String result = formatter.format();

            assertTrue(result.contains("name="));
            assertTrue(result.contains("value="));
            assertTrue(result.contains("active="));
        }
    }

    @Nested
    class ToStringTests
    {
        @Test
        void callsFormat()
        {
            ToStringFormatter formatter = new ToStringFormatter("MyClass");
            formatter.append("field", "value");

            String toStringResult = formatter.toString();
            String formatResult = formatter.format();

            assertEquals(toStringResult, formatResult);
        }

        @Test
        void notNull()
        {
            ToStringFormatter formatter = new ToStringFormatter("MyClass");
            assertNotNull(formatter.toString());
        }
    }

    @Nested
    class CleanTests
    {
        @Test
        void removesAnsiColors()
        {
            // Mit ANSI-Codes
            String dirtyString = "MyClass [value=\u001B[34m42\u001B[0m]";
            String cleaned = ToStringFormatter.clean(dirtyString);

            // Sollte nicht mehr \u001B enthalten
            assertTrue(!cleaned.contains("\u001B"));
        }

        @Test
        void removesHashCodes()
        {
            String dirtyString = "MyClass@3d0eec0 [value=42]";
            String cleaned = ToStringFormatter.clean(dirtyString);

            assertTrue(!cleaned.contains("@"));
        }

        @Test
        void normalString()
        {
            String normalString = "MyClass [value=42]";
            String cleaned = ToStringFormatter.clean(normalString);

            assertEquals(normalString, cleaned);
        }

        @Test
        void emptyString()
        {
            String cleaned = ToStringFormatter.clean("");
            assertEquals("", cleaned);
        }
    }

    @Nested
    class IntegrationTests
    {
        @Test
        void complexFormatting()
        {
            ToStringFormatter formatter = new ToStringFormatter("Person");
            formatter.append("name", "Alice");
            formatter.append("age", 30);
            formatter.append("city", "Berlin");
            formatter.append("active", true);
            formatter.append("salary", 50000.50, "EUR");

            String result = formatter.format();

            assertTrue(result.contains("Person"));
            assertTrue(result.contains("name=\"Alice\""));
            assertTrue(result.contains("age=30"));
            assertTrue(result.contains("city=\"Berlin\""));
            assertTrue(result.contains("active=true"));
            assertTrue(result.contains("salary="));
            assertTrue(result.contains("EUR"));
        }

        @Test
        void mixedPrependAppend()
        {
            ToStringFormatter formatter = new ToStringFormatter("Config");
            formatter.append("setting1", "value1");
            formatter.prepend("id", 1);
            formatter.append("setting2", "value2");
            formatter.prepend("version", "1.0");
            formatter.append("setting3", "value3");

            String result = formatter.format();
            assertNotNull(result);
            assertTrue(result.contains("Config"));
        }

        @Test
        void withDifferentObjects()
        {
            ToStringFormatter formatter1 = new ToStringFormatter(123);
            ToStringFormatter formatter2 = new ToStringFormatter(45.67);
            ToStringFormatter formatter3 = new ToStringFormatter(true);

            String result1 = formatter1.format();
            String result2 = formatter2.format();
            String result3 = formatter3.format();

            assertTrue(result1.contains("Integer"));
            assertTrue(result2.contains("Double"));
            assertTrue(result3.contains("Boolean"));
        }

        @Test
        void methodChaining()
        {
            ToStringFormatter formatter = new ToStringFormatter("Test");
            Object myObject = new Object();
            ToStringFormatter result = formatter.className(myObject);

            assertEquals(formatter, result);
            String formattedOutput = formatter.format();
            assertTrue(formattedOutput.contains("Object"));
        }
    }
}
