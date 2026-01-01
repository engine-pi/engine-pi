/*
 * Source: https://github.com/gurkenlabs/litiengine/blob/main/litiengine/src/main/java/de/gurkenlabs/litiengine/configuration/Quality.java
 *
 * MIT License
 *
 * Copyright (c) 2016 - 2025 Gurkenlabs
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package pi.configuration;

/**
 * Enum representing different quality levels.
 *
 * @author Steffen Wilke
 * @author Matthias Wilke
 *
 * @since 0.42.0
 */
public enum Quality
{
    VERYLOW(0), LOW(1), MEDIUM(2), HIGH(3), VERYHIGH(4);

    private final int value;

    /**
     * Constructs a Quality enum with the specified value.
     *
     * @param value The integer value representing the quality level.
     */
    private Quality(int value)
    {
        this.value = value;
    }

    /**
     * Gets the integer value of the quality level.
     *
     * @return The integer value of the quality level.
     */
    public int getValue()
    {
        return value;
    }
}
