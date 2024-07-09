package de.pirckheimer_gymnasium.engine_pi.util;

import static de.pirckheimer_gymnasium.engine_pi.util.TextAlignment.CENTER;
import static de.pirckheimer_gymnasium.engine_pi.util.TextAlignment.LEFT;
import static de.pirckheimer_gymnasium.engine_pi.util.TextAlignment.RIGHT;

import java.text.DecimalFormat;
import java.util.stream.Collectors;

/**
 * Eine Sammlung von statischen Hilfsmethoden um <b>Text</b> und
 * <b>Zeichenketten</b> zu bearbeiten.
 */
public class TextUtil
{
    private static final DecimalFormat decimalFormat = new DecimalFormat(
            "0.00");

    private TextUtil()
    {
        throw new UnsupportedOperationException();
    }

    /**
     * <b>Rundet</b> eine Zahl auf zwei Nachkommastellen.
     *
     * @param number Eine Zahl, die gerundet werden soll.
     *
     * @return Die gerundete Zahl als Zeichenkette.
     */
    public static String roundNumber(Object number)
    {
        return decimalFormat.format(number);
    }

    /**
     * Gibt die <b>maximale Zeilenbreite</b> eines gegebenen Texts zurück.
     *
     * @param text Der Text, von dem die maximale Zeilenbreite bestimmt werden
     *             soll.
     *
     * @return Die Anzahl an Zeichen, die die längste Zeile beinhaltet.
     *
     * @since 0.23.0
     */
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
     * Gibt die Anzahl der Zeilen zurück.
     *
     * @param text Der Text, von dem die Anzahl der Zeilen bestimmt werden soll.
     *
     * @return Die Anzahl der Zeilen.
     *
     * @since 0.23.0
     */
    public static int getLineCount(String text)
    {
        return (int) text.lines().count();
    }

    /**
     * Teilt einen Text in die einzelnen Zeilen auf. Der Text muss
     * Zeilenumbrüche enthalten.
     *
     * @param text Der Text, der in die einzelnen Zeilen aufgeteilt werden soll.
     *
     * @return Die einzelnen Zeilen des Textes.
     *
     * @since 0.23.0
     */
    public static String[] splitLines(String text)
    {
        return text.lines().toArray(String[]::new);
    }

    /**
     * <b>Richtet</b> den gegebenen Text gemäß einer bestimmten
     * <b>Zeilenbreite</b> und einer gewünschten <b>Textausrichtung</b>
     * <b>aus</b>.
     *
     * @param text      Die Zeichenkette, die ausgerichtet werden soll.
     * @param width     Die Anzahl an Zeichen, die jede Zeile lang sein soll.
     * @param alignment Ob die Zeichen links-, rechtsbündig oder zentriert
     *                  ausgerichtet werden soll.
     *
     * @return Eine Zeichenkette, in der je nach Ausrichtung Leerzeichen
     *         eingefügt wurden.
     *
     * @since 0.23.0
     */
    public static String align(String text, int width, TextAlignment alignment)
    {
        return text.lines().map((String line) -> {
            line = line.trim();
            int length = line.length();
            int spaces = width - length;
            if (alignment == RIGHT)
            {
                return " ".repeat(spaces) + line;
            }
            else if (alignment == CENTER)
            {
                int left = spaces / 2;
                return " ".repeat(left) + line;
            }
            return line;
        }).collect(Collectors.joining("\n"));
    }

    /**
     * <b>Richtet</b> den gegebenen Text gemäß einer gewünschten
     * <b>Textausrichtung</b> <b>aus</b>.
     *
     * @param text      Die Zeichenkette, die ausgerichtet werden soll.
     * @param alignment Ob die Zeichen links-, rechtsbündig oder zentriert
     *                  ausgerichtet werden soll.
     *
     * @return Eine Zeichenkette, in der je nach Ausrichtung Leerzeichen
     *         eingefügt wurden.
     *
     * @since 0.23.0
     */
    public static String align(String text, TextAlignment alignment)
    {
        return align(text, getLineWidth(text), alignment);
    }

    /**
     * Bricht den gegebenen Text nach einer bestimmten <b>Zeilenbreite</b> um.
     * Außerdem kann die <b>Textausrichtung</b> angegeben werden.
     *
     * <p>
     * Nach einem Code-Beispiel auf <a href=
     * "https://github.com/eugenp/tutorials/blob/master/core-java-modules/core-java-string-algorithms-3/src/main/java/com/baeldung/wrappingcharacterwise/Wrapper.java">baeldung.com</a>.
     * </p>
     *
     * @param text      Der Text, der nach einer bestimmten Zeilenbreite
     *                  umgebrochen werden soll.
     * @param width     Die maximale Zeilenbreite.
     * @param alignment Die Textausrichtung.
     *
     * @return Der neu formatierte Text, in den möglicherweise neue
     *         Zeilenumbrüche eingefügt wurden.
     *
     * @throws IllegalArgumentException Falls es ein längeres Wort als die
     *                                  Zeilenbreite gibt.
     *
     * @since 0.23.0
     */
    public static String wrap(String text, int width, TextAlignment alignment)
    {
        if (width < 1)
        {
            width = getLineWidth(text);
        }
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
        return align(stringBuilder.toString(), width, alignment);
    }

    /**
     * Bricht den gegebenen Text nach einer bestimmten <b>Zeilenbreite</b> und
     * zwar <b>linksbündig</b> um.
     *
     * @param text  Der Text, der nach einer bestimmten Zeilenbreite umgebrochen
     *              werden soll.
     * @param width Die maximale Zeilenbreite.
     *
     * @return Der neu formatierte Text, in den möglicherweise neue
     *         Zeilenumbrüche eingefügt wurden.
     *
     * @throws IllegalArgumentException Falls es ein längeres Wort als die
     *                                  Zeilenbreite gibt.
     *
     * @since 0.23.0
     */
    public static String wrap(String text, int width)
    {
        return wrap(text, width, LEFT);
    }
}
