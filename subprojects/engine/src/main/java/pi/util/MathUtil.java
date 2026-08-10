/*
 * Source: https://github.com/gurkenlabs/litiengine/blob/main/litiengine/src/main/java/de/gurkenlabs/litiengine/util/MathUtilities.java
 *
 * MIT License
 *
 * Copyright (c) 2016 - 2024 Gurkenlabs
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
package pi.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Eine Sammlung von statischen Hilfsmethoden um <b>mathematische</b> Operation
 * auszuführen.
 *
 * @author Steffen Wilke
 * @author Matthias Wilke
 */
public class MathUtil
{
    /**
     * Ein privater Konstruktor, um den öffentlichen Konstruktor zu verbergen.
     * Dadurch können von dieser Klasse keine Instanzen erstellt werden.
     */
    private MathUtil()
    {
        throw new UnsupportedOperationException();
    }

    /**
     * Prüft, ob zwei Gleitkommazahlen innerhalb einer Toleranz gleich sind.
     *
     * @param d1 Der erste Wert.
     * @param d2 Der zweite Wert.
     * @param epsilon Die erlaubte Abweichung.
     *
     * @return {@code true}, wenn sich beide Werte höchstens um {@code epsilon}
     *     unterscheiden, sonst {@code false}.
     */
    public static boolean equals(double d1, double d2, double epsilon)
    {
        return Math.abs(d1 - d2) <= epsilon;
    }

    /**
     * Rundet einen {@code float}-Wert auf eine feste Anzahl von
     * Nachkommastellen.
     *
     * @param value Der zu rundende Wert.
     * @param places Die Anzahl der Nachkommastellen.
     *
     * @return Der gerundete Wert.
     */
    public static float round(float value, int places)
    {
        return (float) round((double) value, places);
    }

    /**
     * Rundet einen {@code double}-Wert auf die nächste ganze Zahl.
     *
     * @param value Der zu rundende Wert.
     *
     * @return Der gerundete Wert als {@code int}.
     */
    public static int round(double value)
    {
        return (int) Math.round(value);
    }

