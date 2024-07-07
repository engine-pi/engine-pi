package de.pirckheimer_gymnasium.engine_pi.util;

import java.text.DecimalFormat;

public class TextUtil
{
    private static DecimalFormat decimalFormat = new DecimalFormat("0.00");

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

    public static String align(String input, int width, TextAlignment alignment)
    {
        return input;
    }

    /**
     * https://github.com/eugenp/tutorials/blob/master/core-java-modules/core-java-string-algorithms-3/src/main/java/com/baeldung/wrappingcharacterwise/Wrapper.java
     *
     * @param input
     * @param width
     * @return
     */
    public static String wrap(String input, int width)
    {
        StringBuilder stringBuilder = new StringBuilder(input);
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
