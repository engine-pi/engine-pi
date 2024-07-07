package de.pirckheimer_gymnasium.engine_pi.util;

import java.text.DecimalFormat;
import java.util.stream.Collectors;

public class TextUtil
{
    private static final DecimalFormat decimalFormat = new DecimalFormat(
            "0.00");

    /**
     * Rundet eine Zahl auf zwei Nachkommastellen.
     *
     * @param number Eine Zahl die gerundet werden soll.
     *
     * @return Die gerundete Zahl als Zeichenketten.
     */
    public static String roundNumber(Object number)
    {
        return decimalFormat.format(number);
    }

    public static int getLineWidth(String text)
    {
        final int[] width = { 0 };
        text.lines().forEach((String line) -> {
            if (line.length() > width[0])
            {
                width[0] = line.length();
            }
        });
        return width[0];
    }

    /**
     * @param text      Die Zeichenkette, die ausgerichtet werden soll.
     * @param width     Die Anzahl an Zeichen, die jede Zeile lang sein soll.
     * @param alignment Ob die Zeichen links-, rechtsbündig oder zentriert
     *                  ausgerichtet werden soll.
     * @return Eine Zeichenkette, in der je nach Ausrichtung Leerzeichen
     *         eingefügt wurden.
     */
    public static String align(String text, int width, TextAlignment alignment)
    {
        return text.lines().map((String line) -> {
            line = line.trim();
            int length = line.length();
            int spaces = width - length;
            if (alignment == TextAlignment.RIGHT)
            {
                return " ".repeat(spaces) + line;
            }
            else if (alignment == TextAlignment.CENTER)
            {
                int left = spaces / 2;
                int right = spaces - left;
                return " ".repeat(left) + line + " ".repeat(right);
            }
            return line + " ".repeat(spaces);
        }).collect(Collectors.joining("\n"));
    }

    /**
     * @param text  Die Zeichenkette, die ausgerichtet werden soll.
     * @param width Die Anzahl an Zeichen, die jede Zeile lang sein soll.
     *
     * @return Eine Zeichenkette, in der je nach Ausrichtung Leerzeichen
     *         eingefügt wurden.
     */
    public static String align(String text, int width)
    {
        return align(text, width, TextAlignment.LEFT);
    }

    /**
     * @param text Die Zeichenkette, die ausgerichtet werden soll.
     *
     * @return Eine Zeichenkette, in der je nach Ausrichtung Leerzeichen
     *         eingefügt wurden.
     */
    public static String align(String text)
    {
        return align(text, getLineWidth(text), TextAlignment.LEFT);
    }

    /**
     * @param text      Die Zeichenkette, die ausgerichtet werden soll.
     * @param alignment Ob die Zeichen links-, rechtsbündig oder zentriert
     *                  ausgerichtet werden soll.
     *
     * @return Eine Zeichenkette, in der je nach Ausrichtung Leerzeichen
     *         eingefügt wurden.
     */
    public static String align(String text, TextAlignment alignment)
    {
        return align(text, getLineWidth(text), alignment);
    }

    /**
     * https://github.com/eugenp/tutorials/blob/master/core-java-modules/core-java-string-algorithms-3/src/main/java/com/baeldung/wrappingcharacterwise/Wrapper.java
     *
     * @param text
     * @param width
     * @return
     */
    public static String wrap(String text, int width)
    {
        StringBuilder stringBuilder = new StringBuilder(text);
        int index = 0;
        while (stringBuilder.length() > index + width)
        {
            int lastLineReturn = stringBuilder.lastIndexOf("\n", index + width);
            if (lastLineReturn > index)
            {
                index = lastLineReturn;
            }
            else
            {
                index = stringBuilder.lastIndexOf(" ", index + width);
                if (index == -1)
                {
                    throw new IllegalArgumentException("impossible to slice "
                            + stringBuilder.substring(0, width));
                }
                stringBuilder.replace(index, index + 1, "\n");
                index++;
            }
        }
        return stringBuilder.toString();
    }
}