    /**
     * Rundet einen {@code double}-Wert auf eine feste Anzahl von
     * Nachkommastellen.
     *
     * @param value Der zu rundende Wert.
     * @param places Die Anzahl der Nachkommastellen.
     *
     * @return Der gerundete Wert.
     *
     * @throws IllegalArgumentException Wenn {@code places} kleiner als 0 ist.
     */
    public static double round(double value, int places)
    {
        if (places < 0)
        {
            throw new IllegalArgumentException();
        }
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    /**
     * Begrenzt den Wert (falls nötig) auf den Bereich zwischen Minimum und
     * Maximum.
     *
     * @param value Der zu begrenzende Wert.
     * @param min Der kleinste zulässige Wert.
     * @param max Der größte zulässige Wert.
     *
     * @return Ein auf die angegebenen Grenzen begrenzter Wert.
     */
    public static double clamp(final double value, final double min,
            final double max)
    {
        return Math.max(min, Math.min(max, value));
    }

    /**
     * Begrenzt den Wert (falls nötig) auf den Bereich zwischen Minimum und
     * Maximum.
     *
     * @param value Der zu begrenzende Wert.
     * @param min Der kleinste zulässige Wert.
     * @param max Der größte zulässige Wert.
     *
     * @return Ein auf die angegebenen Grenzen begrenzter Wert.
     */
    public static float clamp(final float value, final float min,
            final float max)
    {
        return Math.max(min, Math.min(max, value));
    }

    /**
     * Begrenzt den Wert (falls nötig) auf den Bereich zwischen Minimum und
     * Maximum.
     *
     * @param value Der zu begrenzende Wert.
     * @param min Der kleinste zulässige Wert.
     * @param max Der größte zulässige Wert.
     *
     * @return Ein auf die angegebenen Grenzen begrenzter Wert.
     */
    public static byte clamp(final byte value, final byte min, final byte max)
    {
        if (value < min)
        {
            return min;
        }
        if (value > max)
        {
            return max;
        }
        return value;
    }

    /**
     * Begrenzt den Wert (falls nötig) auf den Bereich zwischen Minimum und
     * Maximum.
     *
     * @param value Der zu begrenzende Wert.
     * @param min Der kleinste zulässige Wert.
     * @param max Der größte zulässige Wert.
     *
     * @return Ein auf die angegebenen Grenzen begrenzter Wert.
     */
    public static short clamp(final short value, final short min,
            final short max)
    {
        if (value < min)
        {
            return min;
        }
        if (value > max)
        {
            return max;
        }
        return value;
    }

    /**
     * Begrenzt den Wert (falls nötig) auf den Bereich zwischen Minimum und
     * Maximum.
     *
     * @param value Der zu begrenzende Wert.
     * @param min Der kleinste zulässige Wert.
     * @param max Der größte zulässige Wert.
     *
     * @return Ein auf die angegebenen Grenzen begrenzter Wert.
     */
    public static int clamp(final int value, final int min, final int max)
    {
        if (value < min)
        {
            return min;
        }
        if (value > max)
        {
            return max;
        }
        return value;
    }

    /**
     * Begrenzt den Wert (falls nötig) auf den Bereich zwischen Minimum und
     * Maximum.
     *
     * @param value Der zu begrenzende Wert.
     * @param min Der kleinste zulässige Wert.
     * @param max Der größte zulässige Wert.
     *
     * @return Ein auf die angegebenen Grenzen begrenzter Wert.
     */
    public static long clamp(final long value, final long min, final long max)
    {
        if (value < min)
        {
            return min;
        }
        if (value > max)
        {
            return max;
        }
        return value;
    }

    /**
     * Berechnet den Durchschnitt aller Werte im Array.
     *
     * @param numbers Die Werte, aus denen der Durchschnitt berechnet wird.
     *
     * @return Der Durchschnitt der Werte.
     */
    public static double getAverage(final double[] numbers)
    {
        double sum = 0;
        for (final double number : numbers)
        {
            if (number != 0)
            {
                sum += number;
            }
        }
        return sum / numbers.length;
    }

    /**
     * Berechnet den Durchschnitt aller Werte im Array.
     *
     * @param numbers Die Werte, aus denen der Durchschnitt berechnet wird.
     *
     * @return Der Durchschnitt der Werte.
     */
    public static float getAverage(final float[] numbers)
    {
        float sum = 0;
        for (final float number : numbers)
        {
            if (number != 0)
            {
                sum += number;
            }
        }
        return sum / numbers.length;
    }

    /**
     * Berechnet den Durchschnitt aller Werte im Array.
     *
     * @param numbers Die Werte, aus denen der Durchschnitt berechnet wird.
     *
     * @return Der Durchschnitt der Werte.
     */
    public static int getAverage(final int[] numbers)
    {
        int sum = 0;
        for (final int number : numbers)
        {
            if (number != 0)
            {
                sum += number;
            }
        }
        return sum / numbers.length;
    }

    /**
     * Ermittelt den größten Wert aus einer Liste von Ganzzahlen.
     *
     * @param numbers Die zu vergleichenden Werte.
     *
     * @return Der größte gefundene Wert.
     */
    public static int getMax(final int... numbers)
    {
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < numbers.length; i++)
        {
            if (numbers[i] > max)
            {
                max = numbers[i];
            }
        }
        return max;
    }

    /**
     * Prüft, ob ein {@code double}-Wert eine ganze Zahl repräsentiert.
     *
     * @param value Der zu prüfende Wert.
     *
     * @return {@code true}, wenn der Wert eine ganze Zahl ist und nicht
     *     unendlich, sonst {@code false}.
     */
    public static boolean isInt(final double value)
    {
        return value == Math.floor(value) && !Double.isInfinite(value);
    }

    /**
     * Prüft, ob eine Ganzzahl ungerade ist.
     *
     * @param num Die zu prüfende Zahl.
     *
     * @return {@code true}, wenn die Zahl ungerade ist, sonst {@code false}.
     */
    public static boolean isOddNumber(int num)
    {
        return (num & 1) != 0;
    }

    /**
     * Berechnet den ganzzahligen Prozentwert von {@code fraction} bezogen auf
     * {@code value}.
     *
     * @param value Der Gesamtwert.
     * @param fraction Der Teilwert.
     *
     * @return Der Prozentwert als ganze Zahl.
     */
    public static int getFullPercent(double value, double fraction)
    {
        if (value == 0)
        {
            return 0;
        }
        return (int) ((fraction * 100.0f) / value);
    }

    /**
     * Berechnet den Prozentwert von {@code fraction} bezogen auf {@code value}.
     *
     * @param value Der Gesamtwert.
     * @param fraction Der Teilwert.
     *
     * @return Der Prozentwert.
     */
    public static double getPercent(double value, double fraction)
    {
        if (value == 0)
        {
            return 0;
        }
        return (float) fraction * 100 / value;
    }
}
