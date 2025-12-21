package pi.debug;

/**
 * @author Josef Friedrich
 *
 * @repolink https://github.com/bschlangaul-sammlung/java-fuer-examens-aufgaben/blob/main/src/main/java/org/bschlangaul/helfer/Farbe.java
 *
 * @see <a href="https://github.com/fusesource/jansi">jansi</a>
 *
 * @since 0.42.0
 */
public class AnsiColor
{

    // https://github.com/fusesource/jansi/blob/70186aa41bef6877a83e1a0c3ed7ebe87185f377/src/main/java/org/fusesource/jansi/Ansi.java#L27-L78
    public static final String RESET = "\u001B[0m";

    public static final String BLACK = "\u001B[30m";

    public static final String RED = "\u001B[31m";

    public static final String GREEN = "\u001B[32m";

    public static final String YELLOW = "\u001B[33m";

    public static final String BLUE = "\u001B[34m";

    public static final String MAGENTA = "\u001B[35m";

    public static final String CYAN = "\u001B[36m";

    public static final String WHITE = "\u001B[37m";

    public static String black(Object object)
    {
        return BLACK + object.toString() + RESET;
    }

    public static String red(Object object)
    {
        return RED + object.toString() + RESET;
    }

    public static String green(Object object)
    {
        return GREEN + object.toString() + RESET;
    }

    public static String yellow(Object object)
    {
        return YELLOW + object.toString() + RESET;
    }

    public static String blue(Object object)
    {
        return BLUE + object.toString() + RESET;
    }

    public static String magenta(Object object)
    {
        return MAGENTA + object.toString() + RESET;
    }

    public static String cyan(Object object)
    {
        return CYAN + object.toString() + RESET;
    }

    public static String white(Object object)
    {
        return WHITE + object.toString() + RESET;
    }

    /**
     * Entferne die ANSI-Farben wieder.
     *
     * @param colored Eine Zeichenketten mit ANSI-Escapesequenzen.
     *
     * @return Eine Zeichenkette ohne Farbe.
     */
    public static String remove(String colored)
    {
        return colored.replaceAll("\u001B\\[\\d+m", "");
    }
}
