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
}
