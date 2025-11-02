/*
 * https://github.com/gurkenlabs/litiengine/blob/main/litiengine/src/main/java/de/gurkenlabs/litiengine/Valign.java
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
package de.pirckheimer_gymnasium.engine_pi.graphics;

/**
 * The enum {@code Valign} defines a range of vertical alignments.
 */
public enum Valign
{
    DOWN(1f), MIDDLE(0.5f), TOP(0f), MIDDLE_TOP(0.25f), MIDDLE_DOWN(0.75f);

    private final float portion;

    Valign(float portion)
    {
        this.portion = portion;
    }

    /**
     * Gets the vertical align enumeration value for the specified string.
     *
     * @param valignString The string representing the enum value.
     *
     * @return The enum value represented by the specified string or
     *     {@link Valign#DOWN} if the specified string is invalid.
     */
    public static Valign get(final String valignString)
    {
        if (valignString == null || valignString.isEmpty())
        {
            return Valign.DOWN;
        }

        try
        {
            return Valign.valueOf(valignString.toUpperCase());
        }
        catch (final IllegalArgumentException iae)
        {
            return Valign.DOWN;
        }
    }

    /**
     * Gets the proportional value of this instance.
     *
     * @param height The height to calculate the relative value from.
     *
     * @return The proportional value for the specified height.
     */
    public float getValue(float height)
    {
        return height * this.portion;
    }

    /**
     * Gets the proportional value of this instance.
     *
     * @param height The height to calculate the relative value from.
     *
     * @return The proportional value for the specified height.
     */
    public double getValue(double height)
    {
        return height * this.portion;
    }

    /**
     * Gets the proportional value of this instance.
     *
     * @param height The height to calculate the relative value from.
     *
     * @return The proportional value for the specified height.
     */
    public int getValue(int height)
    {
        return (int) (height * this.portion);
    }

    /**
     * Gets the location for the specified object height to be vertically
     * aligned relatively to the bounds of the specified height.<br>
     * Suitable for <b>entity</b> alignment. The return value might be negative
     * or exceed the bottom boundary which is <i>{@code height} -
     * {@code objectHeight}</i>. <br>
     * For <b>text</b> alignment {@link #getLocation(double, double, boolean)}
     * should be used with <i>{@code preventOverflow}</i> set to {@code true}.
     *
     * @param height The height, limiting the vertical alignment.
     * @param objectHeight The height of the object for which to calculate the
     *     vertically aligned location.
     *
     * @return The y-coordinate for the location of the object with the
     *     specified height.
     */
    public double getLocation(final double height, final double objectHeight)
    {
        return getLocation(height, objectHeight, false);
    }

    /**
     * Gets the location for the specified object height to be vertically
     * aligned relatively to the bounds of the specified height. An overflow
     * behavior (whenever <i>{@code objectHeight} > {@code height}</i>) can be
     * controlled using <b>{@code preventOverflow}</b> parameter:
     *
     * <ul>
     * <li><i>false</i>: the return value might be negative or exceed the bottom
     * boundary which is <i>{@code height} - {@code objectHeight}</i> (good for
     * <b>entity</b> alignment).</li>
     * <li><i>true</i>: the return value will always be clamped within the
     * bounds (good for <b>text</b> alignment).</li>
     * </ul>
     *
     * @param height The height, limiting the vertical alignment.
     * @param objectHeight The height of the object for which to calculate the
     *     vertically aligned location.
     * @param preventOverflow A flag indicating whether the return value should
     *     be clamped to keep it within the bounds (prevent values that are
     *     negative or beyond the bottom boundary).
     *
     * @return The y-coordinate for the location of the object with the
     *     specified height.
     */
    public double getLocation(final double height, final double objectHeight,
            final boolean preventOverflow)
    {
        double value = this.getValue(height);
        double location = value - objectHeight / 2.0;

        if (objectHeight > height && !preventOverflow)
        {
            return location;
        }

        return Math.max(0, Math.min(height - objectHeight, location));
    }

    /**
     * Gets the portion value of the alignment.
     *
     * @return The portion value of the alignment.
     */
    public float getPortion()
    {
        return portion;
    }
}
